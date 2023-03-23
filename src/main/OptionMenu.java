package main;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Main function of the program
 * Will call functions from different files to perform ATM functions
 */
public class OptionMenu {
    private String username;
    private String PIN;
    private boolean isLocked;
    private int accNum;
    private long currentAccount;
    private List<Double> balanceList;
    private List<Long> accountNumberList;

    private Scanner menuInput = new Scanner(System.in);
    private DecimalFormat moneyFormat = new DecimalFormat("'$'###,##0.00");
    private AccountServices s = new AccountServices();
    private List<Account> mainAccountList;
    private List<Transaction> oldTransactions;

    /**
     * Constructor of OptionMenu object
     * Transfer CSV account details to main account list
     */
    public OptionMenu(){
        CSVparser csv = new CSVparser();
        this.mainAccountList = csv.parseAccountDetails();//**Transfer CSV account details to main account list
        this.oldTransactions = csv.parseOldDataSet();
        this.accountNumberList = new ArrayList<>();
        this.balanceList = new ArrayList<>();
        isLocked = true;
        s.checkHistory(mainAccountList, oldTransactions);
    }
    
    /**
     * Login function to enable user to enter PIN & username
     * Account is locked by default till user credentials are satisfied
     * if PIN & username do not match, error is thrown and user is sent back to re-try login
     * Set username empty for every instance of login
     * Set PIN empty for every instance of login
     * Return to login to allow user to re-try
     */
    public void getLogin() throws IOException {
        try {
            isLocked = true;
            this.username ="";
            PIN= "";
            this.currentAccount = 0;
            System.out.print("Welcome to the ATM!\nEnter your Username: ");
            this.username = menuInput.next().toLowerCase();
            System.out.print("Enter your PIN Number: ");
            this.PIN = menuInput.next();
            System.out.println();

            for(Account a: mainAccountList){
                if(this.username.equals(a.getUsername()) && this.PIN.equals(a.getPin())) {
                    this.isLocked = false;
                    this.getAccountList();
                    this.getAccountType();
                    break;
                } else { continue; }
            }
        } catch (Exception e) {
            System.out.print(ATMException.throwError(200));//User input error
        }
        if (isLocked){
            System.out.println("Invalid Username or PIN, please try again.\n");
            this.getLogin();            //Return to login to allow user to re-try
            }
    }

    /**
     * Adds the row and set the headers for the whole table depending on the user
     * and account needed in the System. Updates dynamically
     */
    public void TablePrinter(){
        int x = 0;
        TablePrint st = new TablePrint();
        st.setHeaders("#", "Account Number", "User Name", "balanceList");
        st.setShowVerticalLines(true);
        for(Account a: mainAccountList){
            if(this.username.equals(a.getUsername())){
                st.addRow(Integer.toString(x), replace(Long.toString((a.getAccountNumber()))), a.getAccountName(),censor(Double.toString(a.getAccountBalance())));
        
               x++;
            } else {continue;}
        }
        st.print();
    }

    /**
    * Iterates through CSV file to get list of accounts under username
    * Adds Account Number and respective balanceList into an ArrayList
    */
    public void getAccountList(){
        for(Account a: mainAccountList){
            if(this.username.equals(a.getUsername())){
                this.accountNumberList.add(a.getAccountNumber());
                this.balanceList.add(a.getAccountBalance());
            } else {continue;}
        }
    }
    /**
     * Allows user to select what account to access
     * User can also choose to log out at this point
     * Once account selected, user will move on to access services for chosen account
     */
    
    public void getAccountType() {
        if(!this.isLocked) {
            TablePrinter();
            System.out.print("Enter 1000 to Log Out \nSelect the Account you Want to Access: ");

            try {
                int selection = menuInput.nextInt();
                System.out.println("\n");
                this.accNum = selection;
                if(selection == 1000){
                    System.out.println("Thank You for using this ATM, bye."); 
                    System.out.print("\n"); 
                    try {
                        this.getLogin();
                    } catch (IOException e1) {
                        ATMException.throwError(200);
                        e1.printStackTrace();
                    }
                } else {
                    getChecking(accNum);
                System.out.println();
                }
            } catch (InputMismatchException e) {
                System.out.println(ATMException.throwError(200));
                this.getAccountType();
            }
        }
    }
        
