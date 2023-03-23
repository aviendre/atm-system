package main;
import java.time.*;
/** 
* This class is the object holder for every transaction detail that will be extracted from the CSV file. All variables will be 
* only accesible within this class file. Getters() are used to retrieved each data set when needed.
*/
public class Transaction {
    private final long accNo,cheqNo;
    private final LocalDate date, valDate;
    private final String transDets;
    private final double withAmt,depAmt;
    
    /**
     * Constructor for the Transaction object. Have to declare all the parameters else no transaction will be created
     * @param accNo (long) account number
     * @param date (java.time.LocalDate) date of the transaction
     * @param transDets (String) Short descriptor of every transaction
     * @param cheqNo (long) cheque number if available
     * @param valDate (java.time.LocalDate) date of the value date of the cheque
     * @param withAmt (double) withdraw amount for the account balance
     * @param depAmt (double) deposit amount for the account balance
     */
    public Transaction(long accNo, LocalDate date, String transDets, long cheqNo,
    LocalDate valDate,  Double withAmt, Double depAmt) {
        this.accNo = accNo;
        this.cheqNo = cheqNo;
        this.date = date;
        this.valDate = valDate;
        this.transDets = transDets;
        this.withAmt = withAmt;
        this.depAmt = depAmt;
    }

    /**
     * Returns the account number of this object
     * @return
     */
    public long getAccNo() {
        return accNo;
    }

    /**
     * Returns the cheque number of this object
     * @return (long) return the cheque number of this transaction
     */
    public long getCheqNo() {
        return cheqNo;
    }

    /**
     * Returns the date of this object
     * @return (java.time.LocalDate) Date of the transaction
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Returns the value object of this object
     * @return (java.time.LocalDate) Value date of the transaction
     */
    public LocalDate getValDate() {
        return valDate;
    }

    /**
     * Returns the transaction details of this object
     * @return (String) details of the transaction
      */
    public String getTransDets() {
        return transDets;
    }

    /**
     * Returns the withdraw amount of this object
     * @return (double) withdraw amount in the transaction
     */
    public double getWithAmt() {
        return withAmt;
    }

    /**
     * Returns the deposit amount of this object
     * @return (double) deposit amount in the transaction
     */
    public double getDepAmt() {
        return depAmt;
    }

    @Override
    public String toString(){
        return "Account No: " + accNo + " | " + "Date: " + date + " | " + "Transaction Details: " + transDets + " | " + "Cheque No: " + cheqNo + " | "
        + "Value Date: " + valDate + " | " + "Withdraw Amount: " + withAmt + " | " + "Deposit Amount: " + depAmt + " | ";
    }

}