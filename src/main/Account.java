package main;

/**
 * Account child class to the User parent class. Contains all the information
 * needed for daily transactions and viewing of data from the ATM.
 */
public class Account extends User{
    private long accountNumber;
    private String accountName;
    private double accountBalance;

    /**
     * Constructor of the Account object 
     * @param n username of the user
     * @param p PIN number of the user
     * @param anumber account number of the bank account
     * @param aname account name of the bank account 
     * @param abalance account balance of the bank account
     */
    public Account(String n, String p, long anumber, String aname, double abalance){
        super(n,p);
        this.accountNumber = anumber;
        this.accountName = aname;
        this.accountBalance = abalance;
    }

    /**
     * Returns the account number of this object
     * @return (long) account number
     */
    public long getAccountNumber() {
        return this.accountNumber;
    }

    /**
     * Returns the account name of this object
     * @return (String) account name
     */
    public String getAccountName() {
        return this.accountName;
    }

    /**
     * Returns the account balance of this object
     * @return (double) account balance
     */
    public double getAccountBalance() {
        return this.accountBalance;
    }

    /**
     * Setter for the account balance
     * @param accountBalance (double) Value to set as the balance in this object
     */
    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    /**
     * Add value to the account balance of this object
     * @param amount (double) Value to add
     */
    public void addBalance(double amount) {
        this.accountBalance += amount;
    }

    /**
     * Minus value to the account balance of this object
     * @param amount (double) Value to minus
     */
    public void minusBalance(double amount) {
        this.accountBalance -= amount;
    }

    /**
     * Changes the user PIN and does verification for user input
     * @param token (String) PIN number to change user PIN
     * @return (int) returns the print error else 0
     */
    public int changeUserPIN(String token) {
        int x = changePINVerification(token);
        if ( x != 0){
            return x;           
        } else {
            super.setPin(token);
            return x;
        }
    }

    /**
     * Function to verify if the PIN number passed into the changeUserPIN() is able
     * to be used for modification 
     * @param PIN (String) PIN number to check 
     * @return (int) returns the print error else 0
     */
    private int changePINVerification(String PIN){
        String oldPin;
        oldPin = super.getPin();
        if (PIN.equals(oldPin)) {
            return 201;
        } else if(PIN.length() == 0 || PIN.length() < 4 || PIN.length() > 4) {
            return 200;
        } else {
            // new CSVparser().updatePINNumber(super.getUsername(), PIN);
            return 0;
        }
    }
}