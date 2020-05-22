package jdbc.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javaFX.Table;

import java.sql.*;
import java.util.Random;

public class JDBCUtils {

    public static void createTable(Connection connection) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS Shop (\n" +
                "                      ID INT,\n" +
                "                      PRODID INT,\n" +
                "                      TITLE VARCHAR(50),\n" +
                "                      COST INT\n" +
                ");";

        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        }
    }

    public static Connection getNewConnection() throws SQLException {
        String dbURL = "jdbc:mysql://localhost:3306/shop?allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String user = "root";
        String password = "Hih6keRes!";
        Connection connection = DriverManager.getConnection(dbURL, user, password);
        if (connection.isValid(1)) {
            /*System.out.println("Please, print a command: \n/add title cost\n" +
              //                                          "/delete title\n" +
                //                                        "/show_all\n" +
                  //                                      "/price title\n" +
                    //                                    "/change_price title cost\n" +
                      //                                  "/filter_by_price startCost endCost\n" +
                                                          "/finish\n");*/
        }
        return connection;
    }

    public static void createNewData(Connection connection, int id, int prodid, String title, int cost) throws SQLException {
        String sql = "INSERT INTO Shop VALUES(?, ?, ?, ?);";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.setInt(2, prodid);
            statement.setString(3, title);
            statement.setInt(4, cost);
            statement.executeUpdate();
        }
    }

    public static void fillInTable(Connection connection, int amount) throws SQLException {
        Random rand = new Random();
        for(int i = 1; i <= amount; i++) {
            int newProdid = rand.nextInt(10000 - 1000);
            String newTitle = "product" + String.valueOf(i);/*String.valueOf(rand.nextInt(1000 - 100));*/
            int newCost = rand.nextInt(10000 - 100);
            createNewData(connection, i, newProdid, newTitle, newCost);
        }
    }

    public static ObservableList<Table> selectData(ResultSet rs) throws SQLException {
        String str = "";
        ObservableList<Table> productData = FXCollections.observableArrayList();
        while (rs.next()) {
            productData.add(new Table(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4)));
        }
        return productData;
    }

    public static void clearTable(Connection connection) throws SQLException {
        String sql = "TRUNCATE TABLE Shop";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.execute();
        }
    }

    public static String add(Connection connection, String title, int cost) throws SQLException {
        String sql = "SELECT TITLE FROM Shop WHERE TITLE = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.setString(1, title);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return "Product already exists";
            } else {
                  sql = "SELECT * FROM Shop where ID = (select max(ID) from Shop)";
                  try(PreparedStatement newStatement = connection.prepareStatement(sql)) {
                      ResultSet rs1 = newStatement.executeQuery();
                      int id = 0;
                      if (rs1.next())
                          id = rs1.getInt(1) + 1;

                      Random rand = new Random();
                      int prodid = rand.nextInt(10000 - 1000);
                      createNewData(connection, id, prodid, title, cost);
                  }
                  return "";
             }
        }
    }

    public static String delete(Connection connection, String title) throws SQLException {
        String sql = "DELETE FROM Shop WHERE TITLE = ?";

        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, title);
            if (statement.executeUpdate() == 0) {
                return "Record does not exist";
            }
            return "";
        }
    }

    public static ObservableList<Table> showAll(Connection connection) throws SQLException {
        String sql = "SELECT * FROM Shop";
        try(PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery()) {
            if (rs.isBeforeFirst())
                return selectData(rs);
            else {
                return null;
            }
        }
    }

    public static String price(Connection connection, String title) throws SQLException {
        String sql = "SELECT COST FROM Shop WHERE TITLE = ?";
        String str = "";
        try(PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.setString(1, title);
            ResultSet rs = statement.executeQuery();

            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    int cost = rs.getInt(1);
                    str = String.valueOf(cost) + '\n';
                }
                return str;
            }
            else {
                return "Such product does not exist";
            }
        }
    }

    public static String changePrice(Connection connection, String title, int newCost) throws SQLException {
        String sql = "UPDATE Shop SET COST = ? WHERE TITLE = ?";

        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, newCost);
            statement.setString(2, title);
            if (statement.executeUpdate() == 0) {
                return "Product is not changed";
            } else {
                return "";
            }
        }
    }

    public static ObservableList<Table> filterByPrice(Connection connection, int lhs, int rhs) throws SQLException {
        String sql = "SELECT * FROM Shop WHERE COST BETWEEN ? AND ?";
        try(PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.setInt(1, lhs);
            statement.setInt(2, rhs);
            ResultSet rs = statement.executeQuery();
            if (rs.isBeforeFirst())
                return selectData(rs);
            else
                return null;
        }
    }
}
