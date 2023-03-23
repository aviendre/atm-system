package main;
import java.util.*;
import main.interfaces.*;

/**
 * Java Class file to manage all of the ATM Services; deposit, withdraw, money transfer, etc
 */
public class AccountServices implements Deposit<List<Account>>,Withdraw<List<Account>>,MoneyTransfer<List<Account>>,TransactionHistory<List<Transaction>>{
    private double withdrawMin = 20;
    private double minimumBalance = 1000;
    private double transferLimit = 400000000;

    /**
     * Returns the minimum balance for the ATM
     * @return (double) minimum balance value 
     */
    public double getMinimumBalance()
    {
        return this.minimumBalance;
    }

       /**
     * Returns the minimum withdrawn amount for the ATM
     * @return (double) minimum withdrawn value 
     */
    public double getWithdrawMin() {
        return withdrawMin;
    }

    /**
     * Returns the transfer limit for the ATM
     * @return (double) transfer limit value 
     */
    public double getTransferLimit()
    {
        return this.transferLimit;
    }
    
    /**
     * Setter for the transfer limit for the ATM
     * @param value (double) transfer limit value to set
     */
    public void setTransferLimit(double value)
    {
        this.transferLimit = value;
    }

    /**
     * Initialization step
     * Checks all past transaction for this ATM provided by the bank to store the
     * account balance.
     * @param acc Account list to be manipulated
     * @param trans Transaction list to be checked against
     */
    public void checkHistory(List<Account> acc, List<Transaction> trans) {
        for(Transaction t: trans){
            for (Account a: acc){
                if(a.getAccountNumber() == t.getAccNo() && t.getWithAmt() != 0){
                    a.addBalance(t.getWithAmt());
                }
                else if(a.getAccountNumber() == t.getAccNo() && t.getDepAmt() != 0){
                    a.minusBalance(t.getDepAmt());
                }
            }
        }
    }

    /**
     * Verifying the deposit amount and updating the account balance.
     * @param acc Account list to be manipulated
     * @param accNo (long) Account number to update balance
     * @param depAmt (double) value to deposit
     */
    public void deposit(List<Account> acc, long accNo, double depAmt){
        boolean hit = false;
        if (depAmt > 0){ //if deposit amount is a positive value
            for (Account a: acc){
                if (a.getAccountNumber() == accNo){
                    a.addBalance(depAmt);
                    hit = true;
                }
            }
            if (!hit) { //Account number does not exist
                System.out.println(ATMException.throwError(100)); 
            }
        }else{ //if deposit amount is a negative value
            System.out.println(ATMException.throwError(401));
        }
    }

    /**
     * Verifying the withdraw  amount and updating the account balance.
     * @param acc Account list to be manipulated
     * @param accNo (long) Account number to update balance
     * @param withAmt (double) value to withdraw
     */
    public void withdraw(List<Account>acc, long accNo, double withAmt){
        boolean hit = false;
        if (withAmt > getWithdrawMin()){ //if withdraw value is more than the withdraw minimum($20)
            for (Account a:acc){
                if (a.getAccountNumber() == accNo){
                    //if withdrawal amount is lesser than account balance and minimum balance is maintained after withdrawal proceed
                    if (withAmt < a.getAccountBalance() && a.getAccountBalance() - withAmt >= getMinimumBalance()){
                        a.minusBalance(withAmt);
                    } 
                    else {
                        //Insufficient Balance
                        System.out.println(ATMException.throwError(402));
                    }      
                    hit = true;      
                }
            }
            if (!hit) { //Account number does not exist
                System.out.println(ATMException.throwError(100)); 
            }
        }else 
            //withdrawal value is less than minimum value($20)
            System.out.println(ATMException.throwError(403));
        }

    /**
     * Handles the to and fro money transfer in between bank accounts in the System
     * @param acc Account List to be manipulated 
     * @param accFrom (long) Account number transferer
     * @param accTo (long) Account number receiver
     * @param amount (double) amount to be transferred
     */
    public void moneyTransfer(List<Account> acc, long accFrom, long accTo, double amount){
        boolean hitTo = false;
        boolean hitFrom = false;
        // amount is a negative/0 value OR if amount is more than transfer limit
        if (amount <= 0 || amount > transferLimit){
            //throw error
            System.out.println(ATMException.throwError(404));
        } else {  //meets the above requirements
            for (Account a:acc){
                //if accfrom is inside 'a'
                if (a.getAccountNumber() == accFrom){
                    //if the amount given more than the balance this user have in his account: (throw an error)
                    if (amount > a.getAccountBalance()){
                        ATMException.throwError(402);
                    } else { //means theres sufficient money inside
                        for(Account b : acc){
                            if (b.getAccountNumber() == accTo && hitTo == false){ //check if the account to Transfer is inside our database
                                b.addBalance(amount);
                                a.minusBalance(amount);
                                hitTo = true;
                            } else if(b.getAccountNumber() == accTo && hitTo == true){
                                a.minusBalance(amount);
                            }
                        }
                    }
                    hitFrom = true;
                }
            }
            if (!hitFrom || !hitTo) { //Account number for accFrom OR accTo does not exist
                System.out.println(ATMException.throwError(100)); 
            }
        }
    }

    /**
     * Returns the last 5 transaction history for chosen account number
     * @param trans Transaction list to be used
     * @param accNo (long) Account number transferer
     * @return List of last 5 transactions 
     */
    public List<Transaction> transactionHistory(List<Transaction> trans, long accNo ){
        List<Transaction> hist = new ArrayList<>();
        for (Transaction t:trans){
            if (t.getAccNo() == accNo){
                hist.add(t);    
            }
        }       
        return (hist.subList(hist.size() - 5, hist.size())); 
    }
}