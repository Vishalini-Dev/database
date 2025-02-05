package org.example;

import java.sql.*;

public class EmployeeJDBC {
    public static void main(String[] args) throws SQLException {
        // JDBC URL, username and password of the MySQL server
        String jdbcURL = "jdbc:mysql://localhost:3306/employee2";
        String username = "root";
        String password = "Jaya@1980";

        // SQL to create the Employee table
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Employee (" +
                "empcode INT PRIMARY KEY, " +
                "empname VARCHAR(50), " +
                "empage INT, " +
                "esalary INT)";

        // Query to insert data
        String insertSQL = "INSERT INTO Employee (empcode, empname, empage, esalary) VALUES (?, ?, ?, ?)";

        Statement statement = null;
        try {
            // Establishing the connection
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);

            // Create a Statement to execute the table creation query
            statement = connection.createStatement();

            // Execute the table creation query
            statement.execute(createTableSQL);
            System.out.println("Table Employee2 created or already exists.");

            // Prepare to insert data
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);

            // Data to be inserted
            int[][] employeeData = {
                    {101, 25, 10000},
                    {102, 30, 20000},
                    {103, 20, 40000},
                    {104, 40, 80000},
                    {105, 25, 90000}
            };
            String[] names = {"Jenny", "Jacky", "Joe", "John", "Shameer"};

            // Insert the data into the table
            for (int i = 0; i < employeeData.length; i++) {
                preparedStatement.setInt(1, employeeData[i][0]);  // empcode
                preparedStatement.setString(2, names[i]);         // empname
                preparedStatement.setInt(3, employeeData[i][1]);  // empage
                preparedStatement.setInt(4, employeeData[i][2]);  // esalary

                // Execute the update
                preparedStatement.executeUpdate();
            }

            System.out.println("Data inserted successfully!");

            //Display the table
            String selectSQL = "SELECT * FROM Employee";
            Statement selectStatement = connection.createStatement();
            ResultSet resultSet = selectStatement.executeQuery(selectSQL);

            System.out.println("empcode | empname | empage | esalary");
            while (resultSet.next()) {
                int empcode = resultSet.getInt("empcode");
                String empname = resultSet.getString("empname");
                int empage = resultSet.getInt("empage");
                int esalary = resultSet.getInt("esalary");

                System.out.println(empcode + " | " + empname + " | " + empage + " | " + esalary);
            }

            //close connection
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            statement.close();
        }
    }
}


