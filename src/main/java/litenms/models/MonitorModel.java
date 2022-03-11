package litenms.models;

import java.util.HashMap;

public class MonitorModel {
    private int id;
    private String ip;
    private String type;
    private int sshId;
    private String message;
    private HashMap<String,Object> result;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSshId() {
        return sshId;
    }

    public void setSshId(int sshId) {
        this.sshId = sshId;
    }

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
}
