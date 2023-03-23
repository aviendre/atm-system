package main.interfaces;

public interface TransactionHistory<T> {
    T transactionHistory(T l, long accNo);
}
