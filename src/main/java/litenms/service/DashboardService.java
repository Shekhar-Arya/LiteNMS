package litenms.service;

import litenms.dao.DashboardDao;
import litenms.dao.MonitorDao;
import litenms.models.DashboardModel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DashboardService
{

    DashboardDao dashboardDao = new DashboardDao();

    public HashMap<String,Integer> getTotalDevicesByStatus()
    {
        HashMap<String,Integer> result = null;

        try
        {
            HashMap<String,Integer> temp = new HashMap<>();

            temp.put("Up",0);

            temp.put("Down",0);

            temp.put("Unknown",0);

            result = dashboardDao.getTotalDevicesByStatus();

            for (String key:temp.keySet())
            {
                if(!result.containsKey(key))
                {
                    result.put(key,temp.get(key));
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return result;
    }

    public HashMap<String,Object> getTopDataForDashboard()
    {
        HashMap<String,Object> result = null;

        try
        {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            String endTime = format.format(new Date());

            String startTime = format.format(new Date(new Date().getTime()- TimeUnit.HOURS.toMillis(1)));

            result = new HashMap<>();

            List<DashboardModel> dashboardModelsOfUsedMemory = dashboardDao.getTopDataOfUsedMemory(startTime,endTime);

            List<DashboardModel> dashboardModelsOfCpuUsage = dashboardDao.getTopDataOfCpuUsage(startTime,endTime);

            List<DashboardModel> dashboardModelsOfDiskUsage = dashboardDao.getTopDataOfDiskUsage(startTime,endTime);

            result.put("cpu_usage",dashboardModelsOfCpuUsage);

            result.put("disk_usage",dashboardModelsOfDiskUsage);

            result.put("used_memory",dashboardModelsOfUsedMemory);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return result;
    }

}
