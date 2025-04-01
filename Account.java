public class Account implements AccountOperations {
    private User user;
    private double balance;

    public Account(User user, double initialBalance) {
        this.user = user;
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    @Override
    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposited $" + amount + ". New balance: $" + balance);
    }

    @Override
    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount > balance) {
            throw new InsufficientFundsException("Insufficient balance! Current balance: $" + balance);
        }
        balance -= amount;
        System.out.println("Withdrew $" + amount + ". Remaining balance: $" + balance);
    }
}
