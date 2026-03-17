# **FinanceEasy – Personal Finance Management Application**
FinanceEasy is a Java desktop application that helps users track expenses, monitor savings goals, and perform common financial calculations like loan payments, taxes, and compound interest. The goal of the project was to build a simple, accessible tool for students or young adults who are learning to manage their finances.

**Link to project:** https://github.com/JB5735/FinanceEasy

## **How It's Made:**
**Tech used:** Java, Java Swing, Object-Oriented Programming, ArrayList, HashMap

FinanceEasy was built as a multi-tab desktop application using Java Swing for the graphical user interface. The program follows an object-oriented structure with separate classes responsible for different parts of the application such as expense tracking, budget management, goal tracking, and financial calculators.

Expenses entered by the user are stored in dynamic data structures (ArrayList) and summarized using HashMap aggregation to group spending by category. The application includes several built-in financial tools including compound interest forecasting, loan payment calculation, and tax estimation. A savings goal tracker visually displays progress through a dynamic progress bar and estimated time remaining.

The interface is organized using JTabbedPane, allowing users to easily navigate between the expense tracker, financial calculators, goal tracker, and appearance customization settings.

## **Features**

* **Expense Tracking:** Log daily expenses with category, date, and amount.
* **Budget Summary:** Automatically calculate total spending, remaining budget, and category-level expense breakdowns.
* **Savings Goal Tracker:** Track progress toward a financial goal with a dynamic progress bar and estimated days remaining.
* **Financial Calculators:**
    * **Compound Interest Calculator**
    * **Tax Estimator**
    * **Loan Payment Calculator**
* **Customizable Interface:** Light/Dark mode and customizable button colors.

## **How It Works**

1. Users input their monthly income and log expenses by category.

2. The system stores expenses in dynamic data structures (ArrayList).

3. Expense summaries are generated using HashMap aggregation to group spending by category.

4. Financial tools allow users to estimate loan payments, taxes, and compound interest.

5. Savings goals are visualized through a progress bar and timeline calculation.

## **How to Run**
1. Clone the repository:
git clone https://github.com/JB5735/IB-FinanceEasy.git

2. Open the project in IntelliJ IDEA or another Java IDE.

3. Run:
MainGUI.java

## **Optimizations**

Several parts of the system were designed to improve code organization and maintainability. Financial calculations and summary logic were encapsulated into reusable methods and classes, preventing duplicated code across the application. Dynamic data structures (ArrayList and HashMap) allow the application to efficiently store and categorize an arbitrary number of user expenses without requiring fixed storage.

Input validation and exception handling were implemented using try-catch blocks and parsing checks to prevent invalid inputs such as incorrect number formats or invalid dates from crashing the program.

## **Lessons Learned:**

Building FinanceEasy helped me gain experience designing a complete application from planning to implementation. One of the most important lessons was learning how to structure programs using object-oriented principles so that different parts of the application remain modular and easy to maintain.

I also gained practical experience working with Java Swing components such as JTabbedPane, JProgressBar, JTextField, and JOptionPane to build interactive interfaces. Implementing features like dynamic expense summaries and savings goal progress visualization helped reinforce my understanding of data structures, event-driven programming, and UI design.

This project strengthened my ability to translate user requirements into functional software while balancing usability, modular design, and maintainable code.
