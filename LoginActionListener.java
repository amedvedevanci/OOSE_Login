import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.*;

public class LoginActionListener implements ActionListener {
    LoginGui gui = new LoginGui();
    User user = new User();

    public void actionPerformed(ActionEvent event){
        String email = gui.getEmailInput();
        char [] passwordInputArray = gui.getPasswordInput();
        String password = passwordInputArray.toString();
        user.setEmail(email);
        user.setPassword(password);
        String authenticationMessage = user.login(0);
        System.out.println(authenticationMessage);
    }
}
