import java.sql.*;
import java.util.*;

public class RestaurantApp {

    static final String DB_URL = "jdbc:mysql://localhost:3306/restaurant";
    static final String USER = "root"; // 🔁 Replace with your username
    static final String PASS = "root"; // 🔁 Replace with your password

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> selectedIds = new ArrayList<>();

        try (
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();
        ) {
            System.out.println("======= WELCOME TO JAVA RESTAURANT =======");
            System.out.println("=============== MENU CARD ================");

            ResultSet rs = stmt.executeQuery("SELECT * FROM menu");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + ". " +
                        rs.getString("item_name") + " (" + rs.getString("category") + ") - ₹" +
                        rs.getDouble("price"));
            }

            System.out.println("\nEnter item IDs to order (type 0 to finish): ");
            int choice;
            while ((choice = scanner.nextInt()) != 0) {
                selectedIds.add(choice);
            }

            if (selectedIds.isEmpty()) {
                System.out.println("No items selected. Exiting...");
                return;
            }

            StringBuilder itemList = new StringBuilder();
            double totalPrice = 0;

            for (int id : selectedIds) {
                ResultSet itemRs = stmt.executeQuery("SELECT * FROM menu WHERE id = " + id);
                if (itemRs.next()) {
                    String itemName = itemRs.getString("item_name");
                    double price = itemRs.getDouble("price");
                    itemList.append(itemName).append(", ");
                    totalPrice += price;
                } else {
                    System.out.println("Item ID " + id + " not found.");
                }
            }

            String itemsOrdered = itemList.toString();
            if (itemsOrdered.endsWith(", ")) {
                itemsOrdered = itemsOrdered.substring(0, itemsOrdered.length() - 2);
            }

            System.out.println("\n============= BILL =============");
            System.out.println("Items: " + itemsOrdered);
            System.out.println("Total: ₹" + totalPrice);
            System.out.println("================================");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
