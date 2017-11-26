import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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

    public int getIdNumber() {
        return idNumber;
    }

    public int getNumberOfTransaction() {
        return numberOfTransaction;
    }

    public void setCurrentBalance(int currentBalance) {
        accountLock.lock();
        this.currentBalance = currentBalance;
        accountLock.unlock();
    }

    public void setIdNumber(int idNumber) {
        this.idNumber = idNumber;
    }

    public void setNumberOfTransaction(int numberOfTransaction) {
        accountLock.lock();

        this.numberOfTransaction = numberOfTransaction;
        accountLock.unlock();

    }

    public boolean isAccountSame( Account account){
        if(this.idNumber == account.getIdNumber()){
            return true;
        }
        else {
            return false;
        }
    }


    @Override
    public String toString() {
        return "acct: " + idNumber + " bal: " + currentBalance + " trans: " + numberOfTransaction ;
    }
}
