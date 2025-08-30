import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.sql.*;

 class ViewBids extends JFrame {
    private JTable bidsTable;

    public ViewBids() {
        setTitle("View Bids");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Vector<Vector<Object>> data = getBidsData();
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Bid ID");
        columnNames.add("Item Name");
        columnNames.add("User");
        columnNames.add("Bid Amount");
        columnNames.add("Bid Time");

        bidsTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(bidsTable);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

     Vector<Vector<Object>> getBidsData() {
        Vector<Vector<Object>> data = new Vector<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT Bids.id, Items.name, Users.username, Bids.bid_amount, Bids.bid_time FROM Bids JOIN Items ON Bids.item_id = Items.item_id JOIN Users ON Bids.user_id = Users.id" ;
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("id"));
                row.add(rs.getString("name"));
                row.add(rs.getString("username"));
                row.add(rs.getDouble("bid_amount"));
                row.add(rs.getTimestamp("bid_time"));
                data.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
