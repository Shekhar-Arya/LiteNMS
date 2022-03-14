package litenms.models;

import java.sql.Date;
import java.util.HashMap;

public class PollingModel {
    private double avgRtt;         // ping
    private double packetLoss;     //ping
    private int availability;   //ping
    private double cpuUsage;       // mpstat
    private double totalMemory;    // free -h
    private double usedMemory;     // free -h
    private double freeMemory;     // free -h
    private double otherMemory;    // free -h
    private double diskSpaceUsage; // df -ht -ext4
    private Date date;          //time
    private String type;
    private int monitor_id;
    private String message;
    private HashMap<String,Object> result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HashMap<String, Object> getResult() {
        return result;
    }

    public void setResult(HashMap<String, Object> result) {
        this.result = result;
    }

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

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
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


    @Override
    public String toString() {
        return "PollingModel{" +
                "avgRtt=" + avgRtt +
                ", packetLoss=" + packetLoss +
                ", availability=" + availability +
                ", cpuUsage=" + cpuUsage +
                ", totalMemory=" + totalMemory +
                ", usedMemory=" + usedMemory +
                ", freeMemory=" + freeMemory +
                ", otherMemory=" + otherMemory +
                ", diskSpaceUsage=" + diskSpaceUsage +
                ", date=" + date +
                ", type='" + type + '\'' +
                ", monitor_id=" + monitor_id +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }

}
