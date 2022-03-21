package litenms.commonutils;

import com.jcraft.jsch.Session;
import litenms.dao.MonitorDao;
import litenms.models.MonitorModel;
import litenms.models.PollingModel;
import litenms.service.PollingService;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PollingRunnable implements Runnable{

    private MonitorModel model;

    PollingRunnable(MonitorModel model)
    {
        this.model = model;
    }

    @Override
    public void run()
    {
        try
        {
            PollingModel pollingModel = new PollingModel();

            String pingData = PingDevice.pingDevice(model.getIp());

            if(pingData!=null && !pingData.isEmpty())
            {
                double packetLoss = Double.parseDouble(pingData.substring(pingData.indexOf("%")-3,pingData.indexOf("%")).replace(","," ").trim());

                double avgRtt = 0.0;

                double totalMemory = 0.0, usedMemory=0.0, freeMemory=0.0,cpuUsage=0.0,diskDetail = 0.0;

                if(packetLoss!=100)
                {
                    avgRtt = Double.parseDouble(pingData.substring(pingData.lastIndexOf("=")).split("/")[1]);

                    if(model.getType().equals("SSH"))
                    {
                        Session session = null;

                        try
                        {
                            session = SSHConnection.getSSHSession(model.getUsername(),model.getPassword(),model.getIp());

                            if(session!=null)
                            {
                                totalMemory = Double.parseDouble(SSHConnection.getSSHConnection(session,"free -t | grep Total | awk '{print $2}'").trim())/1000000;

                                usedMemory = Double.parseDouble(SSHConnection.getSSHConnection(session,"free -t | grep Total | awk '{print $3}'").trim())/1000000;

                                freeMemory = Double.parseDouble(SSHConnection.getSSHConnection(session,"free -t | grep Total | awk '{print $4}'").trim())/1000000;

                                String[] cpuUsageOutput = SSHConnection.getSSHConnection(session,"mpstat | grep all").trim().split(" ");

                                cpuUsage = Double.parseDouble(cpuUsageOutput[cpuUsageOutput.length-1]);

                                diskDetail = Double.parseDouble(SSHConnection.getSSHConnection(session,"df -ht ext4 | grep / | awk '{print $5}'").replace("%"," ").trim());
                            }
                        }
                        finally {
                            SSHConnection.closeSSHSession(session);
                        }

                    }

                }
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                pollingModel.setMonitor_id(model.getId());

                pollingModel.setAvgRtt(Double.parseDouble(String.format("%.2f", avgRtt)));

                pollingModel.setAvailability(packetLoss>50?0:1); // 0 down and 1 Up

                pollingModel.setPacketLoss(Double.parseDouble(String.format("%.2f", packetLoss)));

                pollingModel.setTotalMemory(Double.parseDouble(String.format("%.2f", totalMemory)));

                pollingModel.setUsedMemory(Double.parseDouble(String.format("%.2f", usedMemory)));

                pollingModel.setFreeMemory(Double.parseDouble(String.format("%.2f", freeMemory)));

                pollingModel.setCpuUsage(Double.parseDouble(String.format("%.2f", cpuUsage)));

                pollingModel.setDiskSpaceUsage(Double.parseDouble(String.format("%.2f", diskDetail)));

                pollingModel.setType(model.getType());

                pollingModel.setDate(formatter.format(new Date()));

                if (packetLoss > 50)
                {
                    MonitorDao.updateMonitorStatus("Down", model.getId());
                }
                else
                {
                    MonitorDao.updateMonitorStatus("Up", model.getId());
                }

                PollingService.addPollingData(pollingModel);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
