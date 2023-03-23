package main;

import java.util.concurrent.TimeUnit;   

public class Main {
  public static void main(String[] args) {
    try {
        System.out.println("\nSetting up ATM\nInitializing...");
        OptionMenu op = new OptionMenu();
        TimeUnit.SECONDS.sleep(2);      //allow the system to initialize
        op.getLogin();
    } catch (Exception e) {
        e.printStackTrace();
    }
  }
}