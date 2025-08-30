import java.sql.*;

public class DatabaseConnection {
      

    public static Connection getConnection() throws Exception {
        String URL = "jdbc:mysql://localhost:3306/auctiondb";
        String USER = "root";
        String PASSWORD = "";
        String driver="com.mysql.cj.jdbc.Driver";
        Class.forName(driver);
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
