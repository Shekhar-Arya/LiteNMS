package litenms.dao;

import litenms.models.DiscoveryModel;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Base64;

public class DiscoveryDao {

    public static boolean addDeviceForDiscovery(DiscoveryModel discoveryModel)
    {
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("insert into discovery (name,ip,type) values (?,?,?);");
            preparedStatement.setString(1,discoveryModel.getName());
            preparedStatement.setString(2,discoveryModel.getIp());
            preparedStatement.setString(3,discoveryModel.getType());
            preparedStatement.executeUpdate();
            if(discoveryModel.getType().equals("SSH"))
            {
                preparedStatement = connection.prepareStatement("insert into sshCredential (ip,username,password) values (?,?,?);");
                preparedStatement.setString(1,discoveryModel.getIp());
                preparedStatement.setString(2,discoveryModel.getUsername());
                preparedStatement.setString(3, Base64.getEncoder().encodeToString(discoveryModel.getIp().getBytes()));
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

}
