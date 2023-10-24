import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CompteBancaireGUI {
    public static void main(String[] args) {
        CompteBancaire[] cb = new CompteBancaire[3];
        cb[0] = new CompteBancaire("Mouaad", 1000, 123);
        cb[1] = new CompteBancaire("Youssef", 3000, 456);
        cb[2] = new CompteBancaire("Amal", 500, 789);

        JFrame frame = new JFrame("Mouaad's Bank");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new FlowLayout());

        JComboBox<String> accountComboBox = new JComboBox<>();
        for (CompteBancaire account : cb) {
            accountComboBox.addItem(account.titulaire);
        }

        JButton authenticateButton = new JButton("Authenticate");
        frame.add(accountComboBox);
        frame.add(authenticateButton);

        authenticateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedAccount = accountComboBox.getSelectedItem().toString();
                CompteBancaire selectedCB = null;

                for (CompteBancaire account : cb) {
                    if (selectedAccount.equals(account.titulaire)) {
                        selectedCB = account;
                        break;
                    }
                }

                if (selectedCB != null) {
                    openBankingOperationsFrame(selectedCB, cb);
                } else {
                    JOptionPane.showMessageDialog(null, "Account not found. Please try again.");
                }
            }
        });

        frame.setVisible(true);
    }

    public static void openBankingOperationsFrame(CompteBancaire selectedCB, CompteBancaire[] cb) {
        JFrame bankingFrame = new JFrame("Banking Operations");
        bankingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        bankingFrame.setSize(400, 300);
        bankingFrame.setLayout(new FlowLayout());

        JLabel balanceLabel = new JLabel("Current Balance: " + selectedCB.solde);
        bankingFrame.add(balanceLabel);

        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        JButton transferButton = new JButton("Transfer");
        JButton exitButton = new JButton("Exit");

        bankingFrame.add(depositButton);
        bankingFrame.add(withdrawButton);
        bankingFrame.add(transferButton);
        bankingFrame.add(exitButton);

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double amount = Double.parseDouble(JOptionPane.showInputDialog("Enter the deposit amount:"));
                selectedCB.solde += amount;
                balanceLabel.setText("Current Balance: " + selectedCB.solde);
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double amount = Double.parseDouble(JOptionPane.showInputDialog("Enter the withdrawal amount:"));
                if (amount <= selectedCB.solde) {
                    selectedCB.solde -= amount;
                    balanceLabel.setText("Current Balance: " + selectedCB.solde);
                } else {
                    JOptionPane.showMessageDialog(null, "Insufficient funds.");
                }
            }
        });

        transferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String recipientName = JOptionPane.showInputDialog("Enter the recipient's name:");
                CompteBancaire recipient = null;
                for (CompteBancaire account : cb) {
                    if (recipientName.equals(account.titulaire)) {
                        recipient = account;
                        break;
                    }
                }

                if (recipient != null) {
                    double amount = Double.parseDouble(JOptionPane.showInputDialog("Enter the transfer amount:"));
                    if (amount <= selectedCB.solde) {
                        selectedCB.solde -= amount;
                        recipient.solde += amount;
                        balanceLabel.setText("Current Balance: " + selectedCB.solde);
                    } else {
                        JOptionPane.showMessageDialog(null, "Insufficient funds.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Recipient account not found.");
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bankingFrame.dispose();
            }
        });

        bankingFrame.setVisible(true);
    }
}
