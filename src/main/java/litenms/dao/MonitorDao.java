package litenms.dao;

import litenms.models.DiscoveryModel;
import litenms.models.MonitorModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MonitorDao {

    public static List<MonitorModel> getMonitorDevices()
    {
        Connection connection = DatabaseConnection.getConnection();
        List<MonitorModel> monitorModels = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("select * from monitor");
            ResultSet set = statement.executeQuery();

            while (set.next())
            {
                MonitorModel model = new MonitorModel();
                model.setId(set.getInt(1));
                model.setIp(set.getString(2));
                model.setType(set.getString(3));
                model.setSshId(set.getInt(4));
                monitorModels.add(model);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return monitorModels;
    }



    public static boolean addDeviceToMonitor(DiscoveryModel discoveryModel)
    {
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = null;
        try {
//            System.out.println(getDeviceFromMonitor(discoveryModel.getIp()));
            if(getDeviceFromMonitor(discoveryModel.getIp(),discoveryModel.getType()).getIp() == null)
            {
                statement = connection.prepareStatement("insert into monitor (ip,type,ssh_id) values (?,?,?)");
                statement.setString(1,discoveryModel.getIp());
                statement.setString(2,discoveryModel.getType());
                statement.setInt(3,discoveryModel.getSshId());
                statement.executeUpdate();
                return true;
            }
            else
            {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            DatabaseConnection.closeConnection(connection,statement);
        }
    }

    public static MonitorModel getDeviceFromMonitor(String ip, String type)
    {
        Connection connection = DatabaseConnection.getConnection();
        MonitorModel model = new MonitorModel();
        PreparedStatement statement = null;
        try {
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DatabaseConnection.closeConnection(connection,statement);
        }
        return model;
    }
}
