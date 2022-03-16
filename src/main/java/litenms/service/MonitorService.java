package litenms.service;

import litenms.dao.DiscoveryDao;
import litenms.dao.MonitorDao;
import litenms.models.DiscoveryModel;
import litenms.models.MonitorModel;

import java.util.List;

public class MonitorService {

    public static boolean addDeviceToMonitor(int id)
    {
        DiscoveryModel model = DiscoveryDao.getDiscoveryRow(id);

        return MonitorDao.addDeviceToMonitor(model);
    }

    public static List<MonitorModel> getMonitorDevices()
    {
        return MonitorDao.getMonitorDevices();
    }
}
