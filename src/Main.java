import java.time.LocalDate;
import java.util.*;

public class Main {

    // ----------------------------- Expense Tracker -----------------------------
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

        @Override
        public String toString() {
            return date + " - " + category + ": $" + amount;
        }
    }

    // ----------------------------- Budget Manager -----------------------------
    static class BudgetManager {
        private double income;
        private List<Expense> expenses;

        public BudgetManager(double income) {
            this.income = income;
            this.expenses = new ArrayList<>();
        }

        public void addExpense(Expense expense) {
            expenses.add(expense);
        }

        public double getTotalExpenses() {
            return expenses.stream().mapToDouble(Expense::getAmount).sum();
        }

        public double getRemainingBudget() {
            return income - getTotalExpenses();
        }

        public HashMap<String, Double> getExpenseByCategory() {
            HashMap<String, Double> categoryTotals = new HashMap<>();
            for (Expense e : expenses) {
                categoryTotals.put(e.getCategory(),
                        categoryTotals.getOrDefault(e.getCategory(), 0.0) + e.getAmount());
            }
            return categoryTotals;
        }

        public List<Expense> getAllExpenses() {
            return expenses;
        }
    }

    // ----------------------------- Goal Tracker -----------------------------
    static class GoalTracker {
        private double targetAmount;
        private LocalDate targetDate;

        public GoalTracker(double targetAmount, LocalDate targetDate) {
            this.targetAmount = targetAmount;
            this.targetDate = targetDate;
        }

        public double getProgress(double currentSavings) {
            return Math.min(100.0, (currentSavings / targetAmount) * 100);
        }

        public long getDaysRemaining() {
            return LocalDate.now().until(targetDate).getDays();
        }

        public void showGoalStatus(double currentSavings) {
            System.out.println("\n========= SAVINGS GOAL TRACKER =========");
            System.out.println("Goal: $" + targetAmount + " by " + targetDate);
            System.out.println("Current Savings: $" + currentSavings);
            System.out.printf("Progress: %.2f%%\n", getProgress(currentSavings));
            System.out.println("Days remaining: " + getDaysRemaining());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Step 1: Get income
        System.out.print("Enter your monthly income: $");
        double income = scanner.nextDouble();
        scanner.nextLine();

        BudgetManager manager = new BudgetManager(income);

        // Step 2: Input expenses
        boolean addingExpenses = true;

        while (addingExpenses) {
            System.out.print("\nEnter expense category (e.g., Food, Rent): ");
            String category = scanner.nextLine();

            System.out.print("Enter expense amount: $");
            double amount = scanner.nextDouble();

            System.out.print("Enter date (YYYY-MM-DD): ");
            String dateStr = scanner.next();
            LocalDate date = LocalDate.parse(dateStr);
            scanner.nextLine();

            Expense expense = new Expense(category, amount, date);
            manager.addExpense(expense);

            System.out.print("Do you want to add another expense? (yes/no): ");
            String response = scanner.nextLine().toLowerCase();
            addingExpenses = response.equals("yes");
        }

        // Ask user if they want to set a savings goal
        System.out.print("\nWould you like to set a savings goal? (yes/no): ");
        String goalResponse = scanner.nextLine().toLowerCase();

        GoalTracker tracker = null;

        if (goalResponse.equals("yes")) {
            System.out.print("Enter your savings target amount: $");
            double targetAmount = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("Enter your goal date (YYYY-MM-DD): ");
            LocalDate targetDate = LocalDate.parse(scanner.nextLine());

            tracker = new GoalTracker(targetAmount, targetDate);
        }

        // Step 3: Show summary
        System.out.println("\n========== SUMMARY ==========");
        System.out.println("Total income: $" + income);
        System.out.println("Total expenses: $" + manager.getTotalExpenses());
        System.out.println("Remaining budget: $" + manager.getRemainingBudget());

        System.out.println("\nExpenses by category:");
        HashMap<String, Double> categoryTotals = manager.getExpenseByCategory();
        for (String cat : categoryTotals.keySet()) {
            System.out.println("- " + cat + ": $" + categoryTotals.get(cat));
        }

        System.out.println("\nAll expenses:");
        for (Expense e : manager.getAllExpenses()) {
            System.out.println(e);
        }

        // Show savings goal progress if applicable
        if (tracker != null) {
            double savings = manager.getRemainingBudget();
            tracker.showGoalStatus(savings);
        }

        scanner.close();
    }
}