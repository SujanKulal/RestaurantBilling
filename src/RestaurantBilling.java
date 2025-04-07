import java.util.*;
import java.sql.*;
public class RestaurantBilling {
    static final String url = "jdbc:mysql://localhost:3306/restaurant";
    static final String user="root";
    static final String pass = "root";

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        List<Integer> selectedIds = new ArrayList<>();

        try {
            Connection con = DriverManager.getConnection(url,user,pass);
            Statement st = con.createStatement();
            System.out.println("===== WELCOME TO MY APP =====");
            System.out.println("===== Here's Our Menu =====");

            ResultSet set = st.executeQuery("SELECT * FROM menu");
            while (set.next()){
                System.out.println(set.getInt("id") +"."+
                        set.getString("item_name") + "(" +
                        set.getString("category") +")" + " - Rs." +
                        set.getDouble("price"));
            }
            int choice;
            System.out.println("=== Selected Item id, enter 0 after selecting ===");
            while ((choice =sc.nextInt()) != 0){
                selectedIds.add(choice);
            }
            if(selectedIds.isEmpty()){
                System.out.println("No items selcted, Exiting....");
                return;
            }
            StringBuilder itemList = new StringBuilder();
            double total =0;
            double price = 0;

            for(int id : selectedIds){
                ResultSet items = st.executeQuery("SELECT * FROM menu WHERE id = " + id);
                if (items.next()){
                    String itemName = items.getString("item_name");
                    price = items.getDouble("price");
                    itemList.append(itemName).append("(Rs:" +price+")").append(",");
                    total +=price;
                }else{
                    System.out.println("Item_id: " + id + " not found");
                }
            }

            System.out.println("===== BILL =====");
            System.out.println("Selected Items: " +itemList );
            System.out.println("Total Price: " + total);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
