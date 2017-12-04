import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.*;


public class Bank {
    private Account[] listOfAccount;
    private BlockingQueue<Transaction> queue;
    private final Transaction nullTrans = new Transaction(-1,0,0);

    public Bank() {
        listOfAccount = new Account[20];
        for(int i = 0;i < 20;i++) {
            listOfAccount[i] = new Account(i); //initialize 20 accounts with beginning balance of $1000
        }
        queue = new LinkedBlockingQueue();
    }

    public static void main(String[] args) throws InterruptedException {
        Bank bank = new Bank();

        ArrayList<Worker> listOfWorker = new ArrayList<>();
        int numberOfThreadInput = Integer.parseInt(args[1]);
        for(int i = 0; i < numberOfThreadInput; i++)
        {
            listOfWorker.add(bank.new Worker()); //adding workers depend on input
        }

        try {
            Iterator<Worker> itr = listOfWorker.iterator();
            while (itr.hasNext()){
                itr.next().start(); //start each worker
            }

            bank.loadFile(args[0]); //load queue with transactions from text files

            for(int i = 0; i < numberOfThreadInput; i++)
            {
                bank.queue.put(bank.nullTrans); //putting null transactions depend on number of workers
            }

            Iterator<Worker> itrNew = listOfWorker.iterator();
            while (itrNew.hasNext()){
                itrNew.next().join(); // workers wait for each other to exit at the same time
            }

        } catch (InterruptedException e) {
            System.out.println("interrupted");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("\n----- All threads done -----");
        System.out.println("----- Accounts Summary -----");

        for(int i = 0 ; i < bank.getListOfAccount().length; i++){
            System.out.println(bank.getListOfAccount()[i].toString());
        }
    }

    public Account[] getListOfAccount() {
        return listOfAccount;
    }

    //method to process transaction
    public synchronized void processTransaction(Transaction t) {
        Account accFrom = listOfAccount[t.getFromAccount()];
        Account accTo = listOfAccount[t.getToAccount()];
        int money = t.getAmount();

        accFrom.setCurrentBalance(accFrom.getCurrentBalance() - money);
        accFrom.setNumberOfTransaction(accFrom.getNumberOfTransaction() + 1);

        accTo.setCurrentBalance(accTo.getCurrentBalance() + money);
        accTo.setNumberOfTransaction(accTo.getNumberOfTransaction() + 1);
    }

    //Worker class to do work
    private class Worker extends Thread {
        public void run() {
            try {
                Transaction t;
                do {
                    t = queue.take();
                    if(t.fromAccount != -1) {
                        processTransaction(t);
                       // System.out.println(this.getName() + " processed" + t);
                    }
                } while (t.fromAccount != -1);
            } catch (InterruptedException e) {
                System.out.println("interrupted");
            }
           // System.out.println(this.getName()  + " is exiting...");
        }
    }

    //method to load text file of transactions on queue
    public void loadFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            int accountFrom;
            int accountTo;
            int amountOfMoney;
            String line = br.readLine();
            while (line != null) {
                String[] strOfNumbers = line.split("\\s");
                accountFrom = Integer.parseInt(strOfNumbers[0]);
                accountTo = Integer.parseInt(strOfNumbers[1]);
                amountOfMoney = Integer.parseInt(strOfNumbers[2]);
                Transaction transaction = new Transaction(accountFrom, accountTo, amountOfMoney);
                queue.put(transaction);
                line = br.readLine();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            br.close();
        }
    }
}

