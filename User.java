import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

 class User extends JFrame {
     JButton browseItemsButton;
     JButton Logout;
     String username;

    public User(String username) {
        this.username = username;

        setTitle("User Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);

        setVisible(true);
    }

     void placeComponents(JPanel panel) {
        panel.setLayout(null);

        browseItemsButton = new JButton("Browse Items");
        browseItemsButton.setBounds(120, 50, 150, 25);
        panel.add(browseItemsButton);
        browseItemsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new BrowseItemsForm(username);
            }
        });

        Logout = new JButton("Logout");
        Logout.setBounds(120, 90, 150, 25);
        panel.add(Logout);
        Logout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               new LoginForm();
            }
        });
    }
}
