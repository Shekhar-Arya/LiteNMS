package litenms.dao;

import litenms.commonutils.CacheStore;
import litenms.models.DiscoveryModel;
import litenms.models.MonitorModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MonitorDao
{
    private DatabaseConnection databaseConnection = new DatabaseConnection();

    public List<MonitorModel> getMonitorDevices()
    {
        Connection connection = null;

        List<MonitorModel> monitorModels = null;

        PreparedStatement statement = null;

        try
        {
            connection = databaseConnection.getConnection();

            monitorModels = new ArrayList<>();

            statement = connection.prepareStatement("select * from monitor");

            ResultSet set = statement.executeQuery();

            while (set.next())
            {
                MonitorModel model = new MonitorModel();

                model.setId(set.getInt(1));

                model.setIp(set.getString(2));

                model.setType(set.getString(3));

                model.setSshId(set.getInt(4));

                model.setStatus(set.getString(5));

                monitorModels.add(model);
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
        return monitorModels;
    }



    public boolean addDeviceToMonitor(DiscoveryModel discoveryModel)
    {
        Connection connection = null;

        PreparedStatement statement = null;

        try
        {
            connection = databaseConnection.getConnection();

            if(getDeviceFromMonitor(discoveryModel.getIp(),discoveryModel.getType()).getIp() == null)
            {
                statement = connection.prepareStatement("insert into monitor (ip,type,ssh_id,status) values (?,?,?,?)");

                statement.setString(1,discoveryModel.getIp());

                statement.setString(2,discoveryModel.getType());

                statement.setInt(3,discoveryModel.getSshId());

                statement.setString(4,"Unknown");

                statement.executeUpdate();

                CacheStore.setCacheList("monitorList",getMonitorDevices());

                return true;
            }
            else
            {
                return false;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();

            return false;
        }
        finally
        {
            databaseConnection.closeConnection(connection,statement);
        }
    }



    public MonitorModel getDeviceFromMonitor(String ip, String type)
    {
        Connection connection = null;

        MonitorModel model = null;

        PreparedStatement statement = null;

        try
        {
            connection = databaseConnection.getConnection();

            model = new MonitorModel();

            statement = connection.prepareStatement("select * from monitor where ip=? and type = ?");

            statement.setString(1,ip);

            statement.setString(2,type);

            ResultSet set = statement.executeQuery();

            while(set.next())
            {
                model.setId(set.getInt(1));

                model.setIp(set.getString(2));

                model.setType(set.getString(3));

                model.setSshId(set.getInt(4));

                model.setStatus(set.getString(5));
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
        return model;
    }

    public int updateMonitorStatus(String status, int id)
    {
        Connection connection = null;

        PreparedStatement statement = null;

        int rowsAffected = 0;

        try
        {
            connection = databaseConnection.getConnection();

            statement = connection.prepareStatement("update monitor set status = ? where id = ?");

            statement.setString(1,status);

            statement.setInt(2,id);

            rowsAffected = statement.executeUpdate();

            CacheStore.setCacheList("monitorList",getMonitorDevices());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            databaseConnection.closeConnection(connection,statement);
        }
        return rowsAffected;
    }

    public boolean deleteMonitorData(int id)
    {
        Connection connection = null;

        PreparedStatement statement = null;

        try
        {
            connection = databaseConnection.getConnection();

            statement = connection.prepareStatement("delete from monitor where id = ?");

            statement.setInt(1,id);

            statement.executeUpdate();

            statement = connection.prepareStatement("delete from polling where monitor_id = ?");

            statement.setInt(1,id);

            statement.executeUpdate();

            CacheStore.setCacheList("monitorList",getMonitorDevices());

            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();

            return false;
        }
        finally
        {
            databaseConnection.closeConnection(connection,statement);
        }
    }

    public String getMonitorIpById(int id)
    {
        Connection connection = null;

        PreparedStatement statement = null;

        String ip = "";

        try
        {
            connection = databaseConnection.getConnection();

            statement = connection.prepareStatement("select ip from monitor where id = ?");

            statement.setInt(1,id);

            ResultSet set = statement.executeQuery();

            while (set.next())
            {
                ip = set.getString(1);
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
    return ip;
    }

}
