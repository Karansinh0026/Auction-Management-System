import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDateTime;

 class AddItemForm extends JFrame {
     JTextField nameField;
     JTextArea descriptionField;
     JTextField startingPriceField;
     JTextField End_time;

    public AddItemForm() {
        setTitle("Add Item");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        add(panel);
        Layout(panel);

        setVisible(true);
    }

     void Layout(JPanel panel) {
        panel.setLayout(null);

        JLabel nameLabel = new JLabel("Item Name:");
        nameLabel.setBounds(10, 20, 80, 25);
        panel.add(nameLabel);

        nameField = new JTextField(20);
        nameField.setBounds(100, 20, 165, 25);
        panel.add(nameField);

        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setBounds(10, 60, 80, 25);
        panel.add(descriptionLabel);

        descriptionField = new JTextArea();
        descriptionField.setBounds(100, 60, 165, 25);
        panel.add(descriptionField);

        JLabel priceLabel = new JLabel("Starting Price:");
        priceLabel.setBounds(10, 110, 100, 25);
        panel.add(priceLabel);

        startingPriceField = new JTextField(20);
        startingPriceField.setBounds(100, 110, 165, 25);
        panel.add(startingPriceField);

        JLabel timeLabel=new JLabel("End Time(YYYY-MM_DD hh:mm:ss):");
        timeLabel.setBounds(10,150,100,25);
        panel.add(timeLabel);

        End_time =new JTextField();
        End_time.setBounds(100,150,165,25);
        panel.add(End_time);
        JButton addButton = new JButton("Add Item");
        addButton.setBounds(100, 200, 120, 25);
        panel.add(addButton);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addItem();
                
            }
        });
    }

     void addItem() {
        String name = nameField.getText();
        String description = descriptionField.getText();
        double startingPrice = Double.parseDouble(startingPriceField.getText());
       Timestamp time=Timestamp.valueOf(End_time.getText());
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO Items (name, description, starting_price, current_price,auction_end_time) VALUES (?, ?, ?, ?,?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setDouble(3, startingPrice);
            stmt.setDouble(4, startingPrice);
            stmt.setTimestamp(5, time);
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Item added successfully");
            new ManageItem();
            dispose();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
