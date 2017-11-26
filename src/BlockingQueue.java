import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueue {
    private ArrayList<Transaction> elements;
    private int head;
    private int size;
    private Lock queueLock = new ReentrantLock();

        public BlockingQueue()
        {
            elements = new ArrayList<>();
            head = 0;
            size = 0;
        }


        public synchronized Transaction remove()
        {


            //queueLock.lock();
            Transaction r = elements.get(head);
            head++;
            size--;
           // queueLock.unlock();
            return r;
        }

    public ArrayList<Transaction> getElements() {
        return elements;
    }

    public int getSize() {
        return size;
    }

    public void add(Transaction newValue)
        {
            queueLock.lock();
            elements.add(newValue);
            size++;
            queueLock.unlock();
        }

        public boolean isEmpty()
        {
            return size == 0;
        }


}
