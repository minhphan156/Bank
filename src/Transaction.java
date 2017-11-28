class Transaction {
    int fromAccount;
    int toAccount;
    int amount;

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
