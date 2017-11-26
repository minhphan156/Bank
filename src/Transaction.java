public class Transaction {
    private Account accountFrom;
    private Account accountTo;
    private int amountOfMoney;

    Transaction(Account accountFrom, Account accountTo, int amountOfMoney){
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amountOfMoney = amountOfMoney;
    }

    public Account getAccountFrom() {
        return accountFrom;
    }

    public Account getAccountTo() {
        return accountTo;
    }

    public void setAccountFrom(Account accountFrom) {
        this.accountFrom = accountFrom;
    }

    public void setAccountTo(Account accountTo) {
        this.accountTo = accountTo;
    }

    public int getAmountOfMoney() {
        return amountOfMoney;
    }

    public void setAmountOfMoney(int amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }

    public boolean isTransactionSame (Transaction transaction){
        if(this.accountFrom.isAccountSame(transaction.getAccountFrom()) && this.accountTo.isAccountSame(transaction.getAccountTo()) && this.amountOfMoney == transaction.getAmountOfMoney()){
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public String toString() {
        return "account From " + "\n" + accountFrom +
                 "\n" + " account to " + accountTo +
                "\n" + " amount Of Money " + amountOfMoney
                + "\n";
    }
}
