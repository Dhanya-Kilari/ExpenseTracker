package ExpenseTracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ExpenseManager {

    private ArrayList<Expense> expenses;
    private final String fileName = "expenses.txt";

    public ExpenseManager() {
        expenses = new ArrayList<>();
        loadExpenses();
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
        saveExpenses();
    }

    public ArrayList<Expense> getExpenses() {
        return expenses;
    }

    public double getTotalExpenses() {
        double total = 0;

        for (Expense expense : expenses) {
            total += expense.getAmount();
        }

        return total;
    }

    private void saveExpenses() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

            for (Expense expense : expenses) {
                writer.write(expense.getCategory() + "," + expense.getAmount());
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error saving expenses: " + e.getMessage());
        }
    }

    private void loadExpenses() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length == 2) {
                    try {
                        String category = parts[0];
                        double amount = Double.parseDouble(parts[1]);

                        expenses.add(new Expense(category, amount));
                    } catch (NumberFormatException e) {
                        System.out.println("Skipping invalid expense record.");
                    }
                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public boolean deleteExpense(int index) {
        if (index >= 0 && index < expenses.size()) {
             expenses.remove(index);
             saveExpenses();
             return true;
        }
        return false;
    }
    
    public boolean updateExpense(int index, String category, double amount) {

        if (index >= 0 && index < expenses.size() && amount > 0) {

            Expense newExpense = new Expense(category, amount);

            expenses.set(index, newExpense);

            saveExpenses();

            return true;
        }
        return false;
    }
    
    public HashMap<String, Double> getCategoryTotals() {

        HashMap<String, Double> categoryTotals = new HashMap<>();

        for (Expense expense : expenses) {

           String category = expense.getCategory();
           double amount = expense.getAmount();

           categoryTotals.put(category, categoryTotals.getOrDefault(category, 0.0) + amount);
        }

        return categoryTotals;
    }
    
    public ArrayList<Expense> searchByCategory(String category) {

        ArrayList<Expense> results = new ArrayList<>();
        
        for (Expense expense : expenses) {

           if (expense.getCategory().equalsIgnoreCase(category)) {
               results.add(expense);
            }
        }

        return results;
    }

    public Expense getHighestExpense() {

         if (expenses.isEmpty()) {
              return null;
            }

            Expense highest = expenses.get(0);

            for (Expense expense : expenses) {
                if (expense.getAmount() > highest.getAmount()) {
                highest = expense;
            }
        }

        return highest;
    }

    public Expense getLowestExpense() {

        if (expenses.isEmpty()) {
            return null;
        }

        Expense lowest = expenses.get(0);
  
        for (Expense expense : expenses) {
           if (expense.getAmount() < lowest.getAmount()) {
              lowest = expense;
            }
        }

        return lowest;
    }
}
