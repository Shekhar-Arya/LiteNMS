package litenms.commonUtil;


import litenms.models.DiscoveryModel;

public class SSHConnection {

    public static String getSSHConnection(DiscoveryModel discoveryModel,String command)
    {
        String sshResult = JSchUtil.createSSHConnection(discoveryModel.getUsername(),discoveryModel.getPassword(),discoveryModel.getIp(),command);
        return sshResult;
    }

}
