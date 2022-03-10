package litenms.service;

import litenms.commonUtil.PingDevice;
import litenms.commonUtil.SSHConnection;
import litenms.dao.DiscoveryDao;
import litenms.models.DiscoveryModel;

import java.sql.ResultSet;
import java.util.ArrayList;
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
//        DiscoveryModel model = DiscoveryDao.getDiscoveryRow(id);
        StringBuilder pingData = PingDevice.pingDevice(model.getIp());
//        System.out.println(pingData.substring(pingData.indexOf("%")-3,pingData.indexOf("%")).replace(","," ").trim());
        if(Integer.parseInt(pingData.substring(pingData.indexOf("%")-3,pingData.indexOf("%")).replace(","," ").trim())<=50)
        {
            DiscoveryDao.removeDiscoveryProvision(model.getId());
            DiscoveryDao.addDiscoveryProvision(model);
            return true;
        }
        else
        {
            DiscoveryDao.removeDiscoveryProvision(model.getId());
            return false;
        }
    }

    public static boolean sshDiscoveryDevice(DiscoveryModel model) {
        String sshResult = SSHConnection.getSSHConnection(model, "uname");
        if (sshResult != null && !sshResult.isEmpty() && sshResult.equals("Linux"))
        {
            DiscoveryDao.removeDiscoveryProvision(model.getId());
            DiscoveryDao.addDiscoveryProvision(model);
            return true;
        }
        else {
            DiscoveryDao.removeDiscoveryProvision(model.getId());
            return false;
        }
    }
}
