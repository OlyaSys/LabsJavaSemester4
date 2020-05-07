package jdbc;

import jdbc.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try(Connection connection = JDBCUtils.getNewConnection()) {
            JDBCUtils.createTable(connection);
            JDBCUtils.clearTable(connection);
            JDBCUtils.fillInTable(connection, 5);
            Scanner in = new Scanner(System.in);
            String cmd = in.nextLine();
            while(!cmd.equals("/finish")) {
                String[] arg = cmd.split(" ");
                if (arg.length == 1) {
                    if (arg[0].equals("/show_all")) {
                        JDBCUtils.showAll(connection);
                    }
                }
                else if (arg.length == 2) {
                    if (arg[0].equals("/delete")) {
                        JDBCUtils.delete(connection, arg[1]);
                    }

                    else if (arg[0].equals("/price")) {
                        JDBCUtils.price(connection, arg[1]);
                    }
                }

                else if (arg.length == 3) {
                    switch (arg[0]) {
                        case "/add":
                            JDBCUtils.add(connection, arg[1], Integer.parseInt(arg[2]));
                            break;
                        case "/change_price":
                            JDBCUtils.changePrice(connection, arg[1], Integer.parseInt(arg[2]));
                            break;
                        case "/filter_by_price":
                            JDBCUtils.filterByPrice(connection, Integer.parseInt(arg[1]), Integer.parseInt(arg[2]));
                            break;
                    }
                }

                in = new Scanner(System.in);
                cmd = in.nextLine();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
