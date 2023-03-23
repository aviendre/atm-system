package main.interfaces;

public interface Withdraw<T> {
    void withdraw(T t, long accNo, double withAmt);
}
