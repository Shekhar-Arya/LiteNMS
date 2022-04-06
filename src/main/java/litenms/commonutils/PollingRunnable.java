package litenms.commonutils;

import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.Session;
import litenms.dao.MonitorDao;
import litenms.models.MonitorModel;
import litenms.models.PollingModel;
import litenms.service.PollingService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class PollingRunnable implements Runnable
{
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

                        ChannelShell channel;

                        ArrayList<String> output = null;

                        String responseString = "";

                        ArrayList<String> commands = new ArrayList<>();

                        commands.add("free -t | grep Total | awk '{print $2}'\n");

                        commands.add("free -t | grep Total | awk '{print $3}'\n");

                        commands.add("free -t | grep Total | awk '{print $4}'\n");

                        commands.add("mpstat | grep all\n");

                        commands.add("df -ht ext4 | grep / | awk '{print $5}'\n");

                        String allCommands = "free -t | grep Total | awk '{print $2}'\nfree -t | grep Total | awk '{print $3}'\nfree -t | grep Total | awk '{print $4}'\nmpstat | grep all\ndf -ht ext4 | grep / | awk '{print $5}'\n";

                        try
                        {
                            session = SSHConnection.getSSHSession(model.getUsername(),model.getPassword(),model.getIp());

                            if(session!=null && session.isConnected())
                            {
                                channel = SSHConnection.getSSHChannel(session);

                                if (channel != null && channel.isConnected())
                                {
                                    responseString = SSHConnection.runSSHCommands(channel, allCommands);
                                }
                            }
                        }
                        finally
                        {
                            SSHConnection.closeSSHSession(session);
                        }

                        if (responseString!=null && !responseString.isEmpty())
                        {
                            responseString = responseString.substring(responseString.indexOf(model.getUsername()+"@")+model.getUsername().length()+1);

                            output = new ArrayList<>();

                            for (String command:commands)
                            {
                                output.add(responseString.substring(responseString.indexOf(command.trim()),responseString.indexOf(model.getUsername()+"@")).replace(command.trim(),""));

                                responseString = responseString.substring(responseString.indexOf(model.getUsername()+"@")+model.getUsername().length()+1);
                            }

                            try
                            {
                                totalMemory = Double.parseDouble(output.get(0).trim())/1000000;
                            }
                            catch (Exception e)
                            {
                                totalMemory = -1;

                                e.printStackTrace();
                            }

                            try
                            {
                                usedMemory = Double.parseDouble(output.get(1).trim())/1000000;
                            }
                            catch (Exception e)
                            {
                                usedMemory = -1;

                                e.printStackTrace();
                            }

                            try
                            {
                                freeMemory = Double.parseDouble(output.get(2).trim())/1000000;
                            }
                            catch (Exception e)
                            {
                                freeMemory = -1;

                                e.printStackTrace();
                            }

                            try
                            {
                                String[] cpuUsageOutput = output.get(3).trim().split(" ");

                                cpuUsage = Double.parseDouble(cpuUsageOutput[cpuUsageOutput.length-1]);
                            }
                            catch (Exception e)
                            {
                                cpuUsage = -1;

                                e.printStackTrace();
                            }

                            try
                            {
                                diskDetail = Double.parseDouble(output.get(4).replace("%"," ").trim());
                            }
                            catch (Exception e)
                            {
                                diskDetail = -1;

                                e.printStackTrace();
                            }
                        }
                    }
                }

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

                Timestamp timestamp = new Timestamp(new Date().getTime());

                pollingModel.setDate(timestamp.toString());

                int rowsAffected = 0;

                if (packetLoss > 50)
                {
                    rowsAffected = MonitorDao.updateMonitorStatus("Down", model.getId());
                }
                else
                {
                    rowsAffected = MonitorDao.updateMonitorStatus("Up", model.getId());
                }

                if (rowsAffected!=0)
                {
                    PollingService.addPollingData(pollingModel);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
