package litenms.models;

import java.sql.Date;

public class PollingModel {
    private double avgRtt;         // ping
    private double packetLoss;     //ping
    private double availability;   //ping
    private double cpuUsage;       // top
    private double totalMemory;    // free -h
    private double usedMemory;     // free -h
    private double freeMemory;     // free -h
    private double otherMemory;    // free -h
    private double diskSpaceUsage; // df -ht -ext4
    private Date date;          //time
    private String type;
    private int monitor_id;

    public int getMonitor_id() {
        return monitor_id;
    }

    public void setMonitor_id(int monitor_id) {
        this.monitor_id = monitor_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAvgRtt() {
        return avgRtt;
    }

    public void setAvgRtt(double avgRtt) {
        this.avgRtt = avgRtt;
    }

    public double getPacketLoss() {
        return packetLoss;
    }

    public void setPacketLoss(double packetLoss) {
        this.packetLoss = packetLoss;
    }

    public double getAvailability() {
        return availability;
    }

    public void setAvailability(double availability) {
        this.availability = availability;
    }

    public double getCpuUsage() {
        return cpuUsage;
    }

    public void setCpuUsage(double cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    public double getTotalMemory() {
        return totalMemory;
    }

    public void setTotalMemory(double totalMemory) {
        this.totalMemory = totalMemory;
    }

    public double getUsedMemory() {
        return usedMemory;
    }

    public void setUsedMemory(double usedMemory) {
        this.usedMemory = usedMemory;
    }

    public double getFreeMemory() {
        return freeMemory;
    }

    public void setFreeMemory(double freeMemory) {
        this.freeMemory = freeMemory;
    }

    public double getOtherMemory() {
        return otherMemory;
    }

    public void setOtherMemory(double otherMemory) {
        this.otherMemory = otherMemory;
    }

    public double getDiskSpaceUsage() {
        return diskSpaceUsage;
    }

    public void setDiskSpaceUsage(double diskSpaceUsage) {
        this.diskSpaceUsage = diskSpaceUsage;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
