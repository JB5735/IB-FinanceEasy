public class Calculators {

    // ---------------- Compound Interest ----------------
    public static double calculateCompoundInterest(double principal, double rate, int timesPerYear, int years) {
        // formula: A = P(1 + r/n)^(nt)
        return principal * Math.pow(1 + (rate / timesPerYear), timesPerYear * years);
    }

    // ---------------- Tax Estimator ----------------
    public static double estimateTax(double income, double taxRate) {
        // taxRate is expected in decimal (e.g., 0.15 for 15%)
        return income * taxRate;
    }

    // ---------------- Loan Calculator ----------------
    public static double calculateMonthlyLoanPayment(double loanAmount, double annualRate, int years) {
        // monthly formula: M = P * (r(1+r)^n) / ((1+r)^n - 1)
        double monthlyRate = annualRate / 12;
        int totalMonths = years * 12;

        if (monthlyRate == 0) {
            return loanAmount / totalMonths; // no interest case
        }

        double numerator = monthlyRate * Math.pow(1 + monthlyRate, totalMonths);
        double denominator = Math.pow(1 + monthlyRate, totalMonths) - 1;

        return loanAmount * (numerator / denominator);
    }
}

