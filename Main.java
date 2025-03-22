import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class Main extends JFrame {
    static Point mouseDownScreenCoords;
    static Point mouseDownCompCoords;

    JLabel T1, T2, T3, T4, exit, a2;
    JTextField accountNumberField, amountField;

    JTextArea transactionHistoryArea;

    JButton depositButton, withdrawButton, createAccountButton, transferButton;

    // Map to store account balances
    Map<String, Double> accountBalances = new HashMap<>();

    Main() {
        setLayout(null);

        T1 = new JLabel("ONLINE BANKING");
        T1.setBounds(79, 20, 295, 48);
        T1.setForeground(new Color(255, 255, 255, 250));
        add(T1);
        Font text1 = new Font("Segoe UI", Font.BOLD, 34);
        T1.setFont(text1);

        T2 = new JLabel("Account Number :");
        T2.setBounds(11, 108, 176, 28);
        T2.setForeground(new Color(255, 255, 255, 255));
        add(T2);
        Font text2 = new Font("Segoe UI", Font.BOLD, 20);
        T2.setFont(text2);

        accountNumberField = new JTextField();
        accountNumberField.setBounds(187, 108, 251, 28);
        add(accountNumberField);
        accountNumberField.setFont(text2);

        T3 = new JLabel("Amount :");
        T3.setBounds(11, 152, 88, 28);
        T3.setForeground(new Color(255, 255, 255, 255));
        add(T3);
        Font text3 = new Font("Segoe UI", Font.BOLD, 20);
        T3.setFont(text3);

        amountField = new JTextField();
        amountField.setBounds(103, 152, 335, 28);
        add(amountField);
        amountField.setFont(text3);

        T4 = new JLabel("Transaction History");
        T4.setBounds(129, 195, 198, 30);
        T4.setForeground(new Color(255, 255, 255, 255));
        add(T4);
        Font text4 = new Font("Malgun Gothic", Font.PLAIN, 22);
        T4.setFont(text4);


        transactionHistoryArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(transactionHistoryArea);
        scrollPane.setBounds(12, 225, 427, 206);
        add(scrollPane);



        depositButton = new JButton("Deposit");
        depositButton.setBounds(12, 448, 91, 30);
        add(depositButton);
        Font button = new Font("Segoe UI", Font.BOLD, 11);
        depositButton.setFont(button);

        withdrawButton = new JButton("Withdraw");
        withdrawButton.setBounds(180, 448, 91, 30);
        add(withdrawButton);
        withdrawButton.setFont(button);

        transferButton = new JButton("Transfer");
        transferButton.setBounds(348, 448, 91, 30);
        add(transferButton);
        transferButton.setFont(button);

        createAccountButton = new JButton("Create Account");
        createAccountButton.setBounds(102, 491, 247, 30);
        add(createAccountButton);
        createAccountButton.setFont(button);

        exit = new JLabel("Exit");
        exit.setBounds(416, 498, 28, 19);
        exit.setForeground(Color.black);
        add(exit);
        Font Exit = new Font("calibri", Font.BOLD, 15);
        exit.setFont(Exit);

        a2 = new JLabel();
        a2.setIcon(new ImageIcon(getClass().getResource("img/exit.png")));
        add(a2);

        exit.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                a2.setBounds(406, 498, 88, 17);
                exit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                exit.setForeground(Color.white);
            }
        });

        exit.addMouseListener(new MouseAdapter() {
            public void mouseExited(MouseEvent e) {
                a2.setBounds(0, 0, 0, 0);
                exit.setForeground(Color.black);
            }
        });

        exit.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        depositButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleDeposit();
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleWithdraw();
            }
        });

        transferButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleTransfer();
            }
        });

        createAccountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleCreateAccount();
            }
        });

        JLabel main1 = new JLabel();
        main1.setIcon(new ImageIcon(getClass().getResource("img/3D.png")));
        main1.setBounds(0,0,450,536);
        add(main1);

        setSize(450, 536);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setBackground(new Color(21, 21, 21, 0));
        setVisible(true);
    }

    private void handleDeposit() {
        String accountNumber = accountNumberField.getText();
        String amountStr = amountField.getText();
        double amount = Double.parseDouble(amountStr);

        if (accountBalances.containsKey(accountNumber)) {
            double currentBalance = accountBalances.get(accountNumber);
            double newBalance = currentBalance + amount;
            accountBalances.put(accountNumber, newBalance);
            transactionHistoryArea.append("Deposit: Account " + accountNumber + ", Amount: " + amountStr +
                    ", Remaining Balance: " + newBalance + "\n");
        } else {
            transactionHistoryArea.append("Account does not exist. Please create an account first.\n");
        }
    }

    private void handleWithdraw() {
        String accountNumber = accountNumberField.getText();
        String amountStr = amountField.getText();
        double amount = Double.parseDouble(amountStr);

        if (accountBalances.containsKey(accountNumber)) {
            double currentBalance = accountBalances.get(accountNumber);
            if (currentBalance >= amount) {
                double newBalance = currentBalance - amount;
                accountBalances.put(accountNumber, newBalance);
                transactionHistoryArea.append("Withdraw: Account " + accountNumber + ", Amount: " + amountStr +
                        ", Remaining Balance: " + newBalance + "\n");
            } else {
                transactionHistoryArea.append("Insufficient funds. Withdrawal failed.\n");
            }
        } else {
            transactionHistoryArea.append("Account does not exist. Please create an account first.\n");
        }
    }

    private void handleTransfer() {
        String accountNumber = accountNumberField.getText();
        String amountStr = amountField.getText();
        double amount = Double.parseDouble(amountStr);

        if (accountBalances.containsKey(accountNumber)) {
            double currentBalance = accountBalances.get(accountNumber);

            if (currentBalance >= amount) {
                // Prompt user to enter the destination account number
                String destinationAccount = JOptionPane.showInputDialog(
                        this,
                        "Enter the destination account number:",
                        "Transfer",
                        JOptionPane.QUESTION_MESSAGE
                );

                if (destinationAccount != null && !destinationAccount.isEmpty()) {
                    if (accountBalances.containsKey(destinationAccount)) {
                        double destinationBalance = accountBalances.get(destinationAccount);
                        double newBalance = currentBalance - amount;
                        double newDestinationBalance = destinationBalance + amount;

                        accountBalances.put(accountNumber, newBalance);
                        accountBalances.put(destinationAccount, newDestinationBalance);

                        transactionHistoryArea.append("Transfer: From Account " + accountNumber +
                                " to Account " + destinationAccount + ", Amount: " + amountStr +
                                ", Remaining Balance: " + newBalance + "\n");
                    } else {
                        transactionHistoryArea.append("Destination account does not exist.\n");
                    }
                } else {
                    transactionHistoryArea.append("Transfer canceled or invalid destination account.\n");
                }
            } else {
                transactionHistoryArea.append("Insufficient funds. Transfer failed.\n");
            }
        } else {
            transactionHistoryArea.append("Account does not exist. Please create an account first.\n");
        }
    }


    private void handleCreateAccount() {
        String accountNumber = accountNumberField.getText();
        if (!accountBalances.containsKey(accountNumber)) {
            // Initialize account with a balance of 0
            accountBalances.put(accountNumber, 0.0);
            transactionHistoryArea.append("New Account Created: " + accountNumber + "\n");
        } else {
            transactionHistoryArea.append("Account already exists.\n");
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, UnsupportedLookAndFeelException {
        for (UIManager.LookAndFeelInfo lafInfo : UIManager.getInstalledLookAndFeels()) {
        }
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        Main Frame = new Main();

        Frame.addMouseListener(new MouseListener() {
            public void mouseReleased(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
                mouseDownScreenCoords = e.getLocationOnScreen();
                mouseDownCompCoords = e.getPoint();
            }

            public void mouseExited(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseClicked(MouseEvent e) {
            }
        });
        Frame.addMouseMotionListener(new MouseMotionListener() {
            public void mouseMoved(MouseEvent e) {
            }

            public void mouseDragged(MouseEvent e) {
                Point currCoords = e.getLocationOnScreen();
                Frame.setLocation(mouseDownScreenCoords.x + (currCoords.x - mouseDownScreenCoords.x)
                        - mouseDownCompCoords.x, mouseDownScreenCoords.y + (currCoords.y - mouseDownScreenCoords.y)
                        - mouseDownCompCoords.y);
            }
        });
    }
}
