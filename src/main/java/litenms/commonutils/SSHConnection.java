package litenms.commonutils;


import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.ByteArrayOutputStream;

public class SSHConnection {

    public static Session getSSHSession(String username, String password, String host)
    {
        Session session = null;
        try
        {
            session = new JSch().getSession(username, host, 22);

            session.setPassword(password);

            session.setConfig("StrictHostKeyChecking", "no");

            session.connect();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return session;
    }

    public static void closeSSHSession(Session session)
    {
        try
        {
            if (session != null)
            {
                session.disconnect();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static String getSSHConnection(Session session,String command)
    {
        ChannelExec channel = null;

        String responseString = null;

        try
        {
            channel = (ChannelExec) session.openChannel("exec");

            channel.setCommand(command);

            ByteArrayOutputStream responseStream = new ByteArrayOutputStream();

            channel.setOutputStream(responseStream);

            channel.connect();

            if(channel.isConnected())
            {
                Thread.sleep(100);
            }

            responseString = responseStream.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();

            return null;
        }
        finally
        {
            if (channel != null)
            {
                channel.disconnect();
            }
        }
        return responseString;
    }
}
