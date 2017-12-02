/**
 * @author Danil Kolesnikov danil.kolesnikov@sjsu.edu
 * @author Minh Phan minh.phan@sjsu.edu
 * @author Yulan Jin yulan.jin@sjsu.edu
 * CS 151 Term Project - Whiteboard
 */

/**
 * Transaction class contains a model of a transaction
 * Fields: From, To, Amount transferred
 */

class Transaction {
    private int fromAccount;
    private int toAccount;
    private  int amount;

    public Transaction(int from, int to, int amt) {
        fromAccount = from;
        toAccount = to;
        amount = amt;
    }

    public int getFromAccount() {
        return fromAccount;
    }

    public int getAmount() {
        return amount;
    }

    public int getToAccount() {
        return toAccount;
    }

    public String toString() {
        return " Transaction: from = " + fromAccount + ", to = " + toAccount + " amount = " + amount;
    }

}
