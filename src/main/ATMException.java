package main;

/**
 * Handling of all the ATM errors and exception throws to print in the CLI.
 * Compile all error codes into a singular file to modify
 */
public class ATMException {

    /**
     * Function to return a error code based on the int value of the error
     * @param a
     * @return
     */
    public static String throwError(int a){
        switch (a) {
            case 100:
                return("100: Initialization Database not found");
            case 101:
                return("100: Initialization Database IO error");
            case 200:
                return("200: USER INPUT ERROR");
            case 201:
                return("200: CANT CHANGE PIN TO OLD PIN");
            case 400:
                return("400: Wrong input from user");
            case 401:
                return("401: Unable to deposit negative values");
            case 402:
                return("402: Insufficient Balance");
            case 403:
                return("403: Withdraw amount too little.");
            default:
                return("Error");
        }
    }
}
