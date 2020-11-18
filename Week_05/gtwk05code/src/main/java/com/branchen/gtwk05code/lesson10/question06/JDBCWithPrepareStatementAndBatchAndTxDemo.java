package com.branchen.gtwk05code.lesson10.question06;

import java.sql.*;
import java.util.Arrays;

/**
 * 批处理,PreparedStatement,手动提交事务
 */
public class JDBCWithPrepareStatementAndBatchAndTxDemo {

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
        JDBCWithPrepareStatementAndBatchAndTxDemo.connection = connection;
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
        connection.setAutoCommit(false);
        String insertSql = "insert into t_person (name,score) values (concat('branchen',last_insert_id()), ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
        for (int i = 0; i < 300; i++) {
            preparedStatement.setInt(1, i);
            preparedStatement.addBatch();
        }    
        int[] rowArray = preparedStatement.executeBatch();
        connection.commit();
        System.out.println(Arrays.toString(rowArray));
        close(null, preparedStatement);
    }
}
