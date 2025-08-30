import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

 class PlaceBid extends JFrame {
     JTextField bidAmountField;
     int itemId;
     String username;

    public PlaceBid(int itemId, String username) {
        this.itemId = itemId;
        this.username = username;

        setTitle("Place Bid");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(new JLabel("Enter your bid amount:"));
        bidAmountField = new JTextField();
        panel.add(bidAmountField);

        JButton placeBidButton = new JButton("Place Bid");
        panel.add(placeBidButton);

        placeBidButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                placeBid();
            }
        });

        add(panel);
        setVisible(true);
    }

     void placeBid() {
        double bidAmount = Double.parseDouble(bidAmountField.getText());

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT current_price FROM Items WHERE item_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, itemId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                double currentPrice = rs.getDouble("current_price");
                if (bidAmount > currentPrice) {
                    String insertQuery = "INSERT INTO Bids (item_id, user_id, bid_amount, bid_time) " +
                                         "VALUES (?, (SELECT id FROM Users WHERE username = ?), ?, NOW())";
                    PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
                    insertStmt.setInt(1, itemId);
                    insertStmt.setString(2, username);
                    insertStmt.setDouble(3, bidAmount);
                    insertStmt.executeUpdate();

                    String updateQuery = "UPDATE Items SET current_price = ? WHERE item_id = ?";
                    PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                    updateStmt.setDouble(1, bidAmount);
                    updateStmt.setInt(2, itemId);
                    updateStmt.executeUpdate();

                    JOptionPane.showMessageDialog(this, "Bid placed successfully!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Your bid must be higher than the current price.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
