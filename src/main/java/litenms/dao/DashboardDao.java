package litenms.dao;

import litenms.models.DashboardModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DashboardDao
{

    DatabaseConnection databaseConnection = new DatabaseConnection();

    public HashMap<String,Integer> getTotalDevicesByStatus()
    {
        Connection connection = null;

        PreparedStatement statement = null;

        HashMap<String,Integer> result = null;

        try
        {
            connection = databaseConnection.getConnection();

            statement = connection.prepareStatement("select count(*), status from monitor group by status;");

            ResultSet set = statement.executeQuery();

            result = new HashMap<>();

            while (set.next())
            {
                result.put(set.getString(2),set.getInt(1));
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            databaseConnection.closeConnection(connection,statement);
        }
        return result;
    }

    public List<DashboardModel> getTopDataOfUsedMemory(String startDate, String endDate)
    {
        Connection connection = null;

        PreparedStatement statement = null;

        List<DashboardModel> models = null;

        try
        {
            connection = databaseConnection.getConnection();

            statement = connection.prepareStatement("select max(polling.used_memory), polling.monitor_id, monitor.ip from polling, monitor where polling.monitor_id=monitor.id and polling.type = 'SSH' and polling.cpu_usage!=0 and polling.date between ? and ? group by polling.monitor_id order by max(polling.used_memory) desc limit 5;");

            statement.setString(1,startDate);

            statement.setString(2,endDate);

            ResultSet set = statement.executeQuery();

            models = new ArrayList<>();

            while (set.next())
            {
                DashboardModel model = new DashboardModel();

                model.setMonitorId(set.getInt(2));

                model.setValue(set.getDouble(1));

                model.setIp(set.getString(3));

                models.add(model);

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            databaseConnection.closeConnection(connection,statement);
        }
        return models;
    }

    public List<DashboardModel> getTopDataOfDiskUsage(String startDate, String endDate)
    {
        Connection connection = null;

        PreparedStatement statement = null;

        List<DashboardModel> models = null;

        try
        {
            connection = databaseConnection.getConnection();

            statement = connection.prepareStatement("select max(polling.disk_usage), polling.monitor_id, monitor.ip from polling, monitor where polling.monitor_id=monitor.id and polling.type = 'SSH' and polling.cpu_usage!=0 and polling.date between ? and ? group by polling.monitor_id order by max(polling.disk_usage) desc limit 5;");

            statement.setString(1,startDate);

            statement.setString(2,endDate);

            ResultSet set = statement.executeQuery();

            models = new ArrayList<>();

            while (set.next())
            {
                DashboardModel model = new DashboardModel();

                model.setMonitorId(set.getInt(2));

                model.setValue(set.getDouble(1));

                model.setIp(set.getString(3));

                models.add(model);

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            databaseConnection.closeConnection(connection,statement);
        }
        return models;
    }

    public List<DashboardModel> getTopDataOfCpuUsage(String startDate, String endDate)
    {
        Connection connection = null;

        PreparedStatement statement = null;

        List<DashboardModel> models = null;

        try
        {
            connection = databaseConnection.getConnection();

            statement = connection.prepareStatement("select min(polling.cpu_usage), polling.monitor_id, monitor.ip from polling, monitor where polling.monitor_id=monitor.id and polling.type = 'SSH' and polling.cpu_usage!=0 and polling.date between ? and ? group by polling.monitor_id order by min(polling.cpu_usage) asc limit 5;");

            statement.setString(1,startDate);

            statement.setString(2,endDate);

            ResultSet set = statement.executeQuery();

            models = new ArrayList<>();

            while (set.next())
            {
                DashboardModel model = new DashboardModel();

                model.setMonitorId(set.getInt(2));

                model.setValue(set.getDouble(1));

                model.setIp(set.getString(3));

                models.add(model);

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            databaseConnection.closeConnection(connection,statement);
        }
        return models;
    }

}
