import models.*;
import management.*;
import utils.InputValidator;

import java.util.Scanner;

public class Main {
    private static AccountManager accountManager;
    private static TransactionManager transactionManager;
    private static Scanner scanner;

    public static void main(String[] args) {
        initializeSystem();
        runApplication();
    }

    private static void initializeSystem() {
        accountManager = new AccountManager();
        transactionManager = new TransactionManager();
        scanner = new Scanner(System.in);

        initializeSampleData();

        System.out.println("Bank Account Management System initialized.\n");
    }

    private static void initializeSampleData() {
        try {
            RegularCustomer john = new RegularCustomer("John Smith", 35, "555-0101", "123 Main St");
            RegularCustomer sarah = new RegularCustomer("Sarah Johnson", 28, "555-0102", "456 Oak Ave");
            RegularCustomer michael = new RegularCustomer("Michael Chen", 42, "555-0103", "789 Pine Rd");
            RegularCustomer emily = new RegularCustomer("Emily Brown", 31, "555-0104", "321 Elm St");
            RegularCustomer david = new RegularCustomer("David Wilson", 45, "555-0105", "654 Maple Dr");

            SavingsAccount acc1 = new SavingsAccount(john, 5250.00);
            CheckingAccount acc2 = new CheckingAccount(sarah, 3450.00);
            SavingsAccount acc3 = new SavingsAccount(michael, 15750.00);
            CheckingAccount acc4 = new CheckingAccount(emily, 890.00);
            SavingsAccount acc5 = new SavingsAccount(david, 25300.00);

            accountManager.addAccount(acc1);
            accountManager.addAccount(acc2);
            accountManager.addAccount(acc3);
            accountManager.addAccount(acc4);
            accountManager.addAccount(acc5);

        } catch (IllegalArgumentException e) {
            System.out.println("Error initializing sample data: " + e.getMessage());
        }
    }

    private static void runApplication() {
        boolean running = true;

        while (running) {
            displayMainMenu();
            int choice = InputValidator.getIntInput(scanner, "Enter choice: ", 1, 5);

            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    viewAccounts();
                    break;
                case 3:
                    processTransaction();
                    break;
                case 4:
                    viewTransactionHistory();
                    break;
                case 5:
                    running = false;
                    exitApplication();
                    break;
            }

            if (running && choice != 5) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
    }

    private static void displayMainMenu() {
        System.out.println("\n" + "=".repeat(35));
        System.out.println("  BANK ACCOUNT MANAGEMENT SYSTEM");
        System.out.println("=".repeat(35));
        System.out.println("1. Create Account");
        System.out.println("2. View Accounts");
        System.out.println("3. Process Transaction");
        System.out.println("4. View Transaction History");
        System.out.println("5. Exit\n");
    }

