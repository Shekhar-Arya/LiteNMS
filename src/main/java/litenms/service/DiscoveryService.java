package litenms.service;

import litenms.dao.DiscoveryDao;
import litenms.models.DiscoveryModel;

public class DiscoveryService {

    public static boolean addDeviceToDiscovery(DiscoveryModel discoveryModel)
    {
        return DiscoveryDao.addDeviceForDiscovery(discoveryModel);
    }
}
