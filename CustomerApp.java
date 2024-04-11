import javax.swing.JOptionPane;

public class CustomerApp {
    public static void main(String[] args){
        //first select login/signup
        //then select menu

        //vars
        int signUpLoginSelect;
        String email;
        String password;
        int userType = 0;

        String registrationMessage;
        String authenticationMessage;

        //instantiate User class

        User user = new User();

        String [] signUpLoginOptions = {"Register","Login"};
        signUpLoginSelect = JOptionPane.showOptionDialog(null,"Please select","Register or Login",0,2,null,signUpLoginOptions,signUpLoginOptions[0]);
        email = JOptionPane.showInputDialog("Enter your email address");
        password = JOptionPane.showInputDialog("Enter your password");

        if(signUpLoginSelect==0){
            registrationMessage = user.createAccount(email,password,userType);
            System.out.println(registrationMessage);
        }
        else if(signUpLoginSelect==1){
            authenticationMessage = user.login(email,password,userType);
            while(!(authenticationMessage.equals("Login successful"))){
                String [] loginOptions = {"Try again","Register","Reset password"};
                int loginFailSelect = JOptionPane.showOptionDialog(null, "Login failed. Try again?", "Try again?", 0, 1, null, loginOptions, loginOptions[0]);
                
                if(loginFailSelect == 0){
                    email = JOptionPane.showInputDialog("Enter your email address");
                    password = JOptionPane.showInputDialog("Enter your password");
                    authenticationMessage = user.login(email, password, userType);
                }
                else if(loginFailSelect == 1){
                    email = JOptionPane.showInputDialog("Enter your email address");
                    password = JOptionPane.showInputDialog("Enter your password");
                    registrationMessage = user.createAccount(email, password, userType);
                }
                else if(loginFailSelect == 2){
                    //reset password
                }
                else{
                    System.exit(0);
                }
                
            }
            System.out.println(authenticationMessage);
        }
        else{
            authenticationMessage = "Operation cancelled";
            System.out.println(authenticationMessage);
        }

        //menuSelect to follow later
    }
}
