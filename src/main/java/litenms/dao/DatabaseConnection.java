package litenms.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnection {

//    select max(avg_rtt), monitor_id from polling where type = 'Ping' and date between '28/03/2022 15:56:42' and '29/03/2022 15:56:42' group by monitor_id order by max(avg_rtt) desc limit 5;

    static
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public Connection getConnection()
    {
        Connection connection = null;
        try
        {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/LiteNMS","root","Shekh@r2705");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return connection;
    }

    public void closeConnection(Connection connection, PreparedStatement preparedStatement)
    {
        try
        {
            if(preparedStatement!=null && !preparedStatement.isClosed())
            {
                preparedStatement.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        try
        {
            if(connection!=null && !connection.isClosed())
            {
                connection.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
