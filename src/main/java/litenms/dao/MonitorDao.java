package litenms.dao;

import litenms.commonutils.CacheStore;
import litenms.models.DiscoveryModel;
import litenms.models.MonitorModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MonitorDao {

    public static List<MonitorModel> getMonitorDevices()
    {
        Connection connection = null;

        List<MonitorModel> monitorModels = null;

        PreparedStatement statement = null;

        try
        {
            connection = DatabaseConnection.getConnection();

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

        return monitorModels;
    }



    public static boolean addDeviceToMonitor(DiscoveryModel discoveryModel)
    {
        Connection connection = null;

        PreparedStatement statement = null;

        try
        {
            connection = DatabaseConnection.getConnection();

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
            DatabaseConnection.closeConnection(connection,statement);
        }
    }

    public static MonitorModel getDeviceFromMonitor(String ip, String type)
    {
        Connection connection = null;

        MonitorModel model = null;

        PreparedStatement statement = null;

        try
        {
            connection = DatabaseConnection.getConnection();

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
            DatabaseConnection.closeConnection(connection,statement);
        }
        return model;
    }

    public static void updateMonitorStatus(String status, int id)
    {
        Connection connection = null;

        PreparedStatement statement = null;

        try
        {
            connection = DatabaseConnection.getConnection();

            statement = connection.prepareStatement("update monitor set status = ? where id = ?");

            statement.setString(1,status);

            statement.setInt(2,id);

            statement.executeUpdate();

            CacheStore.setCacheList("monitorList",getMonitorDevices());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            DatabaseConnection.closeConnection(connection,statement);
        }
    }

}
