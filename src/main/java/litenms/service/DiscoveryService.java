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

    private PingDevice pingDevice = new PingDevice();

    private SSHConnection sshConnection = new SSHConnection();

    private DiscoveryDao discoveryDao = new DiscoveryDao();

    public boolean addDeviceToDiscovery(DiscoveryModel discoveryModel)
    {
        return discoveryDao.addDeviceForDiscovery(discoveryModel);
    }

    public List<DiscoveryModel> getDiscoveryDevices()
    {
        return discoveryDao.getDiscoveryDevices();
    }

    public boolean deleteDiscoveryRow(int id)
    {
        return discoveryDao.deleteDiscoveryRow(id);
    }

    public DiscoveryModel getDiscoveryRow(int id)
    {
        return discoveryDao.getDiscoveryRow(id);
    }

    public boolean updateDiscoveryRow(DiscoveryModel discoveryModel)
    {
        return discoveryDao.updateDiscoveryRow(discoveryModel);
    }

    public boolean runDiscovery(int id)
    {
        DiscoveryModel model = null;

        try
        {
            model = discoveryDao.getDiscoveryRow(id);

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

    public boolean pingDiscoveryDevice(DiscoveryModel model)
    {
        String pingData = null;

        try
        {
            pingData = pingDevice.pingDevice(model.getIp());

            if(pingData !=null && !pingData.isEmpty() )
            {

                if(Integer.parseInt(pingData.substring(pingData.indexOf("%")-3,pingData.indexOf("%")).replace(","," ").trim())<=50)
                {
                    discoveryDao.runDiscoverySuccessfull(model.getId());

                    return true;
                }
                else
                {
                    discoveryDao.runDiscoveryUnsuccessfull(model.getId());

                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public boolean sshDiscoveryDevice(DiscoveryModel model)
    {
        Session session = null;

        ChannelShell channel = null;

        ArrayList<String> output = null;

        try
        {
            ArrayList<String> commands = new ArrayList<>();

            commands.add("uname\n");

            session = sshConnection.getSSHSession(model.getUsername(),model.getPassword(),model.getIp());

            if(session!=null && session.isConnected())
            {

                channel = sshConnection.getSSHChannel(session);

                String responseString = "";

                if (channel!=null && channel.isConnected())
                {
                    responseString = sshConnection.runSSHCommands(channel, commands);
                }

                output = new ArrayList<>();

                responseString = responseString.substring(responseString.indexOf(model.getUsername()+"@")+model.getUsername().length()+1);

                for (String command:commands)
                {
                    output.add(responseString.substring(responseString.indexOf(command.trim()),responseString.indexOf(model.getUsername()+"@")).replace(command.trim(),""));

                    responseString = responseString.substring(responseString.indexOf(model.getUsername()+"@")+model.getUsername().length()+1);
                }

                String sshResult = output.get(0);

                if (sshResult != null && !sshResult.isEmpty() && sshResult.trim().equals("Linux"))
                {
                    discoveryDao.runDiscoverySuccessfull(model.getId());

                    return true;
                }
                else
                {
                    discoveryDao.runDiscoveryUnsuccessfull(model.getId());
                }
            }
        }
        catch (Exception e)
        {
            try
            {
                discoveryDao.runDiscoveryUnsuccessfull(model.getId());
            }
            catch (Exception e1)
            {
                e1.printStackTrace();
            }

            e.printStackTrace();
        }
        finally
        {
            sshConnection.closeSSHSession(session);
        }

        return false;
    }


}
