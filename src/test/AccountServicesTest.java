package test;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.*;

public class AccountServicesTest {
    
    private List<Account> list;
    private List<Transaction> list2;
    private long accountNo1 = 200000, accountNo2 = 10002039;
    private double depAmount1 = 100, depAmount2 = -600;
    AccountServices as = new AccountServices();
    
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;


    @Before
    public void init(){
        this.list = new ArrayList<>();
        this.list.add(new Account("admin", "0000", 10002039, "test", 500));
        this.list.add(new Account("administor", "1111", 10002039, "test", 500));
        this.list.add(new Account("admin", "0000", 10002239, "test2", 1000));

        this.list2 = new ArrayList<>();
        this.list2.add(new Transaction(10002039, LocalDate.parse("2021-01-01",DateTimeFormatter.ofPattern("yyyy-MM-dd")), "test", 0, LocalDate.parse("2021-01-01",DateTimeFormatter.ofPattern("yyyy-MM-dd")), 500.0, 0.0));
        this.list2.add(new Transaction(10002039, LocalDate.parse("2021-01-01",DateTimeFormatter.ofPattern("yyyy-MM-dd")), "test", 0, LocalDate.parse("2021-01-01",DateTimeFormatter.ofPattern("yyyy-MM-dd")), 500.0, 0.0));
        this.list2.add(new Transaction(10002039, LocalDate.parse("2021-01-01",DateTimeFormatter.ofPattern("yyyy-MM-dd")), "test", 0, LocalDate.parse("2021-01-01",DateTimeFormatter.ofPattern("yyyy-MM-dd")), 500.0, 0.0));

        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @Test
    public void checkingOfDepositErrorAccount(){
        
        as.deposit(this.list, accountNo1, depAmount1);
        assertEquals(ATMException.throwError(100)+"\n", outContent.toString());
    }

    @Test
    public void checkingOfDepositErrorIO(){
        
        as.deposit(this.list, accountNo2, depAmount2);
        assertEquals(ATMException.throwError(401)+"\n", outContent.toString());
    }

    @Test
    public void checkingOfCheckHistory(){
        as.checkHistory(list, list2);
        assertEquals(2000.0, list.get(0).getAccountBalance(),0);
    }

    @Test
    public void checkingOfDeposit(){
        as.deposit(list,10002039,500);
        assertEquals(1000.0, list.get(0).getAccountBalance(),0);
        assertEquals(1000.0, list.get(1).getAccountBalance(),0);
    }

    @Test
    public void checkingOfWithdraw(){
        as.checkHistory(list, list2);
        as.withdraw(list,10002039,500);
        assertEquals(1500.0, list.get(0).getAccountBalance(),0);
        assertEquals(1500.0, list.get(1).getAccountBalance(),0);
    }

    @Test
    public void checkingOfMonyTransfer(){
        as.moneyTransfer(list, 10002039, 10002239, 50);
        assertEquals(450.0, list.get(0).getAccountBalance(),0);
        assertEquals(450.0, list.get(1).getAccountBalance(),0);
        assertEquals(1050.0, list.get(2).getAccountBalance(),0);
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

}
