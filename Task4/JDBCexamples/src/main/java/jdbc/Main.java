package jdbc;

import jdbc.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try(Connection connection = JDBCUtils.getNewConnection()) {
            JDBCUtils.clearTable(connection);
            JDBCUtils.fillInTabe(connection, 5);
            if (args[0].equals("/add")) {
                JDBCUtils.add(connection, args[1], Integer.parseInt(args[2]));
            }

            if (args[0].equals("/delete")) {
                JDBCUtils.delete(connection, args[1]);
            }

            if (args[0].equals("/show_all")) {
                JDBCUtils.showAll(connection);
            }

            if (args[0].equals("/price")) {
                JDBCUtils.price(connection, args[1]);
            }

            if (args[0].equals("/change_price")) {
                JDBCUtils.changePrice(connection, args[1], Integer.parseInt(args[2]));
            }

            if (args[0].equals("/filter_by_price")) {
                JDBCUtils.filterByPrice(connection, Integer.parseInt(args[1]), Integer.parseInt(args[2]));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
