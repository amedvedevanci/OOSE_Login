import java.util.HashMap;
import java.io.*;

//class definition
public class User {
    //vars
    protected String username;
    protected String email;
    protected String password;
    protected int userType;
    protected String registrationMessage;
    protected String authenticationMessage;
    protected String resetPasswordMessage;

    boolean registrationSuccessful;
    boolean loginSuccessful;
    boolean resetPasswordSuccessful;

    //make name more meaningful, but apart from that keep filepath separate to allow it to be modified in future
    String filePath = "users.ser";

    //constructor
    public User(){
        userType=0; //0 will be Customer
    }

    //deserialize & get existing hashmap
    public HashMap getUsersHashMap(){
        HashMap<String, String> users;
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filePath))) {
                users = (HashMap<String, String>) inputStream.readObject();
                return users;
            } 

            //need to go back and fix exception handling
            catch (IOException | ClassNotFoundException e) {
                // If the file doesn't exist, create a new HashMap
                users = new HashMap<>();
                return users;
            }
        }

    //email set method
    public void setEmail(String email){
        this.email=email;
    }

    //password set method
    public void setPassword(String password){
        this.password=password;
    }
    
    //create account method
    /*This method takes in email String, password String, and userType int (0: Customer, 1: Secretary, 2: Mechanic, 3: GarageManager) 
    and creates an account.

    The username is created through concatenating userType and email (eg. 0customer@email.com).
    Therefore it is vital that any time this method is called, the correct userType is passed in.

    At the moment, null values are addressed by checking for 0 String length (to avoid storing any inputs that have an empty string).

    The method then returns String registrationMessage
    */
    public String createAccount(int userType){    
       HashMap<String,String> users= getUsersHashMap();

       username = userType+email;

        //check if email already exists in the system
        if(users.containsKey(username)){
            registrationMessage = "Account already exists";
            registrationSuccessful=false;
            setRegistrationSuccessCheck(registrationSuccessful);
        }

        //check if a String value exists for email and password. If either is null, requests to enter values for email and password
        else if(email.length() == 0 || password.length() == 0){
            registrationMessage = "Please enter values for email and password";
            registrationSuccessful=false;
            setRegistrationSuccessCheck(registrationSuccessful);
        }

        //otherwise register user and update the serialised Hashmap
        else{
            users.put(username,password);
            registrationMessage = "Registration successful";
            registrationSuccessful=true;
            setRegistrationSuccessCheck(registrationSuccessful);
            try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
                outputStream.writeObject(users);
            } 
            //Better error handling needed
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        return registrationMessage;
                
    }

    /*login method
    This method takes in email String, password String and userType int to log an existing user into the system.

    Null values in either email or password input are addressed again using .length() method.
    Also checking here if a user is trying to log in from the wrong app (basically the app methods pass in the userType according to the 
    relevant roles, hard-coded which is not ideal)
    Then password is checked against what is stored in the Hashmap.

    The method then returns String authenticationMessage.
    */
    public String login(int userType){
        HashMap<String,String> users= getUsersHashMap();

        username = userType + email;
        //if length is 0 ie String is empty, update authenticationMessage
        if(email.length() == 0 || password.length() == 0){
            authenticationMessage = "Please enter your credentials";
            loginSuccessful=false;
            setLoginSuccessCheck(loginSuccessful);
        }

        //if username is not found in the HashMap, but is found when the email is concatenated with a different int, ask user to login from correct app
        else if(!users.containsKey(username) && (users.containsKey(0+email) || users.containsKey((1+email)) || users.containsKey((2+email)) || users.containsKey((3+email)))){
            authenticationMessage = "Please use correct app to login";
            loginSuccessful=false;
            setLoginSuccessCheck(loginSuccessful);
        }

        //if username is not found at all, update authenticationMessage
        else if(!users.containsKey(username)){
            authenticationMessage = "No account found";
            loginSuccessful=false;
            setLoginSuccessCheck(loginSuccessful);
        }

        //return invalid password if email exists but does not match the password in the system
        else if(!users.get(username).equals(password)){
           authenticationMessage = "Invalid password";
           loginSuccessful=false;
           setLoginSuccessCheck(loginSuccessful);
        }

        //login user after validating correct email and password
        else{
            authenticationMessage = "Login successful";
            loginSuccessful=true;
            setLoginSuccessCheck(loginSuccessful);
        }

        return authenticationMessage;
    }

    public String resetPassword(){
        HashMap<String,String> users= getUsersHashMap();
        username=userType+email;

        //if the email exists within users and password String is not empty, update users HashMap
        //set successcheck to true and update resetPasswordMessage accordingly
        if(users.containsKey(username) && !password.equals("")){
            users.put(username, password);
            
            resetPasswordMessage="Password reset successfully. Please log in";
            resetPasswordSuccessful=true;
            setResetPasswordSuccessCheck(resetPasswordSuccessful);

            //serialize updated HashMap
            try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
                outputStream.writeObject(users);
            } 
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            //if account isn't found, print error message
            //TODO: go back and add handler for empty String (which shouldn't happen but what if)
            resetPasswordMessage="No account found. Please register";
            resetPasswordSuccessful=false;
            setResetPasswordSuccessCheck(resetPasswordSuccessful);
        }

        return resetPasswordMessage;
    }

    //update registrationSuccessful
    public void setRegistrationSuccessCheck(boolean registrationSuccessful){
        this.registrationSuccessful = registrationSuccessful;
    }
    //return registrationSuccessful
    public boolean getRegistrationSuccessCheck(){
        return registrationSuccessful;
    }

    //update loginSuccessful
    public void setLoginSuccessCheck(boolean loginSuccessful){
        this.loginSuccessful = loginSuccessful;
    }
    //return loginSuccessful
    public boolean getLoginSuccessCheck(){
        return loginSuccessful;
    }
    //update resetPasswordSuccessful
    public void setResetPasswordSuccessCheck(boolean resetPasswordSuccessful){
        this.resetPasswordSuccessful=resetPasswordSuccessful;
    }
    //return resetPasswordSuccessful
    public boolean getResetPasswordSuccessCheck(){
        return resetPasswordSuccessful;
    }
}
