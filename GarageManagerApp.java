import javax.swing.JOptionPane;

public class GarageManagerApp {

    public static void main(String[] args){
        //first select login/signup
        //then select menu

        //vars
        int signUpLoginSelect;
        String email;
        String password;
        int userType = 3;

        String registrationMessage;
        String authenticationMessage;

        int menuSelect;

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
            System.out.println(authenticationMessage);
        }
        else{
            authenticationMessage = "Operation cancelled";
            System.out.println(authenticationMessage);
        }

        //menuSelect to follow later

        String [] menuOptions = {"Create Employee Account"};
        menuSelect = JOptionPane.showOptionDialog(null, "Please sekect a menu option", "Menu", 0, 1, null, menuOptions, menuOptions[0]);
    
        if(menuSelect == 0){
            String [] userTypes = {"Customer","Secretary","Mechanic","Garage Manager"};
            userType = JOptionPane.showOptionDialog(null, "Please select the type of account you wish to register", "Select account type", 0, 3, null, userTypes, userTypes[0]);
            email = JOptionPane.showInputDialog("Enter email address");
            password = JOptionPane.showInputDialog("Enter password");
            registrationMessage = user.createAccount(email, password, userType);
            System.out.println(registrationMessage);
        }
    }
}
