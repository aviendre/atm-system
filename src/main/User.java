
package main;
/**
 *  User class contains all information needed for Username and PIN number in the ATM 
 */
public class User {

    private final String username;
    private String pin;

    /**
     * Constructor of the User object 
     * @param user username of the user
     * @param token userpin for the user
     */
    public User(String user,String token) {
        this.username = user;
        this.pin = token;
    }

    /**
     * Setter method for user PIN
     * @param token PIN number to change
     */
    protected void setPin(String token){
        this.pin = token;
    }
    
    /**
     * Getter for username and PIN number
     * @return (String) username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter for PIN number
     * @return (String) PIN number 
     */
    public String getPin() {
        return pin;
    }
}
