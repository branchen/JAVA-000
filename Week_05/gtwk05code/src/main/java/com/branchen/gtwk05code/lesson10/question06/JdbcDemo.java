package com.branchen.gtwk05code.lesson10.question06;

import java.sql.*;

/**
 * @author bran.chen
 * @description
 */
public class JdbcDemo {
    private static Connection connection;

    private static Connection createConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println();
        } catch (ClassNotFoundException e) {
            System.out.println("Can't find mysql jdbc driver");
            e.printStackTrace();
            return null;
        }

        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/wien_point2", "root", "root");
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
        return null;
    }

    public static Statement getStatement() throws Exception {
        Connection connection = createConnection();
        JdbcDemo.connection = connection;
        return connection.createStatement();
    }

    private static void close(ResultSet resultSet, Statement statement) throws SQLException {
        if (null != resultSet) {
            resultSet.close();
        }
        if (statement != null) {
            statement.close();
        }
        if (null != connection) {
            connection.close();
        }
        System.out.println("Connection close");
    }

    public static void main(String[] args) throws Exception {
        Statement statement = getStatement();
        // select
        String sql = "select id,name from t_area limit 10";
        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            System.out.println("id:" + id + "\t\tname: " + name);

        }

        // UPDATA
        sql = "update t_area set name='厦门市（TEST）' where id=350200";
        int rows = statement.executeUpdate(sql);
        if (rows > 0) {
            System.out.println("更新成功...");
        }
        // insert
        sql = "insert into t_area (id,name) values (concat('branchen',last_insert_id()), 100);";
        boolean result = statement.execute(sql);
        if (result) {
            System.out.println("新增数据成功");
        }

        // 关闭资源
        close(resultSet, statement);


    }
}
