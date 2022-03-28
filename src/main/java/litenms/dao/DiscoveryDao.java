package litenms.dao;

import litenms.commonutils.CacheStore;
import litenms.models.DiscoveryModel;
import litenms.models.SSHCredentialModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

public class DiscoveryDao {


    public static boolean addDeviceForDiscovery(DiscoveryModel discoveryModel)
    {
        Connection connection = null;

        PreparedStatement preparedStatement = null;

        try
        {
            connection = DatabaseConnection.getConnection();

            preparedStatement = connection.prepareStatement("insert into discovery (name,ip,type,status) values (?,?,?,?);", Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1,discoveryModel.getName());

            preparedStatement.setString(2,discoveryModel.getIp());

            preparedStatement.setString(3,discoveryModel.getType());

            preparedStatement.setInt(4,0);

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

                CacheStore.setCacheList("sshCredList", getAllSSHCred());
            }

            return true;

        }
        catch (Exception e)
        {
            e.printStackTrace();

            return false;
        }
        finally
        {
            DatabaseConnection.closeConnection(connection,preparedStatement);
        }
    }


    public static List<DiscoveryModel> getDiscoveryDevices()
    {
        Connection connection = null;

        PreparedStatement statement = null;

        ResultSet set = null;

        List<DiscoveryModel> discoveryModelList = null;

        try
        {
            discoveryModelList = new ArrayList<>();

            connection = DatabaseConnection.getConnection();

            statement = connection.prepareStatement("select * from discovery");

            set = statement.executeQuery();

            if(set!=null)
            {
                    while (set.next())
                    {
                        DiscoveryModel modal = new DiscoveryModel();

                        modal.setId(set.getInt(1));

                        modal.setName(set.getString(2));

                        modal.setIp(set.getString(3));

                        modal.setType(set.getString(4));

                        modal.setStatus(set.getInt(5));

                        discoveryModelList.add(modal);
                    }
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

        return discoveryModelList;
    }

    public static boolean deleteDiscoveryRow(int id)
    {
        Connection connection = null;

        PreparedStatement statement = null;

        try
        {
            connection = DatabaseConnection.getConnection();

            statement = connection.prepareStatement("delete from discovery where id = ?");

            statement.setInt(1,id);

            statement.executeUpdate();

            return true;
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

    public static DiscoveryModel getDiscoveryRow(int id)
    {
        DiscoveryModel discoveryModel = null;

        Connection connection = null;

        PreparedStatement statement = null;

        try
        {
            discoveryModel =  new DiscoveryModel();

            connection = DatabaseConnection.getConnection();

            statement = connection.prepareStatement("select * from discovery where id = ?");

            statement.setInt(1,id);

            ResultSet set = statement.executeQuery();

            if (set.next())
            {
                discoveryModel.setId(set.getInt(1));

                discoveryModel.setName(set.getString(2));

                discoveryModel.setIp(set.getString(3));

                discoveryModel.setType(set.getString(4));

                discoveryModel.setStatus(set.getInt(5));
            }
            if(discoveryModel.getType().equals("SSH"))
            {
                statement = connection.prepareStatement("select * from sshCredential where discovery_id = ?");

                statement.setInt(1,discoveryModel.getId());

                set = statement.executeQuery();

                if(set.next())
                {
                    discoveryModel.setSshId(set.getInt(1));

                    discoveryModel.setUsername(set.getString(3));

                    discoveryModel.setPassword(new String(Base64.getDecoder().decode(set.getString(4))));
                }
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
        return discoveryModel;
    }

    public static boolean updateDiscoveryRow(DiscoveryModel discoveryModel)
    {
        Connection connection = null;

        PreparedStatement statement = null;

        try
        {
            connection = DatabaseConnection.getConnection();

            statement = connection.prepareStatement("update discovery set name = ?, ip = ? where id = ?");

            statement.setString(1,discoveryModel.getName());

            statement.setString(2,discoveryModel.getIp());

            statement.setInt(3,discoveryModel.getId());

            statement.executeUpdate();

            if(discoveryModel.getType().equals("SSH"))
            {
                statement = connection.prepareStatement("update sshCredential set ip=?, username = ?, password=? where discovery_id = ?");

                statement.setString(1, discoveryModel.getIp());

                statement.setString(2, discoveryModel.getUsername());

                statement.setString(3, Base64.getEncoder().encodeToString(discoveryModel.getPassword().getBytes()));

                statement.setInt(4, discoveryModel.getId());

                statement.executeUpdate();

                CacheStore.setCacheList("sshCredList", getAllSSHCred());
            }

            return true;
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

    public static boolean runDiscoverySuccessfull(int id)
    {
        Connection connection = null;

        PreparedStatement statement = null;

        try
        {
            connection = DatabaseConnection.getConnection();

            statement = connection.prepareStatement("update discovery set status=? where id = ?");

            statement.setInt(1,1);

            statement.setInt(2,id);

            statement.executeUpdate();

            return true;
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


    public static boolean runDiscoveryUnsuccessfull(int id)
    {
        Connection connection = null;

        PreparedStatement statement = null;

        try
        {
            connection = DatabaseConnection.getConnection();

            statement = connection.prepareStatement("update discovery set status=? where id = ?");

            statement.setInt(1,0);

            statement.setInt(2,id);

            statement.executeUpdate();

            return true;
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

    public static HashMap<Integer,SSHCredentialModel> getAllSSHCred()
    {
        Connection connection = null;

        PreparedStatement statement = null;

        HashMap<Integer,SSHCredentialModel> sshCredentialModels = null;

        try
        {
            sshCredentialModels = new HashMap<>();

            connection = DatabaseConnection.getConnection();

            statement = connection.prepareStatement("select * from sshCredential");

            ResultSet set = statement.executeQuery();

            while (set.next())
            {
                SSHCredentialModel model = new SSHCredentialModel();

                model.setSshId(set.getInt(1));

                model.setUsername(set.getString(3));

                model.setPassword(new String(Base64.getDecoder().decode(set.getString(4))));

                sshCredentialModels.put(set.getInt(1),model);
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
        return sshCredentialModels;
    }


}
