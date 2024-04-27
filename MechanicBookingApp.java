import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.regex.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

public class MechanicBookingApp {
    public static void main(String[] args) {
        //vars
        int signUpLoginSelect;
        String email="";
        String password="";
        String userRole = "";
        int userType = 0;

        String registrationMessage;
        String authenticationMessage;

        boolean operationCancelled = false;
        boolean registrationSuccessful=false;
        boolean loginSuccessful=false;

        //full menu to follow later
        int menuSelect;
        String [] menuOptions = {"Exit","Create Employee Account"};

        //declare objects
        JFrame frame =new JFrame();
        
        //email pattern is attempted to be written according to RFC 3696, although backslash and char limit on local part are not addressed
        // https://datatracker.ietf.org/doc/html/rfc3696#section-3
        // https://www.asciitable.com/ 
        Pattern emailPattern = Pattern.compile("^[^\\.](?!.+@+@)(?!..+\\.\\.)[(#-\\&)(\\--9)(A-Z)(\\^-~)+!\\'=\\s](.+)[^\\.]@[^\\-](?!..+\\-\\-)(?=[\\w])([\\w.-]+\\.)+[a-z]{2,6}+$", Pattern.CASE_INSENSITIVE);

        //Password constraints: 8-64 chars, min 1 lowercase, min 1 uppercase, min 1 number, min 1 special character
        Pattern passwordPattern = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!-/:-@+_])[a-zA-Z0-9!-/:-@+_]{8,64}$");

        //instantiate User class
        User user = new User();

        do{
            String [] userTypes = {"Customer","Secretary","Mechanic","Garage Manager"};
            try{
            userRole = JOptionPane.showInputDialog(frame,"Please select your role","Welcome",JOptionPane.QUESTION_MESSAGE,null,userTypes,"Customer").toString();
            } catch (NullPointerException e){
                operationCancelled = true;
                System.exit(0);
            }

            switch (userRole) {
                case "Customer":
                    Customer customer = new Customer();
                    userType = customer.getUserType();
                    //break;
                case "Secretary":
                    Secretary secretary = new Secretary();
                    userType = secretary.getUserType();
                    //break;
                case "Mechanic":
                    Mechanic mechanic = new Mechanic();
                    userType = mechanic.getUserType();
                    //break;
                case "Garage Manager":
                    GarageManager manager = new GarageManager();
                    //userType = manager.getUserType();
                    //break;
            
                default: System.out.println("Cancelled");
                    //break;
            }
            
            String [] signUpLoginOptions = {"Register","Login"};
            signUpLoginSelect = JOptionPane.showOptionDialog(frame,"Please select","Register or Login",0,2,null,signUpLoginOptions,signUpLoginOptions[0]);
            //first check: set operationCancelled boolean to true if window is closed
            if(signUpLoginSelect==JOptionPane.CLOSED_OPTION){
                operationCancelled = true;
            }
            else{
                //second check: set operationCancelled boolean to true if email is not entered
                //addresses user clicks close here
                email = JOptionPane.showInputDialog("Enter your email address");
                if(email==null){
                    authenticationMessage = "Cancelled";
                    System.out.println(authenticationMessage);
                    operationCancelled = true;
                    System.exit(0);
                }
                Matcher emailMatcher = emailPattern.matcher(email);
                //check if email format matches email pattern
                if(!emailMatcher.matches()){
                    authenticationMessage = "Please provide valid email format";
                    System.out.println(authenticationMessage);
                }
                else{
                    user.setEmail(email);
                    //third check: set operationCancelled boolean to true if password is not entered
                    JPasswordField passwordField = new JPasswordField();
                    int passwordEntry = JOptionPane.showConfirmDialog(null, passwordField, "Enter your password",JOptionPane.OK_CANCEL_OPTION);
                    
                    password = new String(passwordField.getPassword());
                    Matcher passwordMatcher = passwordPattern.matcher(password);

                    if((passwordEntry==JOptionPane.CANCEL_OPTION)||(passwordEntry==JOptionPane.CANCEL_OPTION)){
                        operationCancelled = true;
                    }
                    else if(!passwordMatcher.matches()){
                        authenticationMessage = "Password must be 8-64 characters and contain at least one uppercase, one lowercase, one number and one special character";
                        System.out.println(authenticationMessage);
                    }
                    else{
                        if(signUpLoginSelect==0){
                            JPasswordField confirmPasswordField = new JPasswordField();
                            int confirmPasswordEntry = JOptionPane.showConfirmDialog(null, confirmPasswordField, "Please re-enter password",JOptionPane.OK_CANCEL_OPTION);
                            
                            if(confirmPasswordEntry<0||confirmPasswordEntry==JOptionPane.CANCEL_OPTION){
                                operationCancelled = true;
                            }
                            else{
                                boolean confirmPasswordCheck = Arrays.equals(passwordField.getPassword(), confirmPasswordField.getPassword());
                                if(confirmPasswordCheck){
                                    user.setPassword(password);
                                    registrationMessage = user.createAccount(userType);
                                    System.out.println(registrationMessage);
                                    registrationSuccessful=user.getRegistrationSuccessCheck();
                                }
                                else{
                                    registrationMessage="Passwords do not match";
                                    System.out.println(registrationMessage);
                                    user.setRegistrationSuccessCheck(false);
                                }
                            }
                        }
                        else if(signUpLoginSelect==1){
                            user.setPassword(password);
                            authenticationMessage = user.login(userType);
                            System.out.println(authenticationMessage);
                            loginSuccessful=user.getLoginSuccessCheck();
                        }
                    }
                }
            }
        }while(operationCancelled==false);
        if(operationCancelled){
            System.out.print("Cancelled");
            System.exit(0);
        }

        //menuSelect to follow later
        loginSuccessful=user.getLoginSuccessCheck();
        if(loginSuccessful){
            do{
                menuSelect = JOptionPane.showOptionDialog(null, "Please select a menu option", "Menu", 0, 1, null, menuOptions, menuOptions[0]);
                if((menuSelect==0)||(menuSelect==JOptionPane.CLOSED_OPTION)){
                    System.out.println("Cancelled");
                }
                else if(menuSelect == 1){
                    String [] userTypes = {"Customer","Secretary","Mechanic","Garage Manager"};
                    userType = JOptionPane.showOptionDialog(null, "Please select the type of account you wish to register", "Select account type", 0, 3, null, userTypes, userTypes[0]);
                    if(userType==JOptionPane.CLOSED_OPTION){
                        registrationMessage="Cancelled";
                        System.out.println(registrationMessage);
                    }
                    else{
                        email = JOptionPane.showInputDialog("Enter email address");
                        if(email==null){
                            registrationMessage="Cancelled";
                            System.out.println(registrationMessage);
                        }
                        else{
                            user.setEmail(email);
                            JPasswordField passwordField = new JPasswordField();
                            int passwordEntry = JOptionPane.showConfirmDialog(null, passwordField, "Enter your password",JOptionPane.OK_CANCEL_OPTION);
                            if(passwordEntry==JOptionPane.CLOSED_OPTION||passwordEntry==JOptionPane.CANCEL_OPTION){
                                registrationMessage = "Cancelled";
                                System.out.println(registrationMessage);
                            }
                            else{
                                password = new String(passwordField.getPassword());
                                user.setPassword(password);
                                registrationMessage = user.createAccount(userType);
                                System.out.println(registrationMessage);
                            }
                        }
                    }
                }
            }while(menuSelect>0);
        }
    }
}