    private static void createAccount() {
        System.out.println("\nCREATE ACCOUNT");
        System.out.println("-".repeat(14));

        String name = InputValidator.getStringInput(scanner, "Enter customer name: ");
        int age = InputValidator.getIntInput(scanner, "Enter customer age: ", 18, 120);
        String contact = InputValidator.getStringInput(scanner, "Enter contact number: ");
        String address = InputValidator.getStringInput(scanner, "Enter address: ");

        System.out.println("\nCustomer type:");
        System.out.println("1. Regular Customer (Standard banking services)");
        System.out.println("2. Premium Customer (Enhanced benefits, min balance $10,000)");
        int customerType = InputValidator.getIntInput(scanner, "Select type (1-2): ", 1, 2);

        Customer customer;
        if (customerType == 1) {
            customer = new RegularCustomer(name, age, contact, address);
        } else {
            customer = new PremiumCustomer(name, age, contact, address);
        }

        System.out.println("\nAccount type:");
        System.out.println("1. Savings Account (Interest: 3.5%, Min balance: $500)");
        System.out.println("2. Checking Account (Overdraft: $1000, Monthly fee $10)");
        int accountType = InputValidator.getIntInput(scanner, "Select type (1-2): ", 1, 2);

        double minDeposit = (accountType == 1) ? 500.0 : 0.0;
        double initialDeposit;

        while (true) {
            initialDeposit = InputValidator.getPositiveDoubleInput(scanner,
                    "\nEnter initial deposit amount: $");

            if (accountType == 1 && initialDeposit < minDeposit) {
                System.out.printf("Savings account requires minimum deposit of $%.2f%n\n", minDeposit);
            } else {
                break;
            }
        }

        Account account = null;
        try {
            if (accountType == 1) {
                account = new SavingsAccount(customer, initialDeposit);
            } else {
                account = new CheckingAccount(customer, initialDeposit);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        if (accountManager.addAccount(account)) {
            System.out.println("\n✓ Account created successfully!");
            account.displayAccountDetails();

            Transaction initialTxn = new Transaction(
                    account.getAccountNumber(),
                    "DEPOSIT",
                    initialDeposit,
                    initialDeposit
            );
            transactionManager.addTransaction(initialTxn);

            System.out.printf("%nTotal accounts in system: %d%n", accountManager.getAccountCount());
        } else {
            System.out.println("\n✗ Failed to create account. Maximum capacity reached.");
        }
    }

    private static void viewAccounts() {

        if (accountManager.getAccountCount() == 0) {
            System.out.println("No accounts registered yet. Use option 1 to create accounts.");
            return;
        }

        accountManager.viewAllAccounts();
    }

    private static void processTransaction() {
        System.out.println("\nPROCESS TRANSACTION");
        System.out.println("-".repeat(20));

        if (accountManager.getAccountCount() == 0) {
            System.out.println("No accounts available. Please create an account first.");
            return;
        }

        String accountNumber = InputValidator.getStringInput(scanner, "\nEnter Account Number: ");
        Account account = accountManager.findAccount(accountNumber);

        if (account == null) {
            System.out.println("Account not found!");
            return;
        }

        System.out.println("\nAccount Details:");
        System.out.println("Customer: " + account.getCustomer().getName());
        System.out.println("Account Type: " + account.getAccountType());
        System.out.printf("Current Balance: $%.2f%n", account.getBalance());

        System.out.println("\nTransaction type:");
        System.out.println("1. Deposit");
        System.out.println("2. Withdrawal");
        int txnType = InputValidator.getIntInput(scanner, "\nSelect type (1-2): ", 1, 2);

        double amount = InputValidator.getPositiveDoubleInput(scanner, "\nEnter amount: $");

        boolean valid = true;
        String validationMessage = "";

        if (txnType == 2) {
            if (account instanceof SavingsAccount) {
                SavingsAccount sa = (SavingsAccount) account;
                double newBalance = account.getBalance() - amount;
                if (newBalance < sa.getMinimumBalance()) {
                    valid = false;
                    validationMessage = String.format(
                            "Withdrawal would drop balance below minimum requirement of $%.2f",
                            sa.getMinimumBalance()
                    );
                }
            } else if (account instanceof CheckingAccount) {
                CheckingAccount ca = (CheckingAccount) account;
                double newBalance = account.getBalance() - amount;
                if (newBalance < -ca.getOverdraftLimit()) {
                    valid = false;
                    validationMessage = String.format(
                            "Withdrawal exceeds overdraft limit of $%.2f",
                            ca.getOverdraftLimit()
                    );
                }
            }
        }

        if (!valid) {
            System.out.println("\n✗ " + validationMessage);
            return;
        }

        double previousBalance = account.getBalance();
        String type = (txnType == 1) ? "DEPOSIT" : "WITHDRAWAL";
        double newBalance = (txnType == 1) ? previousBalance + amount : previousBalance - amount;

        System.out.println("TRANSACTION CONFIRMATION");
        System.out.println("-".repeat(30));

        Transaction preview = new Transaction(accountNumber, type, amount, newBalance);
        System.out.printf("%-8s: %s%n", "Transaction ID", preview.getTransactionId());
        System.out.printf("%-8s: %s%n", "Account", accountNumber);
        System.out.printf("%-8s: %s%n", "Type", type);
        System.out.printf("%-8s: $%.2f%n", "Amount", amount);
        System.out.printf("%-8s: $%.2f%n", "Previous Balance", previousBalance);
        System.out.printf("%-8s: $%.2f%n", "New Balance", newBalance);
        System.out.printf("%-8s: %s%n", "Date/Time", preview.getTimestamp());
        System.out.println("-".repeat(30));

        boolean confirm = InputValidator.getYesNoInput(scanner, "\nConfirm transaction?");

        if (confirm) {
            boolean success;
            if (txnType == 1) {
                success = account.deposit(amount);
            } else {
                success = account.withdraw(amount);
            }

            if (success) {
                Transaction transaction = new Transaction(
                        accountNumber,
                        type,
                        amount,
                        account.getBalance()
                );
                transactionManager.addTransaction(transaction);
                System.out.println("\n✓ Transaction completed successfully!");
            } else {
                System.out.println("\n✗ Transaction failed!");
            }
        } else {
            System.out.println("\nTransaction cancelled.");
        }
    }

    private static void viewTransactionHistory() {
        System.out.println("\nVIEW TRANSACTION HISTORY");
        System.out.println("-".repeat(30));

        if (accountManager.getAccountCount() == 0) {
            System.out.println("No accounts available.");
            return;
        }

        String accountNumber = InputValidator.getStringInput(scanner, "\nEnter Account Number: ");
        Account account = accountManager.findAccount(accountNumber);

        if (account == null) {
            System.out.println("Account not found!");
            return;
        }

        System.out.printf("%nAccount: %s - %s%n",
                accountNumber, account.getCustomer().getName());
        System.out.println("Account Type: " + account.getAccountType());
        System.out.printf("Current Balance: $%.2f%n\n", account.getBalance());

        transactionManager.viewTransactionsByAccount(accountNumber);
    }

    private static void exitApplication() {
        System.out.println("\nThank you for using Bank Account Management System!");
        System.out.println("Goodbye!");
        scanner.close();
    }
}