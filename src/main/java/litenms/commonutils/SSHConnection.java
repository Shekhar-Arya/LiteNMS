package litenms.commonutils;


import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.*;
import java.util.ArrayList;

public class SSHConnection
{
    public Session getSSHSession(String username, String password, String host)
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

    public void closeSSHSession(Session session)
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

    public ChannelShell getSSHChannel(Session session)
    {
        ChannelShell channelShell = null;

        try
        {
            channelShell = (ChannelShell) session.openChannel("shell");

            channelShell.connect();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return channelShell;
    }

    public String runSSHCommands(ChannelShell channel, String commands)
    {
        String responseString = "";

        BufferedReader reader = null;

        BufferedWriter writer = null;

        try
        {

            reader = new BufferedReader(new InputStreamReader(channel.getInputStream()));

            writer = new BufferedWriter(new OutputStreamWriter(channel.getOutputStream()));

/*
            for (String command:commands)
            {
                writer.write(command);
            }
*/

            writer.write(commands);

            writer.write("exit\n");

            writer.flush();

            if (channel.isConnected())
            {
                Thread.sleep(100);
            }

            String result = "";

            while ((result = reader.readLine()) != null)
            {
                responseString+=result;
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (channel != null && !channel.isConnected())
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
                e.printStackTrace();
            }

            try
            {
                if (writer!=null)
                {
                    writer.close();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return responseString;
    }
}
