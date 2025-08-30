import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class AdminDashboard extends JFrame {
     JButton manageItemsButton;
    JButton viewBidsButton;
    JButton Logout;

    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        add(panel);
        Layout(panel);

        setVisible(true);
    }

     void Layout(JPanel panel){
        panel.setLayout(null);

        manageItemsButton = new JButton("Manage Items");
         manageItemsButton.setBounds(120, 50, 150, 25);
        panel.add(manageItemsButton);
        manageItemsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               try {
                new ManageItem();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            }
        });

        viewBidsButton = new JButton("View Bids");
        viewBidsButton.setBounds(120, 90, 150, 25);
        panel.add(viewBidsButton);
        viewBidsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ViewBids();
            }
        });
        
        Logout = new JButton("Logout");
        Logout.setBounds(120, 130, 150, 25);
        panel.add(Logout);
        Logout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               new LoginForm();
               dispose();
            }
        });
    }
}
