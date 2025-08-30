import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

 class main{
     JFrame jframe;
     JButton Login,SignUp;
     JPanel jPanel;
     main() {                                                              
        jframe =new JFrame("Main Interface");
        jframe.setSize(400,400);
        jPanel =new JPanel();
        jframe.add(jPanel);
        Login=new JButton("Login");
        Login.setBounds(110,50,150,25);
        Login.addActionListener(new ActionListener() {

            
            public void actionPerformed(ActionEvent e) {
            new LoginForm();  
            jframe.dispose();              
            }
            
        });
         SignUp=new JButton("SignUp");
        SignUp.setBounds(110,90,150,25);
        SignUp.addActionListener(new ActionListener() {

            
            public void actionPerformed(ActionEvent e) {
                new Signup();
                jframe.dispose();
                
            }
            
            
        });
        
        
        jPanel.add(Login);
        jPanel.add(SignUp);
        jPanel.setLayout(new BorderLayout());
        jframe.setVisible(true);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public static void main(String[] args) {
      new  main();
    }
    

}
