package litenms.service;

import litenms.commonutils.CacheStore;
import litenms.dao.DashboardDao;
import litenms.dao.MonitorDao;
import litenms.models.MonitorModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class DashboardService
{

    DashboardDao dashboardDao = new DashboardDao();

    MonitorDao monitorDao = new MonitorDao();

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
            List<String> columns = new ArrayList<>();

            columns.add("used_memory");

            columns.add("disk_usage");

            columns.add("cpu_usage");

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            String endTime = format.format(new Date());

            String startTime = format.format(new Date(new Date().getTime()- TimeUnit.HOURS.toMillis(24)));

            result = new HashMap<>();

            for (String column: columns)
            {
                HashMap<Integer,Double> temp = dashboardDao.getTopDataForDashboard(column,startTime,endTime);

                HashMap<String,Double> output = new HashMap<>();

                String ip = "";

                for (int id : temp.keySet())
                {
                    ip = monitorDao.getMonitorIpById(id);

                    if (ip!=null && !ip.isEmpty())
                    {
                        output.put(ip,temp.get(id));
                    }
                }

                result.put(column,output);

            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return result;
    }

}
