public class Loan implements Loanable {
    private double loanAmount;
    private boolean approved;

    public Loan(double amount) {
        this.loanAmount = amount;
        this.approved = false;
    }

    @Override
    public boolean approveLoan(double amount) {
        if (amount <= 5000) {  // Simplified loan criteria
            approved = true;
            System.out.println("Loan of $" + amount + " approved!");
            return true;
        } else {
            System.out.println("Loan of $" + amount + " rejected.");
            return false;
        }
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public boolean isApproved() {
        return approved;
    }
}
