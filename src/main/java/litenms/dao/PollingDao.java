package litenms.dao;

import litenms.models.PollingModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PollingDao
{

    private DatabaseConnection databaseConnection = new DatabaseConnection();

    public void addDataOfPolling(PollingModel pollingModel)
    {
        Connection connection = null;

        PreparedStatement statement = null;

        try
        {
            connection = databaseConnection.getConnection();

            statement = connection.prepareStatement("insert into polling (monitor_id,type,avg_rtt,packet_loss,total_memory,used_memory,free_memory,cpu_usage,disk_usage,date,availability) values (?,?,?,?,?,?,?,?,?,?,?)");

            statement.setInt(1,pollingModel.getMonitor_id());

            statement.setString(2,pollingModel.getType());

            statement.setDouble(3,pollingModel.getAvgRtt());

            statement.setDouble(4,pollingModel.getPacketLoss());

            statement.setDouble(5,pollingModel.getTotalMemory());

            statement.setDouble(6,pollingModel.getUsedMemory());

            statement.setDouble(7,pollingModel.getFreeMemory());

            statement.setDouble(8,pollingModel.getCpuUsage());

            statement.setDouble(9,pollingModel.getDiskSpaceUsage());

            statement.setTimestamp(10,Timestamp.valueOf(pollingModel.getDate()));

            statement.setInt(11,pollingModel.getAvailability());

            statement.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            databaseConnection.closeConnection(connection,statement);
        }
    }

    public PollingModel getPollingLatestData(int id)
    {
        Connection connection = null;

        PreparedStatement statement = null;

        PollingModel model = null;

        try
        {
            model =  new PollingModel();

            connection = databaseConnection.getConnection();

            statement = connection.prepareStatement("select * from polling where monitor_id = ? order by date desc limit 1");

            statement.setInt(1,id);

            ResultSet set = statement.executeQuery();

            while(set.next())
            {
                model.setType(set.getString(3));

                model.setAvgRtt(set.getDouble(4));

                model.setPacketLoss(set.getDouble(5));

                model.setTotalMemory(set.getDouble(6));

                model.setUsedMemory(set.getDouble(7));

                model.setFreeMemory(set.getDouble(8));

                model.setCpuUsage(set.getDouble(9));

                model.setDiskSpaceUsage(set.getDouble(10));

                Timestamp timestamp = set.getTimestamp(11);

                model.setDate(timestamp.toString());

                model.setAvailability(set.getInt(12));
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

    public List<PollingModel> getPollingLastTwentyFourHourData(int id, Timestamp startTime, Timestamp endTime)
    {
        Connection connection = null;

        PreparedStatement statement = null;

        List<PollingModel> pollingModelList = null;

        try
        {
            connection = databaseConnection.getConnection();

            pollingModelList = new ArrayList<>();

            statement = connection.prepareStatement("select * from polling where monitor_id = ? and date between ? and ?");

            statement.setInt(1,id);

            statement.setTimestamp(2,startTime);

            statement.setTimestamp(3,endTime);

            ResultSet set = statement.executeQuery();

            while(set.next())
            {
                PollingModel model = new PollingModel();

                model.setType(set.getString(3));

                model.setAvgRtt(set.getDouble(4));

                model.setPacketLoss(set.getDouble(5));

                model.setTotalMemory(set.getDouble(6));

                model.setUsedMemory(set.getDouble(7));

                model.setFreeMemory(set.getDouble(8));

                model.setCpuUsage(set.getDouble(9));

                model.setDiskSpaceUsage(set.getDouble(10));

                Timestamp timestamp = set.getTimestamp(11);

                model.setDate(timestamp.toString());

                model.setAvailability(set.getInt(12));

                pollingModelList.add(model);
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
        return pollingModelList;
    }

}
