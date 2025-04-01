import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class BankUI {
    private JFrame frame;
    private JLabel balanceLabel;
    private JTextArea transactionArea;
    private Account account;
    private Loan loan;
    private List<String> transactions = new ArrayList<>();

    public BankUI(Account account, Loan loan) {
        this.account = account;
        this.loan = loan;
        initUI();
    }

    private void initUI() {
        frame = new JFrame("Banking System");
        frame.setSize(500, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(30, 30, 30));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 1));
        topPanel.setBackground(new Color(50, 50, 50));

        JLabel titleLabel = new JLabel("Bank Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel);

        balanceLabel = new JLabel("Balance: $" + account.getBalance(), SwingConstants.CENTER);
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 18));
        balanceLabel.setForeground(Color.GREEN);
        topPanel.add(balanceLabel);

        frame.add(topPanel, BorderLayout.NORTH);

        transactionArea = new JTextArea(10, 30);
        transactionArea.setEditable(false);
        transactionArea.setBackground(new Color(20, 20, 20));
        transactionArea.setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(transactionArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(3, 2, 10, 10));
        bottomPanel.setBackground(new Color(30, 30, 30));

        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        JButton loanButton = new JButton("Apply Loan");

        JTextField amountField = new JTextField();
        amountField.setBackground(new Color(40, 40, 40));
        amountField.setForeground(Color.WHITE);

        depositButton.setBackground(new Color(0, 150, 0));
        withdrawButton.setBackground(new Color(200, 50, 50));
        loanButton.setBackground(new Color(50, 100, 200));
        
        depositButton.setForeground(Color.WHITE);
        withdrawButton.setForeground(Color.WHITE);
        loanButton.setForeground(Color.WHITE);

        bottomPanel.add(new JLabel("Amount: ", SwingConstants.CENTER));
        bottomPanel.add(amountField);
        bottomPanel.add(depositButton);
        bottomPanel.add(withdrawButton);
        bottomPanel.add(loanButton);

        frame.add(bottomPanel, BorderLayout.SOUTH);

        depositButton.addActionListener(e -> deposit(amountField));
        withdrawButton.addActionListener(e -> withdraw(amountField));
        loanButton.addActionListener(e -> applyLoan(amountField));

        frame.setVisible(true);
    }

    private void deposit(JTextField amountField) {
        try {
            double amount = Double.parseDouble(amountField.getText());
            account.deposit(amount);
            updateBalance();
            addTransaction("Deposited $" + amount);
            showNotification("Deposit Successful!", Color.GREEN);
        } catch (NumberFormatException e) {
            showNotification("Invalid amount!", Color.RED);
        }
    }

    private void withdraw(JTextField amountField) {
        try {
            double amount = Double.parseDouble(amountField.getText());
            account.withdraw(amount);
            updateBalance();
            addTransaction("Withdrew $" + amount);
            showNotification("Withdrawal Successful!", Color.YELLOW);
        } catch (NumberFormatException e) {
            showNotification("Invalid amount!", Color.RED);
        } catch (InsufficientFundsException e) {
            showNotification(e.getMessage(), Color.RED);
        }
    }

    private void applyLoan(JTextField amountField) {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (loan.approveLoan(amount)) {
                addTransaction("Loan approved for $" + amount);
                showNotification("Loan Approved!", Color.CYAN);
            } else {
                showNotification("Loan Rejected!", Color.RED);
            }
        } catch (NumberFormatException e) {
            showNotification("Invalid amount!", Color.RED);
        }
    }

    private void updateBalance() {
        balanceLabel.setText("Balance: $" + account.getBalance());
    }

    private void addTransaction(String text) {
        transactions.add(text);
        transactionArea.append(text + "\n");
    }

    private void showNotification(String message, Color color) {
        JOptionPane optionPane = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = optionPane.createDialog("Notification");
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        User user = new User("ReVeN", "123456789");
        Account account = new Account(user, 1000);
        Loan loan = new Loan(3000);
        new BankUI(account, loan);
    }
}
