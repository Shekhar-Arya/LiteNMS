package litenms.models;

import java.util.HashMap;

public class DashboardModel
{
    private HashMap<String,Object> result;

    private int monitorId;

    private double diskUsage;

    private double cpuUsage;

    private double usedMemory;

    private String ip;

    public int getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(int monitorId) {
        this.monitorId = monitorId;
    }

    public double getDiskUsage() {
        return diskUsage;
    }

    public void setDiskUsage(double diskUsage) {
        this.diskUsage = diskUsage;
    }

    public double getCpuUsage() {
        return cpuUsage;
    }

    public void setCpuUsage(double cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    public double getUsedMemory() {
        return usedMemory;
    }

    public void setUsedMemory(double usedMemory) {
        this.usedMemory = usedMemory;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public HashMap<String, Object> getResult()
    {
        return result;
    }

    public void setResult(HashMap<String, Object> result)
    {
        this.result = result;
    }
}
