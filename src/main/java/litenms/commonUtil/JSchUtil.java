package litenms.commonUtil;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.ByteArrayOutputStream;

public class JSchUtil {
    public static String createSSHConnection(String username, String password, String host, String command) {

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

            if(channel.isConnected()) {
                Thread.sleep(100);
            }

            responseString = new String(responseStream.toByteArray());
        } catch (JSchException e) {
            System.out.println(e.getMessage());
//            e.printStackTrace();
        } catch (InterruptedException e) {
//            e.printStackTrace();
            System.out.println(e.getMessage());
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
