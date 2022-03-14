package litenms.commonUtil;

import litenms.dao.MonitorDao;
import litenms.dao.PollingDao;
import litenms.models.DiscoveryModel;
import litenms.models.MonitorModel;
import litenms.models.PollingModel;
import litenms.service.PollingService;

import java.sql.Date;
import java.time.LocalDateTime;

public class PollingRunnable implements Runnable{
    private MonitorModel model;

    PollingRunnable(MonitorModel model)
    {
        this.model = model;
    }

    @Override
    public void run() {
        PollingModel pollingModel = new PollingModel();
        StringBuilder pingData = PingDevice.pingDevice(model.getIp());
        Double avgRtt = Double.parseDouble(pingData.substring(pingData.lastIndexOf("=")).split("/")[1]);
        System.out.println(pingData.toString());
        System.out.println(avgRtt);
        Double packetLoss = Double.parseDouble(pingData.substring(pingData.indexOf("%")-3,pingData.indexOf("%")).replace(","," ").trim());
        Double totalMemory = 0.0, usedMemory=0.0, freeMemory=0.0,cpuUsage=0.0,diskDetail = 0.0;
        if(model.getType().equals("SSH") && packetLoss!=100)
        {
            totalMemory = Double.parseDouble(SSHConnection.getSSHConnection(model.getUsername(),model.getPassword(),model.getIp(),"free -t --giga | grep Total | awk '{print $2}'").trim());
            usedMemory = Double.parseDouble(SSHConnection.getSSHConnection(model.getUsername(),model.getPassword(),model.getIp(),"free -t --giga | grep Total | awk '{print $3}'").trim());
            freeMemory = Double.parseDouble(SSHConnection.getSSHConnection(model.getUsername(),model.getPassword(),model.getIp(),"free -t --giga | grep Total | awk '{print $4}'").trim());
            cpuUsage = Double.parseDouble(SSHConnection.getSSHConnection(model.getUsername(),model.getPassword(),model.getIp(),"mpstat | grep all | awk '{print $13}'").trim());
            diskDetail = Double.parseDouble(SSHConnection.getSSHConnection(model.getUsername(),model.getPassword(),model.getIp(),"df -ht ext4 | grep / | awk '{print $5}'").replace("%"," ").trim());
            System.out.println(totalMemory+" : "+usedMemory+" : "+freeMemory+" : "+cpuUsage+" : "+diskDetail+" ");
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
        pollingModel.setDate(new Date(new java.util.Date().getTime()));
        System.out.println(pollingModel.getDate());
        if (packetLoss > 50) {
            MonitorDao.updateMonitorStatus("Down", model.getId());
        } else {
            MonitorDao.updateMonitorStatus("Up", model.getId());
        }
        PollingService.addPollingData(pollingModel);
    }
}
