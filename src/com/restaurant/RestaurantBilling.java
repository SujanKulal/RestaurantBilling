package com.restaurant;
import java.sql.*;

public class RestaurantBilling {
    public static void main(String[] args) {
        //Database Credentials
        String url= "jdbc:mysql://localhost:3306/restaurant";
        String user = "root";
        String password = "root";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Loading MySql Driver
            Connection conn = DriverManager.getConnection(url,user,password);
            System.out.println("✅ Connected to the database successfully!");

            displayMenu(conn);

            conn.close();
        } catch (Exception e) {
            System.out.println("❌ Database Connection failed!");
            e.printStackTrace();
        }
    }
    public static void displayMenu(Connection conn) throws SQLException{
        String query = "SELECT category,item_name,price FROM menu ORDER BY category";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        String currentCategory = "";
        while (rs.next()){
            String category = rs.getString("category");
            if(!category.equals(currentCategory)){
                System.out.println("\n-- " + category + " --");
                currentCategory = category;
            }
            System.out.println(rs.getString("item_name") + " -₹" + rs.getDouble("price"));
        }
    }
}
