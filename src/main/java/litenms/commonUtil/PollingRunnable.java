package litenms.commonUtil;

import litenms.models.DiscoveryModel;
import litenms.models.MonitorModel;
import litenms.models.PollingModel;

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
            cpuUsage = Double.parseDouble(SSHConnection.getSSHConnection(model.getUsername(),model.getPassword(),model.getIp(),"top -bn2 | grep '%Cpu' | tail -1 | grep -P '(....|...) id,'|awk '{print $8}'").trim());
            diskDetail = Double.parseDouble(SSHConnection.getSSHConnection(model.getUsername(),model.getPassword(),model.getIp(),"df -ht ext4 | grep /dev/sda1 | awk '{print $5}'").replace("%"," ").trim());
            System.out.println(totalMemory+" : "+usedMemory+" : "+freeMemory+" : "+cpuUsage+" : "+diskDetail+" ");
        }

        java.util.Date date = new java.util.Date();
        pollingModel.setMonitor_id(model.getId());
        pollingModel.setAvgRtt(avgRtt);
        pollingModel.setPacketLoss(packetLoss);
        pollingModel.setTotalMemory(totalMemory);
        pollingModel.setUsedMemory(usedMemory);
        pollingModel.setFreeMemory(freeMemory);
        pollingModel.setCpuUsage(cpuUsage);
        pollingModel.setDiskSpaceUsage(diskDetail);
        pollingModel.setType(model.getType());
        pollingModel.setDate(new Date(date.getTime()));
        System.out.println(pollingModel.getDate());
    }
}
