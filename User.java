//complete nested HashMap logic


import javax.swing.JOptionPane;
import java.util.HashMap;
import java.io.*;

//after removing testing printing blocks, no need to import these:
import java.util.Map;
import java.util.Set;
import java.util.Iterator;

//class definition
public class User {
    //vars
    private String username;
    private String email;
    private String password;
    private int userType;
    private String registrationMessage;
    private String authenticationMessage;

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

    //create account method
    /*This method takes in email String, password String, and userType int (0: Customer, 1: Secretary, 2: Mechanic, 3: GarageManager) 
    and creates an account.

    The username is created through concatenating userType and email (eg. 0customer@email.com).
    Therefore it is vital that any time this method is called, the correct userType is passed in.

    At the moment, null values are addressed by checking for 0 String length (to avoid storing any inputs that have an empty string).

    The method then returns String registrationMessage
    */
    
    public String createAccount(String email, String password, int userType){
       HashMap<String,String> users= getUsersHashMap();

       username = userType+email;

       //print HashMap for testing purposes Only. Remove this block of Code
       System.out.println("Deserialized HashMap..");
            // Display content using Iterator
            Set set = users.entrySet();
            Iterator iterator = set.iterator();
            while(iterator.hasNext()) {
                Map.Entry mentry = (Map.Entry)iterator.next();
                System.out.print("key: "+ mentry.getKey() + " & Value: ");
                System.out.println(mentry.getValue());
            }
        
        
        
        //check if email already exists in the system
        if(users.containsKey(username)){
            registrationMessage = "Account already exists";
        }

        //check if a String value exists for email and password. If either is null, requests to enter values for email and password
        else if(email.length() == 0 || password.length() == 0){
            registrationMessage = "Please enter values for email and password";
        }

        //otherwise register user and update the serialised Hashmap
        //TODO: remove the hashmap updated message
        else{
            users.put(username,password);
            registrationMessage = "Registration successful";
            try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
                outputStream.writeObject(users);
                System.out.println("HashMap updated and saved successfully!");

                // prints the HashMap again for testing purposes only, delete this
                Set set2 = users.entrySet();
                Iterator iterator2 = set2.iterator();
                while(iterator2.hasNext()) {
                    Map.Entry mentry2 = (Map.Entry)iterator2.next();
                    System.out.print("key: "+ mentry2.getKey() + " & Value: ");
                    System.out.println(mentry2.getValue());
                }

            } 
            //this catch basically only exists because I got an error message saying it had to. Better error handling needed.
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
    public String login(String email, String password, int userType){
        HashMap<String,String> users= getUsersHashMap();
        
        //print HashMap for testing purposes Only. Remove this block of code
        System.out.println("Deserialized HashMap..");
            // Display content using Iterator
            Set set = users.entrySet();
            Iterator iterator = set.iterator();
            while(iterator.hasNext()) {
                Map.Entry mentry = (Map.Entry)iterator.next();
                System.out.print("key: "+ mentry.getKey() + " & Value: ");
                System.out.println(mentry.getValue());
            }

        username = userType + email;
        //if length is 0 ie String is empty, update authenticationMessage
        if(email.length() == 0 || password.length() == 0){
            authenticationMessage = "Please enter your credentials";
        }

        //if username is not found in the HashMap, but is found when the email is concatenated with a different int, ask user to login from correct app
        else if(!users.containsKey(username) && (users.containsKey(0+email) || users.containsKey((1+email)) || users.containsKey((2+email)) || users.containsKey((3+email)))){
            authenticationMessage = "Please use correct app to login";
        }

        //if username is not found at all, update authenticationMessage
        else if(!users.containsKey(username)){
            authenticationMessage = "No account found";
        }

        //return invalid password if email exists but does not match the password in the system
        else if(!users.get(username).equals(password)){
           authenticationMessage = "Invalid password";
        }

        //login user after validating correct email and password
        else{
            authenticationMessage = "Login successful";
        }

        return authenticationMessage;
    }
}
