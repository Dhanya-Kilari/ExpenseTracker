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

            System.out.println((i + 1) + ". " + e.getCategory() + " - Rs. " + e.getAmount() + " - " + e.getDate());
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
            System.out.println("7. Monthly-Wise Expense Summary");
            System.out.println("8. Daily-Wise Expense Summary");
            System.out.println("9. Yearly-Wise Expense Summary");
            System.out.println("10. Search Expense by Category");
            System.out.println("11. Highest & Lowest Expense");
            System.out.println("12. Exit");

            System.out.print("Enter your choice: ");

            String input = sc.nextLine().trim();

            if (input.isEmpty()) {
                 System.out.println("Choice cannot be empty. Please enter a choice.");
                 continue;
            }

            try {
                 choice = Integer.parseInt(input);
            } 
            catch (NumberFormatException e) {
                   System.out.println("Invalid input! Please enter a number.");
                   continue;
            }

            switch (choice) {

                case 1:
                    System.out.print("Enter Expense Category: ");
                    String category = sc.nextLine().trim();

                    if(category.isEmpty() || !category.matches("[a-zA-Z ]+")){
                        System.out.println("Invalid Category! Please enter text only.");
                        break;
                    }

                    System.out.print("Enter Expense Amount: ");

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

                    System.out.print("Enter Expense Date(dd-mm-yyyy): ");
                    String date = sc.next();

                    Expense expense = new Expense(category, amount, date);
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
                        if(manager.getExpenses().isEmpty()){
                            displayExpenses(manager);
                            break;
                        }
                        
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
                    if(manager.getExpenses().isEmpty()){
                            displayExpenses(manager);
                            break;
                        }
                
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

                    System.out.print("Enter New Expense Category: ");
                    String newCategory = sc.nextLine().trim();

                    if(newCategory.isEmpty() || !newCategory.matches("[a-zA-Z ]+")){
                        System.out.println("Invalid Category! Please enter text only.");
                        break;
                    }

                    System.out.print("Enter New Expense Amount: ");
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

                    System.out.print("Enter New Expense Date(dd-mm-yyyy): ");
                    String newDate = sc.next();

                    if (manager.updateExpense(updateNumber - 1, newCategory, newAmount, newDate)) {
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
                        HashMap<String, Double> monthlyTotals = manager.getMonthlyTotals();

                        for (String month : monthlyTotals.keySet()) {
                            System.out.println(month + " : " + monthlyTotals.get(month));
                        }
                        break;

                case 8:
                        HashMap<String, Double> dailyTotals = manager.getDailyTotals();

                        for (String date1 : dailyTotals.keySet()) {
                            System.out.println(date1 + " : " + dailyTotals.get(date1));
                        }
                        break;

                case 9:
                        HashMap<String, Double> yearlyTotals = manager.getYearlyTotals();

                        for (String year : yearlyTotals.keySet()) {
                              System.out.println(year + " : " + yearlyTotals.get(year));
                            }
                            break;

                case 10:
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

                case 11:
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

                case 12:
                    System.out.println("Thank you for using Expense Tracker!");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 11);

        sc.close();
    }
}