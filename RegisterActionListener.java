import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.*;

public class RegisterActionListener implements ActionListener{
    LoginGui gui = new LoginGui();
    User user = new User();

    String registrationMessage;
    boolean operationCancelled;

    public void actionPerformed(ActionEvent event){
        String email = gui.getEmailInput();
        char [] passwordInputArray = gui.getPasswordInput();
        JPasswordField confirmPasswordField = new JPasswordField();
        int confirmPasswordEntry = JOptionPane.showConfirmDialog(null, confirmPasswordField, "Please re-enter password",JOptionPane.OK_CANCEL_OPTION);
        
        if(confirmPasswordEntry<0||confirmPasswordEntry==JOptionPane.CANCEL_OPTION){
            registrationMessage = "Cancelled";
            operationCancelled = true;
        }
        else{
            boolean confirmPasswordCheck = Arrays.equals(passwordInputArray, confirmPasswordField.getPassword());
            if(confirmPasswordCheck){
                String password = passwordInputArray.toString();
                user.setPassword(password);
                registrationMessage = user.createAccount(0);
                System.out.println(registrationMessage);
            }
            else{
                registrationMessage="Passwords do not match";
                System.out.println(registrationMessage);
                user.setRegistrationSuccessCheck(false);
            }
        }
    }
}
