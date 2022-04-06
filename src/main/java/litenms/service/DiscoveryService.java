package litenms.service;

import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.Session;
import litenms.commonutils.PingDevice;
import litenms.commonutils.SSHConnection;
import litenms.dao.DiscoveryDao;
import litenms.models.DiscoveryModel;
import java.util.ArrayList;
import java.util.List;

public class DiscoveryService
{
    public static boolean addDeviceToDiscovery(DiscoveryModel discoveryModel)
    {
        return DiscoveryDao.addDeviceForDiscovery(discoveryModel);
    }

    public static List<DiscoveryModel> getDiscoveryDevices()
    {
        return DiscoveryDao.getDiscoveryDevices();
    }

    public static boolean deleteDiscoveryRow(int id)
    {
        return DiscoveryDao.deleteDiscoveryRow(id);
    }

    public static DiscoveryModel getDiscoveryRow(int id)
    {
        return DiscoveryDao.getDiscoveryRow(id);
    }

    public static boolean updateDiscoveryRow(DiscoveryModel discoveryModel)
    {
        return DiscoveryDao.updateDiscoveryRow(discoveryModel);
    }

    public static boolean runDiscovery(int id)
    {
        DiscoveryModel model = null;

        try
        {
            model = DiscoveryDao.getDiscoveryRow(id);

            if(pingDiscoveryDevice(model))
            {
                if(model.getType().equals("SSH"))
                {
                    return sshDiscoveryDevice(model);
                }
                return true;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean pingDiscoveryDevice(DiscoveryModel model)
    {
        String pingData = null;

        try
        {
            pingData = PingDevice.pingDevice(model.getIp());

            if(pingData !=null && !pingData.isEmpty() )
            {

                if(Integer.parseInt(pingData.substring(pingData.indexOf("%")-3,pingData.indexOf("%")).replace(","," ").trim())<=50)
                {
                    DiscoveryDao.runDiscoverySuccessfull(model.getId());

                    return true;
                }
                else
                {
                    DiscoveryDao.runDiscoveryUnsuccessfull(model.getId());

                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean sshDiscoveryDevice(DiscoveryModel model)
    {
        Session session = null;

        ChannelShell channel = null;

        ArrayList<String> output = null;

        try
        {
            String commands = "uname\n";

            session = SSHConnection.getSSHSession(model.getUsername(),model.getPassword(),model.getIp());

            if(session!=null && session.isConnected())
            {

                channel = SSHConnection.getSSHChannel(session);

                String responseString = "";

                if (channel!=null && channel.isConnected())
                {
                    responseString = SSHConnection.runSSHCommands(channel, commands);
                }

                output = new ArrayList<>();

                responseString = responseString.substring(responseString.indexOf(model.getUsername()+"@")+model.getUsername().length()+1);

                output.add(responseString.substring(responseString.indexOf(commands.trim()),responseString.indexOf(model.getUsername()+"@")).replace(commands.trim(),""));

                String sshResult = output.get(0);

                if (sshResult != null && !sshResult.isEmpty() && sshResult.trim().equals("Linux"))
                {
                    DiscoveryDao.runDiscoverySuccessfull(model.getId());

                    return true;
                }
                else
                {
                    DiscoveryDao.runDiscoveryUnsuccessfull(model.getId());
                }
            }
        }
        catch (Exception e)
        {
            try
            {
                DiscoveryDao.runDiscoveryUnsuccessfull(model.getId());
            }
            catch (Exception e1)
            {
                e1.printStackTrace();
            }

            e.printStackTrace();
        }
        finally
        {
            SSHConnection.closeSSHSession(session);
        }

        return false;
    }


}
