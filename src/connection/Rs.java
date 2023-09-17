package connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Rs {
    public static ResultSet dbResponse(String dbSql) throws SQLException, ClassNotFoundException {
        ResultSet resultSet= null;
        Connection connection= ConnectionConfig.connection();
        try {

            Statement statement=connection.createStatement();
            String sql=dbSql;
            statement.execute(sql);
            resultSet = statement.getResultSet();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }
}
