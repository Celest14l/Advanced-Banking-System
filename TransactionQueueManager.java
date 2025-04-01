import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TransactionQueueManager implements Runnable {
    private BlockingQueue<Transaction> transactionQueue = new LinkedBlockingQueue<>();

    public void addTransaction(Transaction transaction) {
        transactionQueue.add(transaction);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Transaction transaction = transactionQueue.take();
                System.out.println("Processing: " + transaction);
                Thread.sleep(1000); // Simulate transaction time
            } catch (InterruptedException e) {
                System.out.println("Transaction interrupted.");
            }
        }
    }
}
