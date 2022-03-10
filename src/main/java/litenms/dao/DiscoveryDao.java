package litenms.dao;

import litenms.models.DiscoveryModel;

import javax.xml.crypto.Data;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class DiscoveryDao {

    public static boolean addDeviceForDiscovery(DiscoveryModel discoveryModel)
    {
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = null;
        try {

            preparedStatement = connection.prepareStatement("insert into discovery (name,ip,type) values (?,?,?);", Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1,discoveryModel.getName());

            preparedStatement.setString(2,discoveryModel.getIp());

            preparedStatement.setString(3,discoveryModel.getType());

            preparedStatement.executeUpdate();

            if(discoveryModel.getType().equals("SSH"))
            {
                ResultSet set = preparedStatement.getGeneratedKeys();
                set.last();

                preparedStatement = connection.prepareStatement("insert into sshCredential (ip,username,password,discovery_id) values (?,?,?,?);");

                preparedStatement.setString(1,discoveryModel.getIp());

                preparedStatement.setString(2,discoveryModel.getUsername());

                preparedStatement.setString(3, Base64.getEncoder().encodeToString(discoveryModel.getPassword().getBytes()));

                preparedStatement.setInt(4,set.getInt(1));

                preparedStatement.executeUpdate();
            }

            return true;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
        finally {

            DatabaseConnection.closeConnection(connection,preparedStatement);

        }
    }


    public static List<DiscoveryModel> getDiscoveryDevices()
    {
        Connection connection = DatabaseConnection.getConnection();

        PreparedStatement statement = null;

        ResultSet set = null;

        List<DiscoveryModel> discoveryModelList = new ArrayList<>();
        try {

            statement = connection.prepareStatement("select * from discovery");

            set = statement.executeQuery();


            if(set!=null)
            {
                try {

                    while (set.next())
                    {
                        DiscoveryModel modal = new DiscoveryModel();

                        modal.setId(set.getInt(1));

                        modal.setName(set.getString(2));

                        modal.setIp(set.getString(3));

                        modal.setType(set.getString(4));

                        discoveryModelList.add(modal);
                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();

        }
        finally {

            DatabaseConnection.closeConnection(connection,statement);
        }

        return discoveryModelList;
    }

    public static boolean deleteDiscoveryRow(int id)
    {
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("delete from discovery where id = ?");
            statement.setInt(1,id);
            statement.executeUpdate();

            statement = connection.prepareStatement("delete from sshCredential where discovery_id = ?");
            statement.setInt(1,id);
            statement.executeUpdate();

            statement = connection.prepareStatement("delete from discovery_provision where discovery_id = ?");
            statement.setInt(1,id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            DatabaseConnection.closeConnection(connection,statement);
        }
    }

    public static DiscoveryModel getDiscoveryRow(int id)
    {
        DiscoveryModel discoveryModel = new DiscoveryModel();
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("select * from discovery where id = ?");
            statement.setInt(1,id);
            ResultSet set = statement.executeQuery();
            if (set.next())
            {
                discoveryModel.setId(set.getInt(1));

                discoveryModel.setName(set.getString(2));

                discoveryModel.setIp(set.getString(3));

                discoveryModel.setType(set.getString(4));
            }
            if(discoveryModel.getType().equals("SSH"))
            {
                statement = connection.prepareStatement("select * from sshCredential where discovery_id = ?");
                statement.setInt(1,discoveryModel.getId());
                set = statement.executeQuery();
                if(set.next())
                {
                    discoveryModel.setUsername(set.getString(3));
                    discoveryModel.setPassword(new String(Base64.getDecoder().decode(set.getString(4))));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DatabaseConnection.closeConnection(connection,statement);
        }
        return discoveryModel;
    }

    public static boolean updateDiscoveryRow(DiscoveryModel discoveryModel)
    {
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("update discovery set name = ?, ip = ? where id = ?");
            statement.setString(1,discoveryModel.getName());
            statement.setString(2,discoveryModel.getIp());
            statement.setInt(3,discoveryModel.getId());
            statement.executeUpdate();

            statement = connection.prepareStatement("update discovery_provision set ip=?,name=? where discovery_id=?");
            statement.setString(1,discoveryModel.getIp());
            statement.setString(2,discoveryModel.getName());
            statement.setInt(3,discoveryModel.getId());
            if(discoveryModel.getType().equals("SSH"))
            {
                statement = connection.prepareStatement("update sshCredential set ip=?, username = ?, password=? where discovery_id = ?");
                statement.setString(1,discoveryModel.getIp());
                statement.setString(2,discoveryModel.getUsername());
                statement.setString(3,Base64.getEncoder().encodeToString(discoveryModel.getPassword().getBytes()));
                statement.setInt(4,discoveryModel.getId());
                statement.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            DatabaseConnection.closeConnection(connection,statement);
        }
    }

    public static boolean addDiscoveryProvision(DiscoveryModel discoveryModel)
    {
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("insert into discovery_provision (name,ip,type,discovery_id) values (?,?,?,?)");
            statement.setString(1,discoveryModel.getName());
            statement.setString(2,discoveryModel.getIp());
            statement.setString(3,discoveryModel.getType());
            statement.setInt(4,discoveryModel.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            DatabaseConnection.closeConnection(connection,statement);
        }
    }

    public static void removeDiscoveryProvision(int discoveryId)
    {
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("delete from discovery_provision where discovery_id = ?");
            statement.setInt(1,discoveryId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DatabaseConnection.closeConnection(connection,statement);
        }
    }
}
