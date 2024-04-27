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
        String resetPasswordMessage;

        boolean operationCancelled = false;
        boolean registrationSuccessful=false;
        boolean loginSuccessful=false;
        boolean resetPasswordSuccessful;

        //full menu to follow later
        int menuSelect;

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

        //the loop around the application will run unless operation is cancelled or login is successful
        do{
            //userRole selection screen
            String [] userTypes = {"Customer","Secretary","Mechanic","Garage Manager"};
            try{
            userRole = JOptionPane.showInputDialog(frame,"Please select your role","Welcome",JOptionPane.QUESTION_MESSAGE,null,userTypes,"Customer").toString();
            } catch (NullPointerException e){
                operationCancelled = true;
                System.exit(0);
            }

            //define userType based on userRole selection
            switch (userRole) {
                case "Customer":
                    Customer customer = new Customer();
                    userType = customer.getUserType();
                    break;
                case "Secretary":
                    Secretary secretary = new Secretary();
                    userType = secretary.getUserType();
                    break;
                case "Mechanic":
                    Mechanic mechanic = new Mechanic();
                    userType = mechanic.getUserType();
                    break;
                case "Garage Manager":
                    GarageManager manager = new GarageManager();
                    userType = manager.getUserType();
                    break;
            
                default: System.out.println("Cancelled");
                    //break;
            }
            
            //sign up or login selection screen
            String [] signUpLoginOptions = {"Register","Login"};
            signUpLoginSelect = JOptionPane.showOptionDialog(frame,"Please select",userRole,0,2,null,signUpLoginOptions,signUpLoginOptions[0]);
            //set operationCancelled boolean to true if window is closed
            if(signUpLoginSelect==JOptionPane.CLOSED_OPTION){
                operationCancelled = true;
            }
            else{
                //set operationCancelled boolean to true if email is not entered
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
                    //if email is valid, pass it to user class
                    user.setEmail(email);

                    //initialize password input
                    JPasswordField passwordField = new JPasswordField();
                    int passwordEntry = JOptionPane.showConfirmDialog(null, passwordField, "Enter your password",JOptionPane.OK_CANCEL_OPTION);
                    
                    password = new String(passwordField.getPassword());
                    Matcher passwordMatcher = passwordPattern.matcher(password);

                    //set operationCancelled to true if window is closed/cancel button pressed
                    if((passwordEntry==JOptionPane.CANCEL_OPTION)||(passwordEntry==JOptionPane.CANCEL_OPTION)){
                        operationCancelled = true;
                    }

                    //return error if password format does not match constraints.
                    //TODO: add support for non-Latin alphabet characters
                    //TODO: amend error message to display which symbols are permitted
                    else if(!passwordMatcher.matches()){
                        authenticationMessage = "Password must be 8-64 characters and contain at least one uppercase, one lowercase, one number and one special character";
                        System.out.println(authenticationMessage);
                    }

                    //if Register button was clicked previously, ask user to re-enter password
                    else{
                        if(signUpLoginSelect==0){
                            JPasswordField confirmPasswordField = new JPasswordField();
                            int confirmPasswordEntry = JOptionPane.showConfirmDialog(null, confirmPasswordField, "Please re-enter password",JOptionPane.OK_CANCEL_OPTION);
                            
                            //set operationCancelled to true if window is closed or cancel button is pressed
                            if(confirmPasswordEntry<0||confirmPasswordEntry==JOptionPane.CANCEL_OPTION){
                                operationCancelled = true;
                            }

                            else{
                                //compare password char[] and confirmPassword char[]
                                boolean confirmPasswordCheck = Arrays.equals(passwordField.getPassword(), confirmPasswordField.getPassword());
                                
                                //call createAccount() if inputs match
                                if(confirmPasswordCheck){
                                    user.setPassword(password);
                                    registrationMessage = user.createAccount(userType);
                                    System.out.println(registrationMessage);
                                    registrationSuccessful=user.getRegistrationSuccessCheck();
                                }

                                //return error if inputs do not match
                                else{
                                    registrationMessage="Passwords do not match";
                                    System.out.println(registrationMessage);
                                    user.setRegistrationSuccessCheck(false);
                                }
                            }
                        }

                        //if user selected login previously, call login method and check if login was successful
                        else if(signUpLoginSelect==1){
                            user.setPassword(password);
                            authenticationMessage = user.login(userType);
                            System.out.println(authenticationMessage);
                            loginSuccessful=user.getLoginSuccessCheck();

                            //if login was unsuccessful, show unsuccessful login options screen
                            if(loginSuccessful==false){
                                String [] loginOptions = {"Register","Try again","Reset password"};
                                int unsuccessfulLoginChoice = JOptionPane.showOptionDialog(frame, authenticationMessage+"\r\nWhat would you like to do?", "Cannot login", 0, 2, null, loginOptions, loginOptions[0]);
                                
                                //set operationCancelled to true if window is closed
                                if(unsuccessfulLoginChoice==JOptionPane.CLOSED_OPTION){
                                    operationCancelled=true;
                                }
                                else if(unsuccessfulLoginChoice==0 || unsuccessfulLoginChoice ==1){
                                    operationCancelled=false;
                                }

                                //if reset password is selected, update password in User. display password field
                                else if(unsuccessfulLoginChoice==2){
                                    JPasswordField resetPasswordField = new JPasswordField();
                                    int resetPasswordEntry = JOptionPane.showConfirmDialog(null, resetPasswordField, "Enter your password",JOptionPane.OK_CANCEL_OPTION);
                    
                                    String resetPassword = new String(resetPasswordField.getPassword());

                                    //set operationCancelled to true if window is closed or cancel button pressed
                                    if((resetPasswordEntry==JOptionPane.CANCEL_OPTION)||(resetPasswordEntry==JOptionPane.CANCEL_OPTION)){
                                        operationCancelled = true;
                                    }

                                    //return error message if password input does not match regex constraints
                                    //TODO: add support for non-Latin alphabet characters
                                    //TODO: amend error message to display which symbols are permitted
                                    else if(!passwordMatcher.matches()){
                                        resetPasswordMessage = "Password must be 8-64 characters and contain at least one uppercase, one lowercase, one number and one special character";
                                        System.out.println(resetPasswordMessage);
                                    }

                                    //call resetPassword() if password passes validation check
                                    else{
                                        user.setPassword(resetPassword);
                                        resetPasswordMessage = user.resetPassword();
                                        System.out.println(resetPasswordMessage);
                                        
                                        resetPasswordSuccessful = user.getResetPasswordSuccessCheck();

                                        //redundant for now, setting this up for future customization
                                        if(resetPasswordSuccessful){
                                            operationCancelled=false;
                                        }
                                        else{
                                            operationCancelled=false;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }while(operationCancelled==false && loginSuccessful==false);
        
        //if operationCancelled becomes true in the method above, display Cancelled message and exit application
        if(operationCancelled){
            System.out.print("Cancelled");
            System.exit(0);
        }

        //check if user is logged in
        loginSuccessful=user.getLoginSuccessCheck();
        if(loginSuccessful){

            //if user is logged in, display Welcome screen
            JOptionPane.showMessageDialog(null,"Welcome "+userRole);
            
            //full menu options and customization to follow later
            //initial setup and example (shortened) menu for authorization handling
            switch (userRole) {
                case "Customer":
                    break;
                case "Secretary":
                    break;
                case "Mechanic":
                    break;
                case "Garage Manager":
                    GarageManager manager = new GarageManager();
                    String [] menuOptions = manager.getMenuOptions();
                    menuSelect = JOptionPane.showOptionDialog(null, "Please select a menu option", "Menu", 0, 1, null, menuOptions, menuOptions[0]);
                    manager.setMenuSelect(menuSelect);
                    manager.runMenu();
                    break;
                default: JOptionPane.showMessageDialog(null, "Unable to determine role");
                    break;
            }
        }
    }
}
