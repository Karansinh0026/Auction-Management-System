import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Signup extends JFrame{
    
     JPanel jpanel;
     JLabel Message,Username,password,Email;
     JTextField username,Password,email;
     JButton Submit;

    Signup(){
        setName("SignUp");
        setSize(400,400);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jpanel=new JPanel();
        add(jpanel);
        jpanel.setLayout(null);
        jpanel.setVisible(true);
        Layout(jpanel);

    }

    void Layout(JPanel jpanel){
        Message =new JLabel("Enter your Details");
        Message.setBounds(140,5,250, 30);
        Message.setFont(getFont().deriveFont(1));
        Message.setForeground(Color.GRAY);
        jpanel.add(Message);
        Username =new JLabel("Username :");
        Username.setBounds(40,60,150,25);
        jpanel.add(Username);
        password =new JLabel("Password :");
        password.setBounds(40,90,150,25);
        jpanel.add(password);
        Email =new JLabel("Email-id :");
        Email.setBounds(40,120,150,25);
        jpanel.add(Email);

        username=new JTextField();
        username.setBounds(150,60,150,25);
        jpanel.add(username);
        Password =new JTextField();
        Password.setBounds(150,90,150,25);
        jpanel.add(Password);
        email=new JTextField();
        email.setBounds(150,120, 150, 25);
        jpanel.add(email);

        Submit=new JButton("Submit");
        Submit.setBounds(120,190,100,25);
        Submit.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String user=username.getText();
                String password=Password.getText();
                String mail=email.getText();

                if(user.isEmpty()){
                    JOptionPane.showMessageDialog(jpanel, "Username cannot be empty");
                }
                else if(password.isEmpty()){
                    JOptionPane.showMessageDialog(jpanel, "Password cannot be empty");
                }
               else  if(mail.isEmpty()){
                    JOptionPane.showMessageDialog(jpanel, "Email cannot be empty");
                }
              else { try(Connection con=DatabaseConnection.getConnection()) {
                    String check="select username from users";
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery(check);
            while(rs.next()){
                if(user.equals(rs.getString("username"))){
                    JOptionPane.showMessageDialog(jpanel, "user already Exits");
                }
            }
                } catch (Exception exception) {
                    System.out.println(exception);
                }
                Insert(user, password, mail);
            }
            
            
        }
    }
    );
        jpanel.add(Submit);


    }

    void Insert(String username,String password,String email){
        try {
            Connection con=DatabaseConnection.getConnection();

           String insert="insert into users(username,password,email,role) values(?,?,?,'USER')";
           PreparedStatement pst=con.prepareStatement(insert);
           pst.setString(1,username);
           pst.setString(2, password);
           pst.setString(3,email);

           int x=pst.executeUpdate();
           if(x>0){
            JOptionPane.showMessageDialog(jpanel, "Sign Up sucess");
            dispose();
            new main();

           }
        } catch (Exception e) {
           System.out.println(e);
        }    }
}
