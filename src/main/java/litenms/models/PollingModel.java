package litenms.models;

import java.sql.Date;

public class PollingModel {
    private int avgRtt;         // ping
    private int packetLoss;     //ping
    private int availability;   //ping
    private int cpuUsage;       // free -h
    private int totalMemory;    // free -h
    private int usedMemory;     // free -h
    private int freeMemory;     // free -h
    private int otherMemory;    // free -h
    private int diskSpaceUsage; // df -ht -ext4
    private Date date;          //time

    public int getAvgRtt() {
        return avgRtt;
    }

    public void setAvgRtt(int avgRtt) {
        this.avgRtt = avgRtt;
    }

    public int getPacketLoss() {
        return packetLoss;
    }

    public void setPacketLoss(int packetLoss) {
        this.packetLoss = packetLoss;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    public int getCpuUsage() {
        return cpuUsage;
    }

    public void setCpuUsage(int cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    public int getTotalMemory() {
        return totalMemory;
    }

    public void setTotalMemory(int totalMemory) {
        this.totalMemory = totalMemory;
    }

    public int getUsedMemory() {
        return usedMemory;
    }

    public void setUsedMemory(int usedMemory) {
        this.usedMemory = usedMemory;
    }

    public int getFreeMemory() {
        return freeMemory;
    }

    public void setFreeMemory(int freeMemory) {
        this.freeMemory = freeMemory;
    }

    public int getOtherMemory() {
        return otherMemory;
    }

    public void setOtherMemory(int otherMemory) {
        this.otherMemory = otherMemory;
    }

    public int getDiskSpaceUsage() {
        return diskSpaceUsage;
    }

    public void setDiskSpaceUsage(int diskSpaceUsage) {
        this.diskSpaceUsage = diskSpaceUsage;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
