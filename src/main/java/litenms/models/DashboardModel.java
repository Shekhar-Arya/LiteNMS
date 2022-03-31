package litenms.models;

import java.util.HashMap;

public class DashboardModel
{
    private HashMap<String,Object> result;

    private int monitorId;

    private double value;

    private String ip;

    public int getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(int monitorId) {
        this.monitorId = monitorId;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
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
