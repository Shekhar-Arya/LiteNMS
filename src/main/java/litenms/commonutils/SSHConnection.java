package litenms.commonutils;


import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;

public class SSHConnection
{
    public synchronized static Session getSSHSession(String username, String password, String host)
    {
        Session session = null;
        try
        {
            session = new JSch().getSession(username, host, 22);

            session.setPassword(password);

            session.setConfig("StrictHostKeyChecking", "no");

            session.connect(5000);
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
            if (session != null && session.isConnected())
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

        String responseString = "";

        BufferedReader reader = null;

        try
        {
            channel = (ChannelExec) session.openChannel("exec");

            channel.setCommand(command);

//            ByteArrayOutputStream responseStream = new ByteArrayOutputStream();

//            channel.setOutputStream(responseStream);

            reader = new BufferedReader(new InputStreamReader(channel.getInputStream()));

            channel.connect(5000);

            if(channel.isConnected())
            {
                Thread.sleep(100);
            }

            String result = "";

            while ((result = reader.readLine()) != null)
            {
                responseString+=result;
            }

            System.out.println(responseString);

//            responseString = responseStream.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();

            return null;
        }
        finally
        {
            try
            {
                if (channel != null && !channel.isClosed())
                {
                    channel.disconnect();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            try
            {
                if (reader!=null)
                {
                    reader.close();
                }
            }
            catch (Exception e)
            {

            }
        }
        return responseString;
    }
}
