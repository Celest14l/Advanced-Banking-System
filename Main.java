public class Main {
    public static void main(String[] args) {
        try {
            // Create User and Account
            User user = new User("ReVeN", "123456789");
            Account account = new Account(user, 1000);
            Loan loan = new Loan(3000);

            // Start Transaction Manager (Thread)
            TransactionQueueManager transactionManager = new TransactionQueueManager();
            Thread transactionThread = new Thread(transactionManager);
            transactionThread.start();

            // Perform Deposits and Withdrawals
            account.deposit(500);
            account.withdraw(200);

            // Add Transactions to Queue
            transactionManager.addTransaction(new Transaction("Deposit", 500));
            transactionManager.addTransaction(new Transaction("Withdraw", 200));

            // Loan Approval
            if (loan.approveLoan(3000)) {
                System.out.println("Loan approved. Amount: $" + loan.getLoanAmount());
            } else {
                throw new LoanRejectedException("Loan request denied for amount: $3000");
            }

            // Test Insufficient Funds Exception
            account.withdraw(2000); // This should trigger the exception

        } catch (InsufficientFundsException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (LoanRejectedException e) {
            System.out.println("Loan Error: " + e.getMessage());
        } finally {
            System.out.println("Banking session ended.");
        }
    }
}