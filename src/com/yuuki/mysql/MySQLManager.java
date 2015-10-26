package com.yuuki.mysql;

import java.sql.*;
import java.util.ArrayList;

/**
 * @author Borja
 * @date 14/08/2015
 * @package com.yuuki.blackeye.mysql
 */
public class MySQLManager {
    private String DATABASE_HOST;
    private String DATABASE_USERNAME;
    private String DATABASE_PASSWORD;
    private String DATABASE_TABLE;

    /**
     * Set the values for the database connection. Also loads the MySQL driver.
     * @param DATABASE_HOST "localhost"
     * @param DATABASE_USERNAME "root"
     * @param DATABASE_PASSWORD " "
     * @param DATABASE_TABLE "table"
     * @throws ClassNotFoundException
     */
    public MySQLManager(String DATABASE_HOST, String DATABASE_USERNAME, String DATABASE_PASSWORD, String DATABASE_TABLE) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        this.DATABASE_HOST     = DATABASE_HOST;
        this.DATABASE_USERNAME = DATABASE_USERNAME;
        this.DATABASE_PASSWORD = DATABASE_PASSWORD;
        this.DATABASE_TABLE    = DATABASE_TABLE;
    }

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    private ArrayList<Object[]> selectData;

    public ArrayList<Object[]> getData() {
        return selectData;
    }

    //this will be used to check if a row exists
    private boolean exists;

    public boolean exists() {
        return exists;
    }

    /**
     * Executes any query in the MySQL server.
     *
     * Params is used to bind the necessary params to the query, for example
     *  String query = "INSERT INTO users (username, password) VALUES (?, ?)";
     *  Object[] params = {"Yuuki", "1234"};
     *  executeQuery(query, params);
     *
     * In case of "SELECT" queries it vary a bit. Params is used to tell the function the fields that you want to return in that query
     * Once the query it's done you can retrieve the data using 'getData()'
     *
     *  String query = "SELECT * FROM users";
     *  Object[] params = {"username", "password", "email"};
     *  executeQuery(query, params);
     *
     *  ArrayList<Object[]> data = getData();
     *
     *  //for read the data you can use foreach
     *  for(Object user : data) {
     *      //Remember the order or 'params'
     *      String username = (String) user[0];
     *      String password = (String) user[1];
     *      String email    = (String) user[2];
     *  }
     *
     * @param query Query to be executed
     * @param params Params of that query
     * @throws SQLException
     */
    public void executeQuery(String query, Object[] params) throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://" + DATABASE_HOST + ":3306/" + DATABASE_TABLE + "?user=" + DATABASE_USERNAME + "&password=" + DATABASE_PASSWORD);
        preparedStatement = connection.prepareStatement(query);

        //UPDATE table SET var='var' var1='var1' .. WHERE condition='condition'
        //INSERT INTO table (var, var1, ..) VALUES (?, ?, ?)
        bindParams(preparedStatement, params);

        if(query.split(" ")[0].equals("SELECT")) {
            resultSet = preparedStatement.executeQuery();

            exists = false;
            selectData = new ArrayList<>();
            while(resultSet.next()) {
                //activates the exists boolean (to check if a query returned something)
                exists = true;

                Object[] data = new Object[resultSet.getMetaData().getColumnCount()];
                for(int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    //i-1 because it starts at i = 1 cause resultSet
                    data[i - 1] = resultSet.getObject(i);
                }
                selectData.add(data);
            }
        } else {
            //For queries like UPDATE, INSERT
            preparedStatement.executeUpdate();
        }

        close();
    }

    /**
     * Bind all the params inside 'params' object array to the prepared statement
     * @param statement Created on executeQuery
     * @param params Params to bind
     * @throws SQLException
     */
    private void bindParams(PreparedStatement statement, Object[] params) throws SQLException {
        if(params == null) {
            return;
        }

        for (int i = 0; i < params.length; i++) {
            if(params[i] != null) {
                statement.setObject(i + 1, params[i]);
            } else {
                statement.setObject(i + 1, Types.OTHER);
            }
        }
    }

    /**
     * Closes the database connection
     */
    public void close() {
        try {
            if(connection != null) {
                connection.close();
            }

            if(preparedStatement != null) {
                preparedStatement.close();
            }

            if(resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}