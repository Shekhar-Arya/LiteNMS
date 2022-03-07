package litenms.dao;

import litenms.models.LoginModel;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDao {


    public static boolean checkUserExist(LoginModel loginModel){
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("select password from user where user_name=?");
            statement.setString(1,loginModel.getUsername());
//            System.out.println(BCrypt.hashpw(loginModel.getPassword(),BCrypt.gensalt()));
//            statement.setString(2, BCrypt.hashpw(loginModel.getPassword(),BCrypt.gensalt()));
            ResultSet set = statement.executeQuery();
            if(set.next()){
                if(BCrypt.checkpw(loginModel.getPassword(),set.getString(1)))
                {
                    return true;
                }
                return false;
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
