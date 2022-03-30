package litenms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

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

    public HashMap<Integer,Double> getTopDataForDashboard(String column, String startDate, String endDate)
    {
        Connection connection = null;

        PreparedStatement statement = null;

        HashMap<Integer,Double> result = null;

        try
        {
            connection = databaseConnection.getConnection();

            statement = connection.prepareStatement("select max(?), monitor_id from polling where type = 'SSH' and date between ? and ? group by monitor_id order by max(?) desc limit 5;");

            statement.setString(1,column.trim());

            statement.setString(2,startDate);

            statement.setString(3,endDate);

            statement.setString(4,column.trim());

            ResultSet set = statement.executeQuery();

            result = new HashMap<>();

            while (set.next())
            {
                result.put(set.getInt(2),set.getDouble(1));
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

}
