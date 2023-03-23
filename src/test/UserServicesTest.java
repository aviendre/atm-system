package test;

import main.*;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserServicesTest {
    
    private List<Account> list;
    private String username = "admin";
    private String pin = "1234";

    @Before
    public void init(){
        this.list = new ArrayList<Account>();
        this.list.add(new Account("admin", "0000", 10002039, "test", 500));
        this.list.add(new Account("admin", "0000", 10002039, "test", 500));
        //this.list.add(new Account("ad", "0000", 10002039, "test", 500));
    }

    @Test
    public void checkChangePIN(){
        UserServices.changeAllPin(this.list, this.username, this.pin);
        for(Account a : this.list){
            if(username.equals(a.getUsername())){
                assertTrue("PIN NUMBER DID NOT CHANGE IN", (a.getPin() == this.pin));
            }
        }
    }

    @After
    public void end(){
        list.clear();
    }
}
