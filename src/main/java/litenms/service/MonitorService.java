package litenms.service;

import litenms.dao.DiscoveryDao;
import litenms.dao.MonitorDao;
import litenms.models.DiscoveryModel;
import litenms.models.MonitorModel;

import java.util.List;

public class MonitorService
{

    MonitorDao monitorDao = new MonitorDao();

    DiscoveryDao discoveryDao = new DiscoveryDao();

    public boolean addDeviceToMonitor(int id)
    {
        DiscoveryModel model = null;

        try
        {
            model = discoveryDao.getDiscoveryRow(id);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return monitorDao.addDeviceToMonitor(model);
    }

    public List<MonitorModel> getMonitorDevices()
    {
        return monitorDao.getMonitorDevices();
    }


    public boolean deleteMonitorData(int id)
    {
        return monitorDao.deleteMonitorData(id);
    }
}
