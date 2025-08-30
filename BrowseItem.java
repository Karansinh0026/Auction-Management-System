import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

 class BrowseItemsForm extends JFrame {
    JTable itemsTable;
    JButton bidButton;
     String username;

    public BrowseItemsForm(String username) {
        this.username = username;

        setTitle("Browse Items");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Vector<Vector<Object>> data = getItemsData();
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Item ID");
        columnNames.add("Name");
        columnNames.add("Description");
        columnNames.add("Current Price");
        columnNames.add("End Time");

        itemsTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(itemsTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        bidButton = new JButton("Place Bid");
        buttonPanel.add(bidButton);
        add(buttonPanel, BorderLayout.SOUTH);

        bidButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = itemsTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int itemId = (int) itemsTable.getValueAt(selectedRow, 0);
                    new PlaceBid(itemId, username);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select an item to bid on.");
                }
            }
        });

        setVisible(true);
    }

    private Vector<Vector<Object>> getItemsData() {
        Vector<Vector<Object>> data = new Vector<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT item_id, name, description, current_price, auction_end_time FROM Items";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("item_id"));
                row.add(rs.getString("name"));
                row.add(rs.getString("description"));
                row.add(rs.getDouble("current_price"));
                row.add(rs.getTimestamp("auction_end_time"));
                data.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
