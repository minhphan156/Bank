import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Danil Kolesnikov danil.kolesnikov@sjsu.edu
 * @author Minh Phan minh.phan@sjsu.edu
 * @author Yulan Jin yulan.jin@sjsu.edu
 * CS 151 Term Project - Whiteboard
 */

/**
 * Account class is a model of a bank account
 * Fields: id of the account; current balance, number of transactions
 */

public class Account{

    private int idNumber;
    private int currentBalance = 1000;
    private int numberOfTransaction = 0;
    private Lock accountLock = new ReentrantLock();

    Account(int idNumber){
        this.idNumber = idNumber;
    }

    public int getCurrentBalance() {
        return currentBalance;
    }


    public int getNumberOfTransaction() {
        return numberOfTransaction;
    }

    public void setCurrentBalance(int currentBalance) {
        accountLock.lock();
        this.currentBalance = currentBalance;
        accountLock.unlock();
    }

    public void setNumberOfTransaction(int numberOfTransaction) {
        accountLock.lock();

        this.numberOfTransaction = numberOfTransaction;
        accountLock.unlock();

    }

    @Override
    public String toString() {
        return "acct: " + idNumber + " bal: " + currentBalance + " trans: " + numberOfTransaction ;
    }
}
