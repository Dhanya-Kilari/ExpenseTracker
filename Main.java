package ExpenseTracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void displayExpenses(ExpenseManager manager) {

        if (manager.getExpenses().isEmpty()) {
            System.out.println("No expenses available.");
            return;
        }

        System.out.println("\nYour Expenses:");

        for (int i = 0; i < manager.getExpenses().size(); i++) {
            Expense e = manager.getExpenses().get(i);

            System.out.println((i + 1) + ". " + e.getCategory() + " - Rs. " + e.getAmount());
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ExpenseManager manager = new ExpenseManager();

        int choice=0;

        do {
            System.out.println("\n===== EXPENSE TRACKER =====");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Total Expenses");
            System.out.println("4. Delete Expense");
            System.out.println("5. Update Expense");
            System.out.println("6. Category-Wise Expense Summary");
            System.out.println("7. Search Expense by Category");
            System.out.println("8. Highest & Lowest Expense");
            System.out.println("9. Exit");

            System.out.print("Enter your choice: ");

            try {
                choice = sc.nextInt();
                sc.nextLine();
            } 
            catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number.");
                sc.nextLine();
                continue;
            }

            switch (choice) {

                case 1:
                    System.out.print("Enter category: ");
                    String category = sc.nextLine().trim();

                    if(category.isEmpty()){
                        System.out.println("Category cannot be empty.");
                        break;
                    }

                    System.out.print("Enter amount: ");

                    double amount;

                    try {
                       amount = sc.nextDouble();
                       sc.nextLine();
                    } 
                    catch (InputMismatchException e) {
                       System.out.println("Invalid input! Please enter a number.");
                       sc.nextLine();
                       break;
                    }

                    if (amount <= 0) {
                        System.out.println("Amount must be greater than zero.");
                        break;
                    }

                    Expense expense = new Expense(category, amount);
                    manager.addExpense(expense);

                    System.out.println("Expense added successfully!");
                    break;

                case 2:
                    displayExpenses(manager);
                    break;

                case 3:
                    System.out.println("Total Expenses: Rs. " + manager.getTotalExpenses());
                    break;
                
                case 4:
                        displayExpenses(manager);
                        System.out.print("Enter expense number to delete: ");
                        int expenseNumber;

                        try {
                           expenseNumber = sc.nextInt();
                           sc.nextLine();
                        }
                        catch (InputMismatchException e) {
                            System.out.println("Invalid input! Please enter a number.");
                            sc.nextLine();
                            break;
                        }

                        if (manager.deleteExpense(expenseNumber - 1)) {
                               System.out.println("Expense deleted successfully!");
                            } else {
                                System.out.println("Invalid expense number.");
                            }
                            break;
                
                case 5:
                    displayExpenses(manager);

                    System.out.print("Enter expense number to update: ");
                    int updateNumber;
                    try {
                       updateNumber = sc.nextInt();
                       sc.nextLine();
                    }
                    catch (InputMismatchException e) {
                        System.out.println("Invalid input! Please enter a number.");
                        sc.nextLine();
                        break;
                    }

                    System.out.print("Enter new category: ");
                    String newCategory = sc.nextLine().trim();

                    if(newCategory.isEmpty()){
                        System.out.println("Category cannot be empty.");
                        break;
                    }

                    System.out.print("Enter new amount: ");
                    double newAmount;

                    try {
                       newAmount = sc.nextDouble();
                       sc.nextLine();
                    }
                    catch (InputMismatchException e) {
                       System.out.println("Invalid input! Please enter a number.");
                       sc.nextLine();
                       break;
                    }

                    if (newAmount <= 0) {
                      System.out.println("Amount must be greater than zero.");
                      break;
                    }

                    if (manager.updateExpense(updateNumber - 1, newCategory, newAmount)) {
                        System.out.println("Expense updated successfully!");
                    } 
                    else {
                        System.out.println("Invalid expense number.");
                    }
                    break;

                case 6:
                    HashMap<String, Double> categoryTotals = manager.getCategoryTotals();

                    if (categoryTotals.isEmpty()) {
                         System.out.println("No expenses available.");
                         break;
                        }

                        System.out.println("\n===== CATEGORY-WISE EXPENSE SUMMARY =====");

                        for (String category1 : categoryTotals.keySet()) {
                            System.out.println(category1 + " : Rs. " + categoryTotals.get(category1));
                        }

                        break;

                case 7:
                    System.out.print("Enter category to search: ");
                    String searchCategory = sc.nextLine().trim();

                    if(searchCategory.isEmpty()){
                        System.out.println("Category cannot be empty.");
                        break;
                    }

                    ArrayList<Expense> results = manager.searchByCategory(searchCategory);

                    if (results.isEmpty()) {
                        System.out.println("No expenses found for this category.");
                        break;
                    }

                    double searchTotal = 0;

                    System.out.println("\n===== SEARCH RESULTS =====");

                    for (int i = 0; i < results.size(); i++) {
                           Expense e = results.get(i);

                           System.out.println((i + 1) + ". "+ e.getCategory()+ " - Rs. " + e.getAmount());

                           searchTotal += e.getAmount();
                    }

                    System.out.println("Total " + searchCategory+ " Expenses: Rs. " + searchTotal);
                    break;

                case 8:
                    Expense highest = manager.getHighestExpense();
                    Expense lowest = manager.getLowestExpense();

                    if (highest == null) {
                       System.out.println("No expenses available for analysis.");
                       break;
                    }

                    System.out.println("\n===== EXPENSE ANALYSIS =====");

                    System.out.println("Highest Expense: " + highest.getCategory() + " - Rs. " + highest.getAmount());

                    System.out.println("Lowest Expense: " + lowest.getCategory() + " - Rs. " + lowest.getAmount());

                    break;

                case 9:
                    System.out.println("Thank you for using Expense Tracker!");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 9);

        sc.close();
    }
}