package litenms.commonUtil;


import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import litenms.models.DiscoveryModel;

import java.io.ByteArrayOutputStream;

public class SSHConnection {

    public static String getSSHConnection(String username, String password, String host, String command)
    {
//        String sshResult = JSchUtil.createSSHConnection(discoveryModel.getUsername(),discoveryModel.getPassword(),discoveryModel.getIp(),command);
//        return sshResult;
        Session session = null;
        ChannelExec channel = null;
        String responseString = null;

        try {
            session = new JSch().getSession(username, host, 22);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(command);
            ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
            channel.setOutputStream(responseStream);
            channel.connect();

            if(channel.isConnected() && command.equals("top -bn2 | grep '%Cpu' | tail -1 | grep -P '(....|...) id,'|awk '{print $8}'")) {
                Thread.sleep(5000);
            }
            else if(channel.isConnected())
            {
                Thread.sleep(100);
            }

            responseString = new String(responseStream.toByteArray());
        } catch (JSchException e) {
            System.out.println(e.getMessage());
            return null;
//            e.printStackTrace();
        } catch (InterruptedException e) {
//            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        } finally {
            if (session != null) {
                session.disconnect();
            }
            if (channel != null) {
                channel.disconnect();
            }
        }
        return responseString;

    }

}
