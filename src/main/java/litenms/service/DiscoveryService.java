package litenms.service;

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

}
