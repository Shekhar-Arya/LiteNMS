package litenms.dao;

import litenms.models.LoginModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;

public class LoginDao
{
    private DatabaseConnection databaseConnection = new DatabaseConnection();

    public boolean checkUserExist(LoginModel loginModel)
    {
        Connection connection = null;

        PreparedStatement statement = null;

        try
        {
            connection = databaseConnection.getConnection();

            statement = connection.prepareStatement("select password from user where user_name=? and password=?");

            statement.setString(1,loginModel.getUsername());

            statement.setString(2, Base64.getEncoder().encodeToString(loginModel.getPassword().getBytes()));

            ResultSet set = statement.executeQuery();

            if(set.next())
            {
                return true;
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
        return false;
    }
}
