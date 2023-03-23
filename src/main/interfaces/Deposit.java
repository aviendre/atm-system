package main.interfaces;

public interface Deposit<T> {
    void deposit(T t, long accNo, double depAmt);
}
