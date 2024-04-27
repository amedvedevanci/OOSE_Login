import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

public class GarageManager extends User{
    private int menuSelect;
    private String [] menuOptions = {"Exit","Create Employee Account"};

    //constructor
    public GarageManager(){
        userType=3;
    }

    //return userType
    public int getUserType(){
        return userType;
    }

    //return menuOptions for this user type
    public String[] getMenuOptions(){
        return menuOptions;
    }

    //set menuSelect
    public void setMenuSelect(int menuSelect){
        this.menuSelect = menuSelect;
    }

    //menu function
    /*TODO: a lot of this still needs abstracted properly, methods need to be updated and in general 
    separated as for example create account interface is just repeated from previous instance.
    Need to add the additional handlers added previously, namely patterns, matchers, 
    alternatively separate those into their own classes which makes a lot more sense.
    
    Full menu to follow later. 
    */
    public void runMenu(){
        do{
            if((menuSelect==0)||(menuSelect==JOptionPane.CANCEL_OPTION)){
                System.out.println("Cancelled");
                System.exit(0);
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
                        setEmail(email);
                        JPasswordField passwordField = new JPasswordField();
                        int passwordEntry = JOptionPane.showConfirmDialog(null, passwordField, "Enter your password",JOptionPane.OK_CANCEL_OPTION);
                        if(passwordEntry==JOptionPane.CLOSED_OPTION||passwordEntry==JOptionPane.CANCEL_OPTION){
                            registrationMessage = "Cancelled";
                            System.out.println(registrationMessage);
                        }
                        else{
                            password = new String(passwordField.getPassword());
                            setPassword(password);
                            registrationMessage = createAccount(userType);
                            System.out.println(registrationMessage);
                        }
                    }
                }
            }
        }while(menuSelect>0);
    }
}