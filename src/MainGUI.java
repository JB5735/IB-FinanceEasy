import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.*;
import java.awt.Font;

public class MainGUI {

    // --------------------- GUI Components & Data Storage ---------------------
    private JFrame frame;
    private JTextField incomeField, categoryField, amountField, dateField;
    private JTextArea expenseDisplay;
    private ArrayList<Expense> expenseList;
    private double income = 0;

    public MainGUI() {

        // --------------------- Set Font Style ---------------------
        Font font = new Font("SansSerif", Font.PLAIN, 14);
        UIManager.put("Label.font", font);
        UIManager.put("TextField.font", font);
        UIManager.put("TextArea.font", font);
        UIManager.put("Button.font", font);
        UIManager.put("TabbedPane.font", new Font("SansSerif", Font.BOLD, 13));

        // --------------------- Frame and Tabbed Pane Setup ---------------------
        frame = new JFrame("FinanceEasy - Budget Tracker");
        frame.setSize(500, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTabbedPane tabbedPane = new JTabbedPane();

        expenseList = new ArrayList<>();

        // --------------------- Expense Input Panel ---------------------
        JPanel topPanel = new JPanel(new GridLayout(5, 2));
        topPanel.setBorder(BorderFactory.createTitledBorder("Enter Your Info"));

        incomeField = new JTextField();
        categoryField = new JTextField();
        amountField = new JTextField();
        dateField = new JTextField();

        topPanel.add(new JLabel("Monthly Income:"));
        topPanel.add(incomeField);
        topPanel.add(new JLabel("Expense Category:"));
        topPanel.add(categoryField);
        topPanel.add(new JLabel("Amount ($):"));
        topPanel.add(amountField);
        topPanel.add(new JLabel("Date (YYYY-MM-DD):"));
        topPanel.add(dateField);

        JButton addButton = new JButton("Add Expense");
        JButton summaryButton = new JButton("Show Summary");
        Color base = new Color(59, 89, 182);
        Color hover = new Color(30, 70, 160);

        addHoverEffect(addButton, base, hover);
        addHoverEffect(summaryButton, base, hover);

        topPanel.add(addButton);
        topPanel.add(summaryButton);

        // --------------------- Expense Display Panel ---------------------
        expenseDisplay = new JTextArea();
        expenseDisplay.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(expenseDisplay);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Logged Expenses"));

        // Wrap expense components into one panel for the "Expenses" tab
        JPanel expensePanel = new JPanel(new BorderLayout());
        expensePanel.add(topPanel, BorderLayout.NORTH);
        expensePanel.add(scrollPane, BorderLayout.CENTER);

        // --------------------- Calculator Tab UI ---------------------
        JPanel calculatorPanel = new JPanel();
        JPanel goalPanel = new JPanel();
        goalPanel.setLayout(new GridLayout(6, 2, 10, 10));

        JTextField goalAmountField = new JTextField();
        JTextField goalDateField = new JTextField();
        JTextField currentSavingsField = new JTextField();

        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);

        JLabel daysLeftLabel = new JLabel("");

        goalPanel.add(new JLabel("Savings Goal ($):"));
        goalPanel.add(goalAmountField);

        goalPanel.add(new JLabel("Target Date (YYYY-MM-DD):"));
        goalPanel.add(goalDateField);

        goalPanel.add(new JLabel("Current Savings ($):"));
        goalPanel.add(currentSavingsField);

        goalPanel.add(new JLabel("Progress:"));
        goalPanel.add(progressBar);

        goalPanel.add(new JLabel("Days Remaining:"));
        goalPanel.add(daysLeftLabel);

        JButton trackButton = new JButton("Track Progress");
        Color green = new Color(46, 139, 87);
        Color greenHover = new Color(30, 120, 70);

        addHoverEffect(trackButton, green, greenHover);
        goalPanel.add(new JLabel("")); // Filler for grid
        goalPanel.add(trackButton);

        trackButton.addActionListener(e -> {
            try {
                double goalAmount = Double.parseDouble(goalAmountField.getText());
                double currentSavings = Double.parseDouble(currentSavingsField.getText());
                LocalDate goalDate = LocalDate.parse(goalDateField.getText());

                double progress = (currentSavings / goalAmount) * 100;
                progress = Math.min(progress, 100); // cap at 100%

                progressBar.setValue((int) progress);

                long daysLeft = LocalDate.now().until(goalDate).getDays();
                daysLeftLabel.setText(daysLeft + " day(s) left");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Please enter valid numbers and a correct date format.");
            }
        });


        calculatorPanel.setLayout(new GridLayout(3, 1, 10, 10)); // 3 stacked buttons

        JButton compoundButton = new JButton("Compound Interest");
        JButton taxButton = new JButton("Tax Estimator");
        JButton loanButton = new JButton("Loan Calculator");

        addHoverEffect(compoundButton, base, hover);
        addHoverEffect(taxButton, base, hover);
        addHoverEffect(loanButton, base, hover);

        calculatorPanel.add(compoundButton);
        calculatorPanel.add(taxButton);
        calculatorPanel.add(loanButton);

        // --------------------- Calculator Button Functionality ---------------------

        // Compound Interest Calculator
        compoundButton.addActionListener(e -> {
            try {
                String pStr = JOptionPane.showInputDialog(frame, "Enter principal amount:");
                String rStr = JOptionPane.showInputDialog(frame, "Enter annual interest rate (%):");
                String nStr = JOptionPane.showInputDialog(frame, "Times compounded per year:");
                String tStr = JOptionPane.showInputDialog(frame, "Number of years:");

                double P = Double.parseDouble(pStr);
                double r = Double.parseDouble(rStr) / 100.0;
                int n = Integer.parseInt(nStr);
                int t = Integer.parseInt(tStr);

                double result = Calculators.calculateCompoundInterest(P, r, n, t);
                JOptionPane.showMessageDialog(frame, "Future Value: $" + String.format("%.2f", result));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input.");
            }
        });

        // Tax Estimator Calculator
        taxButton.addActionListener(e -> {
            try {
                String incomeStr = JOptionPane.showInputDialog(frame, "Enter income:");
                String rateStr = JOptionPane.showInputDialog(frame, "Enter tax rate (%):");

                double income = Double.parseDouble(incomeStr);
                double rate = Double.parseDouble(rateStr) / 100.0;

                double tax = Calculators.estimateTax(income, rate);
                JOptionPane.showMessageDialog(frame, "Estimated Tax: $" + String.format("%.2f", tax));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input.");
            }
        });

        // Loan Payment Calculator
        loanButton.addActionListener(e -> {
            try {
                String amountStr = JOptionPane.showInputDialog(frame, "Enter loan amount:");
                String rateStr = JOptionPane.showInputDialog(frame, "Enter annual interest rate (%):");
                String yearsStr = JOptionPane.showInputDialog(frame, "Enter number of years:");

                double amount = Double.parseDouble(amountStr);
                double rate = Double.parseDouble(rateStr) / 100.0;
                int years = Integer.parseInt(yearsStr);

                double monthly = Calculators.calculateMonthlyLoanPayment(amount, rate, years);
                JOptionPane.showMessageDialog(frame, "Monthly Payment: $" + String.format("%.2f", monthly));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input.");
            }
        });

        // --------------------- Add Tabs to Tabbed Pane ---------------------
        tabbedPane.addTab("Expenses", expensePanel);
        tabbedPane.addTab("Calculators", calculatorPanel);

        tabbedPane.addTab("Goal Tracker", goalPanel);

        frame.add(tabbedPane);

        // --------------------- "Add Expense" Button Logic ---------------------
        addButton.addActionListener(e -> {
            try {
                if (income == 0) {
                    income = Double.parseDouble(incomeField.getText());
                }

                String category = categoryField.getText();
                double amount = Double.parseDouble(amountField.getText());
                LocalDate date = LocalDate.parse(dateField.getText());

                Expense expense = new Expense(category, amount, date);
                expenseList.add(expense);

                expenseDisplay.append(expense.toString() + "\n");

                // Clear input fields
                categoryField.setText("");
                amountField.setText("");
                dateField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Please enter valid inputs.");
            }
        });

        // --------------------- "Show Summary" Button Logic ---------------------
        summaryButton.addActionListener(e -> {
            double total = 0;
            HashMap<String, Double> categoryTotals = new HashMap<>();

            for (Expense exp : expenseList) {
                total += exp.getAmount();
                String cat = exp.getCategory();

                if (categoryTotals.containsKey(cat)) {
                    categoryTotals.put(cat, categoryTotals.get(cat) + exp.getAmount());
                } else {
                    categoryTotals.put(cat, exp.getAmount());
                }
            }

            double remaining = income - total;

            StringBuilder message = new StringBuilder();
            message.append("Total Spent: $").append(String.format("%.2f", total)).append("\n");
            message.append("Remaining Budget: $").append(String.format("%.2f", remaining)).append("\n\n");
            message.append("Expenses by Category:\n");

            for (String cat : categoryTotals.keySet()) {
                message.append("- ").append(cat).append(": $").append(String.format("%.2f", categoryTotals.get(cat))).append("\n");
            }

            JOptionPane.showMessageDialog(frame, message.toString(), "Monthly Budget Summary", JOptionPane.INFORMATION_MESSAGE);
        });

        // --------------------- Display Frame ---------------------
        frame.setVisible(true);
    }

    // --------------------- Utility: Add Hover Effect ---------------------
    private void addHoverEffect(JButton button, Color normal, Color hover) {
        button.setBackground(normal);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hover);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(normal);
            }
        });
    }

    public static void main(String[] args) {
        new MainGUI();
    }
}