    /**
     * Allows users to select actions to perform on the selected account
     * After each function
     * @param accNum Array index of user's selected account
     */
    public void getChecking(int accNum) {
        this.currentAccount = this.accountNumberList.get(accNum);
        System.out.print("\n");
        System.out.println("Account details: "+ currentAccount);
        System.out.print("\n");
        System.out.println(" 1 - View Balance");
        System.out.println(" 2 - Withdraw Funds");
        System.out.println(" 3 - Deposit Funds");
        System.out.println(" 4 - Transfer");
        System.out.println(" 5 - Settings");
        System.out.println(" 6 - Transaction History");
        System.out.println(" 7 - Account Page");
        System.out.println("\n");
        System.out.print("Type options 1 - 7 for account service : ");
        int selection = menuInput.nextInt();
        System.out.println("\n");

        switch (selection) {
            case 1:
                this.balanceList.clear();
                this.accountNumberList.clear();
                getAccountList();
                System.out.println("Checking Account balanceList: " + moneyFormat.format(this.balanceList.get(accNum)));
                getChecking(accNum);
                break;
            case 2:
                System.out.print("Please enter the amount you wish to withdraw: ");
                double wAmt = menuInput.nextDouble();
                System.out.println();
                s.withdraw(mainAccountList,this.currentAccount,wAmt);
                getChecking(accNum);
                break;

            case 3:
                System.out.print("Please enter the amount you wish to deposit: ");
                double dAmt = menuInput.nextDouble();
                System.out.println();
                s.deposit(mainAccountList,this.currentAccount,dAmt);
                getChecking(accNum);
                break;

            case 4:
                MoneyTransfer();
                break;

            case 5:
                getSettings();
                break;
                
            case 6:
                for(Transaction t : s.transactionHistory(oldTransactions, this.currentAccount)){
                    System.out.println(t.toString());
                }
                System.out.println("\n");
                getChecking(accNum);
                break;

            case 7:
                this.getAccountList();
                this.getAccountType();
                break;

            default:
                System.out.println("\n" + "Invalid Choice." + "\n");
                getChecking(accNum);
                break;
        }
    }

    /**
     * Allow users to change their settings such as PIN number and withdrawal limit
     */
    public void getSettings(){
        System.out.println("Please select the available options: ");
        System.out.print("\n");
        System.out.println(" 1 - Change PIN");
        System.out.println(" 2 - Change Withdrawal Limit");
        System.out.println(" 3 - Exit");
        System.out.print("\n");
        
        System.out.print("Choice:");
        int selection = menuInput.nextInt();
        System.out.print("\n");
        
        switch (selection) {
            case 1:
                System.out.print("Please enter new PIN: ");
                String newPin = menuInput.next();//**Change PIN function
                System.out.print("\n");
                UserServices.changeAllPin(mainAccountList, this.username, newPin);
                System.out.println("Thank You for using this ATM, bye.");  
                System.out.print("\n");
                try {
                    this.getLogin();
                } catch (IOException e1) {
                    ATMException.throwError(200);
                    e1.printStackTrace();
                }
                break;

            case 2:
                System.out.println("Current limit: " + moneyFormat.format(s.getTransferLimit()));
                System.out.print("Please enter new Limit: ");
                s.setTransferLimit(menuInput.nextDouble());
                System.out.print("\n");
            case 3:
                this.getAccountList();
                this.getAccountType();
                break;

            default:
                System.out.println("\n" + "Invalid Choice." + "\n");
            }
    }

    /**
     * Allows you to perform money transfer to another account
     * If user chooses to exit at this point they will returned to getChecking
     */
    public void getTransfer() {
        System.out.print("\n");
        System.out.println("Please select the available options: ");
        System.out.print("\n");
        System.out.println(" Type 1 - Transfer");
        System.out.println(" Type 2 - Exit");
        System.out.print("Choice: ");
        int selection = menuInput.nextInt();
        System.out.print("\n");
        switch (selection) {
            case 1:
                MoneyTransfer();
                break;

            case 2:
                this.getAccountList();//**Return to initial account listing
                this.getAccountType();//**Return to initial account selection
                break;

            default:
                System.out.println("\n" + "Invalid Choice." + "\n");
                getChecking(accNum);//**Return to account services
        }
    }
            
    /**
     * User inputs receipient amount number and amount they wish to transfer
     * Gets method from AccountServices to perform the validation
     */
    public void MoneyTransfer(){
        System.out.print("Please enter receipient Account No. : ");
        long rAcc = menuInput.nextLong();
        System.out.print("\n");
        System.out.print("Please enter amount to transfer: ");
        double tAmt = menuInput.nextDouble();
        System.out.print("\n");
        s.moneyTransfer(mainAccountList,this.currentAccount,rAcc,tAmt);
        getChecking(accNum);//**Return to account services
    }

    /**
     * Replaces a set amount of characters with * and returns the censored text
     * @param s (String) input text to censor
     * @return (String) censored text
     */
    public static String replace(String s){
        return multiply("*",4) + s.substring(4, s.length()) ;
    }

    /**
     * Returns a string by a set amount of * to censor
     * @param a (String) input to censor text
     * @return (String) censored text
     */
    public static String censor(String a){
        return a.substring(a.length()) + "******************";
    }

    /**
     * Appends a string and multiply it by a value of n (e.g * x 4 becomes ****)
     * @param str (String) input string or char to multiply
     * @param n (int) value to multiply by
     * @return (String) the multipled string/char 
     */
    public static String multiply(String str, int n){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(str);
        }
        return sb.toString();
    }
}

