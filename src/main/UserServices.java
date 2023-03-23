package main;
import java.util.List;

/**
 * Handles all the user changing services under the ATM
 */
public class UserServices {

    /**
     * Changes all the PIN number of the given username as certain users will have multiple accounts
     * @param acc Account List to be manipulated
     * @param uname (String) Username of the user to change the PIN
     * @param token (String) User PIN to change to
     */
    public static void changeAllPin(List<Account> acc, String uname,String token){
        boolean success = false;
        int x = 0;
        for (Account a : acc ){
            if (uname.equals(a.getUsername())){
                x = a.changeUserPIN(token);
                if(x == 0){
                    success = true;
                }
            } else {
                continue;
            }
        }
        if(success)
            System.out.println("PIN NUMBER CHANGED SUCCESSFUL");
        else{
            System.out.println("PIN NUMBER CHANGED UNSUCCESSFUL");
            System.out.println(ATMException.throwError(x));
        }
    }
}
