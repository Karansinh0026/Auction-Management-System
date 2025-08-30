import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.*;

 class ManageItem extends JFrame {
     JTable itemsTable;
     JButton addButton, editButton, deleteButton;

    public ManageItem() {
        setTitle("Manage Items");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Vector<Vector<Object>> data = getItemsData();
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Item ID");
        columnNames.add("Name");
        columnNames.add("Description");
        columnNames.add("Starting Price");
        columnNames.add("Current Price");
        columnNames.add("End Time");

        itemsTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(itemsTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add Item");
        editButton = new JButton("Edit Item");
        deleteButton = new JButton("Delete Item");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AddItemForm();
            }
        });

        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = itemsTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int itemId = (int) itemsTable.getValueAt(selectedRow, 0);
                    new EditItem(itemId);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select an item to edit.");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = itemsTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int itemId = (int) itemsTable.getValueAt(selectedRow, 0);
                    deleteItem(itemId);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select an item to delete.");
                }
            }
        });

        setVisible(true);
    }

     Vector<Vector<Object>> getItemsData() {
        Vector<Vector<Object>> data = new Vector<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM Items";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("item_id"));
                row.add(rs.getString("name"));
                row.add(rs.getString("description"));
                row.add(rs.getDouble("starting_price"));
                row.add(rs.getDouble("current_price"));
                row.add(rs.getTimestamp("auction_end_time"));
                data.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

     void deleteItem(int itemId) {
        try (Connection conn = DatabaseConnection.getConnection()) {

            String query = "DELETE FROM items where item_id=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, itemId);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Item deleted successfully.");
            dispose();
            new ManageItem();  
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
