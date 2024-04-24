import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginGui {
    //components
    private JFrame frame = new JFrame("Mechanic Booking System");
    private JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    private JPanel inputFieldPanel = new JPanel(new GridBagLayout());
    private GridBagConstraints gbc = new GridBagConstraints();
    private JTextField usernameField = new JTextField(10);
    private JPasswordField passwordField = new JPasswordField(10);
    private JButton registerButton = new JButton("Register");
    private JButton loginButton = new JButton("Login");
    private JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

    //set up initial Frame
    public void initializeFrame(){
        frame.setSize(400,400);
        frame.setLayout(new GridLayout(3,0));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //add title to title panel
    public void fillTitlePanel(){
        titlePanel.add(new JLabel("Welcome to our garage"));
    }

    //create login form
    public void fillLoginForm(){

        //username prompt
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputFieldPanel.add(new JLabel("Enter username   "),gbc);

        //username field
        gbc.gridx = 1;
        gbc.gridy = 0;
        inputFieldPanel.add(usernameField, gbc);

        //password prompt
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputFieldPanel.add(new JLabel("Enter password   "),gbc);

        //password field
        gbc.gridx = 1;
        gbc.gridy = 1;
        inputFieldPanel.add(passwordField, gbc);
    }

    //button panel
    public void fillButtonPanel(){
        buttonPanel.add(registerButton);
        ActionListener registrationListener = new RegisterActionListener();
        registerButton.addActionListener(registrationListener);
        
        buttonPanel.add(loginButton);
        ActionListener loginListener = new LoginActionListener();
        loginButton.addActionListener(loginListener);
    }

    //add components into frame
    public void populateFrame(){
        initializeFrame();
        fillTitlePanel();
        fillLoginForm();
        fillButtonPanel();
        frame.add(titlePanel);
        frame.add(inputFieldPanel);
        frame.add(buttonPanel);
        frame.setVisible(true);
    }

    public String getEmailInput(){
        return usernameField.getText();
    }

    public char[] getPasswordInput(){
        return passwordField.getPassword();
    }
}
