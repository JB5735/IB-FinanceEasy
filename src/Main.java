import java.util.*;
import java.time.LocalDate;

public class Main {

    // --------------------- Expense Class ---------------------
    static class Expense {
        private String category;
        private double amount;
        private LocalDate date;

        public Expense(String category, double amount, LocalDate date) {
            this.category = category;
            this.amount = amount;
            this.date = date;
        }

        public String getCategory() {
            return category;
        }

        public double getAmount() {
            return amount;
        }

        public LocalDate getDate() {
            return date;
        }

        public String toString() {
            return date.toString() + " - " + category + ": $" + amount;
        }
    }

    // --------------------- Budget Manager ---------------------
    static class BudgetManager {
        private double income;
        private ArrayList<Expense> expenses;

        public BudgetManager(double income) {
            this.income = income;
            expenses = new ArrayList<>();
        }

        public void addExpense(Expense e) {
            expenses.add(e);
        }

        public double getTotalSpent() {
            double total = 0;
            for (Expense e : expenses) {
                total += e.getAmount();
            }
            return total;
        }

        public double getLeftover() {
            return income - getTotalSpent();
        }

        public HashMap<String, Double> getByCategory() {
            HashMap<String, Double> map = new HashMap<>();
            for (Expense e : expenses) {
                String cat = e.getCategory();
                double amt = e.getAmount();
                if (map.containsKey(cat)) {
                    map.put(cat, map.get(cat) + amt);
                } else {
                    map.put(cat, amt);
                }
            }
            return map;
        }

        public ArrayList<Expense> getExpenses() {
            return expenses;
        }
    }

    // --------------------- Goal Tracker ---------------------
    static class GoalTracker {
        private double goalAmount;
        private LocalDate goalDate;

        public GoalTracker(double goalAmount, LocalDate goalDate) {
            this.goalAmount = goalAmount;
            this.goalDate = goalDate;
        }

        public double getProgress(double current) {
            return (current / goalAmount) * 100;
        }

        public long getDaysLeft() {
            return LocalDate.now().until(goalDate).getDays();
        }

        public void showProgress(double current) {
            System.out.println("\n=== SAVINGS GOAL TRACKER ===");
            System.out.println("Goal: $" + goalAmount + " by " + goalDate);
            System.out.println("Current Savings: $" + current);
            System.out.printf("Progress: %.1f%%\n", getProgress(current));
            System.out.println("Days left: " + getDaysLeft());
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter your monthly income: $");
        double income = sc.nextDouble();
        sc.nextLine(); // clean up newline

        BudgetManager bm = new BudgetManager(income);

        boolean keepGoing = true;

        while (keepGoing) {
            System.out.print("\nEnter expense category (like Food, Rent): ");
            String cat = sc.nextLine();

            System.out.print("Enter amount: $");
            double amt = sc.nextDouble();

            System.out.print("Enter date (YYYY-MM-DD): ");
            String dateStr = sc.next();
            sc.nextLine(); // clear newline
            LocalDate date = LocalDate.parse(dateStr);

            Expense e = new Expense(cat, amt, date);
            bm.addExpense(e);

            System.out.print("Add another expense? (yes/no): ");
            String ans = sc.nextLine();
            if (!ans.equalsIgnoreCase("yes")) {
                keepGoing = false;
            }
        }

        // ---------------- Savings goal section ----------------
        GoalTracker tracker = null;
        System.out.print("\nWould you like to set a savings goal? (yes/no): ");
        String goalAns = sc.nextLine();

        if (goalAns.equalsIgnoreCase("yes")) {
            System.out.print("Enter savings target amount: $");
            double goalAmt = sc.nextDouble();
            sc.nextLine();

            System.out.print("Enter target date (YYYY-MM-DD): ");
            LocalDate goalDate = LocalDate.parse(sc.nextLine());

            tracker = new GoalTracker(goalAmt, goalDate);
        }

        // ---------------- Tax Estimator ----------------
        System.out.print("\nWould you like to estimate your taxes? (yes/no): ");
        String taxAnswer = sc.nextLine().toLowerCase();

        if (taxAnswer.equals("yes")) {
            System.out.print("Enter your yearly income (or estimate): $");
            double yearlyIncome = sc.nextDouble();

            System.out.print("Enter estimated tax rate (e.g. 10 for 10%): ");
            double taxRatePercent = sc.nextDouble();
            sc.nextLine(); // clear buffer

            double estimatedTax = Calculators.estimateTax(yearlyIncome, taxRatePercent / 100.0);
            System.out.printf("Estimated tax owed: $%.2f\n", estimatedTax);
        }

        // ---------------- Loan Calculator ----------------
        System.out.print("\nWould you like to calculate a loan payment? (yes/no): ");
        String loanAnswer = sc.nextLine().toLowerCase();

        if (loanAnswer.equals("yes")) {
            System.out.print("Enter loan amount: $");
            double loanAmount = sc.nextDouble();

            System.out.print("Enter annual interest rate (e.g. 5 for 5%): ");
            double annualRate = sc.nextDouble() / 100.0;

            System.out.print("Enter loan term (in years): ");
            int loanYears = sc.nextInt();
            sc.nextLine(); // clear newline

            double monthlyPayment = Calculators.calculateMonthlyLoanPayment(loanAmount, annualRate, loanYears);
            System.out.printf("Your estimated monthly payment is: $%.2f\n", monthlyPayment);
        }


        // Final summary
        System.out.println("\n====== SUMMARY ======");
        System.out.println("Monthly Income: $" + income);
        System.out.println("Total Spent: $" + bm.getTotalSpent());
        System.out.println("Leftover: $" + bm.getLeftover());

        System.out.println("\nExpenses by Category:");
        HashMap<String, Double> categoryMap = bm.getByCategory();
        for (String cat : categoryMap.keySet()) {
            System.out.println("- " + cat + ": $" + categoryMap.get(cat));
        }

        System.out.println("\nAll Expenses:");
        for (Expense e : bm.getExpenses()) {
            System.out.println(e);
        }

        if (tracker != null) {
            tracker.showProgress(bm.getLeftover());
        }

        sc.close();
    }
}
