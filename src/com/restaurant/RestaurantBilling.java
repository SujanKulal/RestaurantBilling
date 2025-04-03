package com.restaurant;
import java.sql.*;

public class RestaurantBilling {
    public static void main(String[] args) {
        // Database Credentials
        String url = "jdbc:mysql://localhost:3306/restaurant";
        String user = "root";
        String password = "root";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Connected to the database successfully!\n");

            displayMenu(conn);

            conn.close();
        } catch (Exception e) {
            System.out.println("❌ Database Connection failed!");
            e.printStackTrace();
        }
    }

    public static void displayMenu(Connection conn) throws SQLException {
        // Define the categories in the required order
        String[] categories = {"Indian", "Chinese", "Italian", "Mexican", "Fast Food", "Dessert", "Beverage"};

        for (String category : categories) {
            String query = "SELECT item_name, price FROM menu WHERE category = ? ORDER BY item_name";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, category);
            ResultSet rs = pstmt.executeQuery();

            // Display category header only if items exist
            boolean categoryDisplayed = false;
            while (rs.next()) {
                if (!categoryDisplayed) {
                    System.out.println("\n-- " + category + " --");
                    categoryDisplayed = true;
                }
                System.out.println(rs.getString("item_name") + " - ₹" + rs.getDouble("price"));
            }
        }
    }
}
