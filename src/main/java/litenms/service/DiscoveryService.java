package litenms.service;

import com.jcraft.jsch.Session;
import litenms.commonutils.PingDevice;
import litenms.commonutils.SSHConnection;
import litenms.dao.DiscoveryDao;
import litenms.models.DiscoveryModel;
import java.util.List;

public class DiscoveryService {

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
        DiscoveryModel model = DiscoveryDao.getDiscoveryRow(id);

        if(pingDiscoveryDevice(model))
        {
            if(model.getType().equals("SSH"))
            {
                return sshDiscoveryDevice(model);
            }
            return true;
        }

        else {
            return false;
        }
    }

    public static boolean pingDiscoveryDevice(DiscoveryModel model)
    {
        String pingData = PingDevice.pingDevice(model.getIp());
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
                return false;
            }
        }
        return false;
    }

    public static boolean sshDiscoveryDevice(DiscoveryModel model) {

        Session session = null;

        try
        {
            session = SSHConnection.getSSHSession(model.getUsername(),model.getPassword(),model.getIp());

            if(session!=null)
            {
                String sshResult = SSHConnection.getSSHConnection(session, "uname");

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
        finally
        {
            SSHConnection.closeSSHSession(session);
        }

        return false;
    }


}
