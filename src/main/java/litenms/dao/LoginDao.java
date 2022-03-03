package litenms.dao;

import litenms.models.LoginModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDao {


    public static boolean checkUserExist(LoginModel loginModel){
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("select * from user where user_name=? and password=?");
            statement.setString(1,loginModel.getUsername());
            statement.setString(2,loginModel.getPassword());
            ResultSet set = statement.executeQuery();
            if(set.next()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DatabaseConnection.closeConnection(connection,statement);
        }
        return false;
    }
}
