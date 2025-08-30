import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

 class EditItem extends JFrame {
     JTextField itemIdField;
     JTextField itemNameField;
     JTextField startingPriceField;
     JTextArea descriptionArea;
     JButton saveButton, cancelButton;
     Connection connection;

    public EditItem( int itemId) {
        try {
            
         connection=DatabaseConnection.getConnection();
        setTitle("Edit Auction Item");
        setSize(400, 300);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5, 2));

        panel.add(new JLabel("Item ID:"));
        itemIdField = new JTextField();
        itemIdField.setEditable(false);                      
        panel.add(itemIdField);

        panel.add(new JLabel("Item Name:"));
        itemNameField = new JTextField();
        panel.add(itemNameField);

        panel.add(new JLabel("Starting Price:"));
        startingPriceField = new JTextField();
        panel.add(startingPriceField);

        panel.add(new JLabel("Description:"));
        descriptionArea = new JTextArea();
        panel.add(new JScrollPane(descriptionArea));

        loadItemDetails(itemId);

        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveItemDetails(itemId);
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    } catch (Exception e) {
        System.out.println(e);
    }
    }

    private void loadItemDetails(int itemId) {
        try {
            String query = "SELECT * FROM items WHERE item_id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, itemId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                itemIdField.setText(String.valueOf(rs.getInt("item_id")));
                itemNameField.setText(rs.getString("name"));
                startingPriceField.setText(String.valueOf(rs.getDouble("starting_price")));
                descriptionArea.setText(rs.getString("description"));
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading item details: ");
        }
    }

    private void saveItemDetails(int itemId) {
        try {
            String updateQuery = "UPDATE items SET name = ?, starting_price = ?, description = ? WHERE item_id = ?";
            PreparedStatement stmt = connection.prepareStatement(updateQuery);
            stmt.setString(1, itemNameField.getText());
            stmt.setDouble(2, Double.parseDouble(startingPriceField.getText()));
            stmt.setString(3, descriptionArea.getText());
            stmt.setInt(4, itemId);

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Item updated successfully!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error updating item.");
            }

            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error updating item details: ");
        }
    }

   
}
