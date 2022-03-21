package litenms.service;

import litenms.dao.DiscoveryDao;
import litenms.dao.MonitorDao;
import litenms.dao.PollingDao;
import litenms.models.DiscoveryModel;
import litenms.models.MonitorModel;

import java.util.List;

public class MonitorService {

    public static boolean addDeviceToMonitor(int id)
    {
        DiscoveryModel model = null;
        try
        {
            model = DiscoveryDao.getDiscoveryRow(id);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return MonitorDao.addDeviceToMonitor(model);
    }

    public static List<MonitorModel> getMonitorDevices()
    {
        return MonitorDao.getMonitorDevices();
    }


    public static boolean deleteMonitorData(int id)
    {
        return MonitorDao.deleteMonitorData(id);
    }
}
