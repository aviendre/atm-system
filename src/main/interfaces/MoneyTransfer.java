package main.interfaces;

public interface MoneyTransfer<T>{
    void moneyTransfer(T t, long accFrom, long accTo, double amount);
}
