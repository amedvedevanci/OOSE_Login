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

    public String getUsername(String email){
        HashMap<String,String> users= getUsersHashMap();
        if(users.containsKey(0+email) || users.containsKey(1+email) || users.containsKey(2+email) || users.containsKey(3+email)){
            return username;
        }
        else{
            authenticationMessage = "Error fetching username";
            return authenticationMessage;
        }
    }


    //create account
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

        else if(email.length() == 0 || password.length() == 0){
            registrationMessage = "Please enter values for email and password";
        }

        //otherwise register user and update the serialised Hashmap
        else{
            users.put(username,password);
            registrationMessage = "Registration successful";
            try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
                outputStream.writeObject(users);
                System.out.println("HashMap updated and saved successfully!");

                //testing purposes only, delete this
                Set set2 = users.entrySet();
                Iterator iterator2 = set2.iterator();
                while(iterator2.hasNext()) {
                    Map.Entry mentry2 = (Map.Entry)iterator2.next();
                    System.out.print("key: "+ mentry2.getKey() + " & Value: ");
                    System.out.println(mentry2.getValue());
                }

            } 
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        return registrationMessage;
                
    }

    //login
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
        //return no account found if email does not exist in the system
        if(email.length() == 0 || password.length() == 0){
            authenticationMessage = "Please enter your credentials";
        }
        
        else if(!users.containsKey(username) && (users.containsKey(0+email) || users.containsKey((1+email)) || users.containsKey((2+email)) || users.containsKey((3+email)))){
            authenticationMessage = "Please use correct app to login";
        }
        
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
