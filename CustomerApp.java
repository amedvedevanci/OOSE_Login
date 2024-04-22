import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

public class CustomerApp {

    public static void main(String[] args){
        //first select login/signup
        //then select menu

        //vars
        int signUpLoginSelect;
        String email="";
        String password="";
        int userType = 0;

        String registrationMessage;
        String authenticationMessage;

        boolean operationCancelled = false;
        boolean registrationSuccessful=false;
        boolean loginSuccessful=false;

        //full menu to follow later
        int menuSelect;

        //declare objects
        JFrame frame =new JFrame();
        //instantiate User class

        User user = new User();

        String [] signUpLoginOptions = {"Register","Login"};
        signUpLoginSelect = JOptionPane.showOptionDialog(frame,"Please select","Register or Login",0,2,null,signUpLoginOptions,signUpLoginOptions[0]);
        
        
        //first check: set operationCancelled boolean to true if window is closed
        if(signUpLoginSelect==JOptionPane.CLOSED_OPTION){
            authenticationMessage="Cancelled";
            System.out.println(authenticationMessage);
            operationCancelled = true;
        }
        else{
            //second check: set operationCancelled boolean to true if email is not entered
            email = JOptionPane.showInputDialog("Enter your email address");
            if(email == null){
                authenticationMessage = "Cancelled";
                System.out.println(authenticationMessage);
                operationCancelled = true;
            }
            else{
                user.setEmail(email);
                //third check: set operationCancelled boolean to true if password is not entered
                JPasswordField passwordField = new JPasswordField();
                int passwordEntry = JOptionPane.showConfirmDialog(null, passwordField, "Enter your password",JOptionPane.OK_CANCEL_OPTION);
                if(passwordEntry<0||passwordEntry==JOptionPane.CANCEL_OPTION){
                    authenticationMessage = "Cancelled";
                    System.out.println(authenticationMessage);
                    operationCancelled = true;
                }
                else{
                    password = new String(passwordField.getPassword());
                    user.setPassword(password);
                    if(signUpLoginSelect==0){
                        registrationMessage = user.createAccount(userType);
                        System.out.println(registrationMessage);
                    }
                    else if(signUpLoginSelect==1){
                        authenticationMessage = user.login(userType);
                        System.out.println(authenticationMessage);
                    }
                }
            }
        }
    }
}