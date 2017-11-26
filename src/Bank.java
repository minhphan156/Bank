import java.io.*;
import java.util.concurrent.CountDownLatch;

public class Bank {
    private Account[] listOfAccount;
    private static BlockingQueue blockingQueue;
    private static final Transaction nullTrans = new Transaction(new Account(-1), new Account(0), 0);
    private static CountDownLatch latch;

    public Bank(){
        listOfAccount = new Account[20];
        blockingQueue = new BlockingQueue ();
        for(int i = 0;i < 20;i++) {
            listOfAccount[i] = new Account(i);
        }
    }

    public static void main(String[] args) throws IOException {
        Bank bank = new Bank();
        String textFile = "";
        int numberOfThreadInput = Integer.parseInt(args[1]);

        bank.loadFile(args[0],textFile);
        //add nullTrans
        for(int i = 0; i < numberOfThreadInput; i++)
        {
            bank.getBlockingQueue().add(bank.nullTrans);
        }



        latch = new CountDownLatch(numberOfThreadInput);

        for(int i = 0; i < numberOfThreadInput;i++ ){
            new Worker().start();
        }

        try {
            latch.await();
        }
        catch (InterruptedException ignored){}

        System.out.println("----------- all done -----------");
        // DEBUG blocking queue
//        Iterator<Transaction> itr = bank.getBlockingQueue().getElements().iterator();
//        while (itr.hasNext()){
//            System.out.println(itr.next().toString());
//        }

        for(int i = 0 ; i < bank.getListOfAccount().length; i++){
            System.out.println(bank.getListOfAccount()[i].toString());
        }

    }

    public void loadFile(String fileName, String textFile) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            StringBuilder sb = new StringBuilder();

            int accountFrom;
            int accountTo;
            int amountOfMoney;
            String line = br.readLine();
            while (line != null) {
                String[] strOfNumbers = line.split("\\s");
                accountFrom = Integer.parseInt(strOfNumbers[0]);
                accountTo = Integer.parseInt(strOfNumbers[1]);
                amountOfMoney = Integer.parseInt(strOfNumbers[2]);
                Transaction transaction = new Transaction(listOfAccount[accountFrom], listOfAccount[accountTo], amountOfMoney);
                blockingQueue.add(transaction);
                line = br.readLine();
            }
        } finally {
            br.close();
        }

    }


    public BlockingQueue getBlockingQueue() {
        return blockingQueue;
    }

    public Account[] getListOfAccount() {
        return listOfAccount;
    }

    public void setListOfAccount(Account[] listOfAccount) {
        this.listOfAccount = listOfAccount;
    }



    public static class Worker extends Thread{

        public Worker(){}

        @Override
        public void run(){

              //  System.out.println("blocking queue size begin thread is " + blockingQueue.getSize());

                Transaction transaction = blockingQueue.remove();

                while (transaction.isTransactionSame(nullTrans) != true) {

//                    if(transaction.getAccountFrom().isAccountSame(transaction.getAccountTo())){
//
//                        transaction.getAccountFrom().setCurrentBalance(transaction.getAccountFrom().getCurrentBalance() - transaction.getAmountOfMoney());
//                        transaction.getAccountTo().setCurrentBalance(transaction.getAccountTo().getCurrentBalance() + transaction.getAmountOfMoney());
//
//                        transaction.getAccountFrom().setNumberOfTransaction(transaction.getAccountFrom().getNumberOfTransaction() + 1);
//                        transaction.getAccountTo().setNumberOfTransaction(transaction.getAccountTo().getNumberOfTransaction() + 1);
//
//                        transaction = blockingQueue.remove();
//
//                    }
//                    else {

//                    System.out.println("-----------------------------------");
//                    Thread running = Thread.currentThread();
//                    System.out.println(running.getName());
//                    System.out.println(transaction.toString());
//                    System.out.println("-----------------------------------");


                    transaction.getAccountFrom().setCurrentBalance(transaction.getAccountFrom().getCurrentBalance() - transaction.getAmountOfMoney());
                        transaction.getAccountTo().setCurrentBalance(transaction.getAccountTo().getCurrentBalance() + transaction.getAmountOfMoney());

                        transaction.getAccountFrom().setNumberOfTransaction(transaction.getAccountFrom().getNumberOfTransaction() + 1);
                        transaction.getAccountTo().setNumberOfTransaction(transaction.getAccountTo().getNumberOfTransaction() + 1);

                        transaction = blockingQueue.remove();
//                    }
                  //  System.out.println("blocking queue size middle thread is " + blockingQueue.getSize());

                }


                System.out.println(" I am done for this thread");

          //  System.out.println("blocking queue size end thread is " + blockingQueue.getSize());
           // System.out.println();
            latch.countDown();

        }

    }
}
