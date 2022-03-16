package litenms.models;

public class SSHCredentialModel {

    private int sshId;

    private String username;

    private String password;

    private int discoveryId;

    public int getSshId() {
        return sshId;
    }

    public void setSshId(int sshId) {
        this.sshId = sshId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getDiscoveryId() {
        return discoveryId;
    }

    public void setDiscoveryId(int discoveryId) {
        this.discoveryId = discoveryId;
    }
}
