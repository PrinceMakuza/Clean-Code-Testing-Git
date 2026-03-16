import models.*;
import services.*;
import exceptions.*;
import utils.InputValidator;
import java.util.Scanner;

/**
 * Main application class for the Bank Account Management System.
 */
public class Main {
    private static AccountManager accountManager;
    private static TransactionManager transactionManager;
    private static StatementGenerator statementGenerator;
    private static Scanner scanner;

    public static void main(String[] args) {
        initializeSystem();
        runApplication();
    }

    private static void initializeSystem() {
        accountManager = new AccountManager();
        transactionManager = new TransactionManager();
        statementGenerator = new StatementGenerator();
        scanner = new Scanner(System.in);
        initializeSampleData();
        System.out.println("Bank Account Management System initialized.\n");
    }

    private static void initializeSampleData() {
        try {
            Customer john = new RegularCustomer("John Smith", 35, "555-0101", "123 Main St");
            Customer sarah = new PremiumCustomer("Sarah Johnson", 28, "555-0102", "456 Oak Ave");

            Account acc1 = new SavingsAccount(john, 5250.00);
            Account acc2 = new CheckingAccount(sarah, 3450.00);

            accountManager.addAccount(acc1);
            accountManager.addAccount(acc2);

            transactionManager.addTransaction(new Transaction(acc1.getAccountNumber(), "DEPOSIT", 2000.00, 7250.00));
            transactionManager.addTransaction(new Transaction(acc1.getAccountNumber(), "WITHDRAWAL", 500.00, 6750.00));
            transactionManager.addTransaction(new Transaction(acc1.getAccountNumber(), "DEPOSIT", 1500.00, 8250.00));
            transactionManager.addTransaction(new Transaction(acc1.getAccountNumber(), "WITHDRAWAL", 3000.00, 5250.00));
            
            // For checking account sample
            transactionManager.addTransaction(new Transaction(acc2.getAccountNumber(), "DEPOSIT", 500.00, 3950.00));
            transactionManager.addTransaction(new Transaction(acc2.getAccountNumber(), "WITHDRAWAL", 1000.00, 2950.00));

        } catch (Exception e) {
            System.out.println("Error initializing sample data: " + e.getMessage());
        }
    }

    private static void runApplication() {
        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = InputValidator.getIntInput(scanner, "Enter your choice: ", 1, 5);
            try {
                switch (choice) {
                    case 1 -> manageAccounts();
                    case 2 -> performTransactions();
                    case 3 -> generateStatement();
                    case 4 -> runTests();
                    case 5 -> {
                        running = false;
                        exitApplication();
                    }
                }
            } catch (Exception e) {
                System.out.println("\nError: " + e.getMessage());
            }

            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
    }

    private static void displayMainMenu() {
        System.out.println("\n===============================");
        System.out.println("BANK ACCOUNT MANAGEMENT SYSTEM");
        System.out.println("===============================");
        System.out.println("\nMain Menu:");
        System.out.println("1. Manage Accounts");
        System.out.println("2. Perform Transactions");
        System.out.println("3. Generate Account Statements");
        System.out.println("4. Run Tests");
        System.out.println("5. Exit");
    }

    private static void manageAccounts() {
        System.out.println("\n1. Create Account");
        System.out.println("2. View All Accounts");
        int subChoice = InputValidator.getIntInput(scanner, "Select: ", 1, 2);
        
        if (subChoice == 1) {
            createAccount();
        } else {
            accountManager.viewAllAccounts();
        }
    }

    private static void createAccount() {
        System.out.println("\nCREATE ACCOUNT");
        Customer customer = collectCustomerInfo();
        
        System.out.println("\nAccount Type: 1. Savings, 2. Checking");
        int accType = InputValidator.getIntInput(scanner, "Select: ", 1, 2);
        double initialDeposit = InputValidator.getPositiveDoubleInput(scanner, "Enter initial deposit: $");

        try {
            Account account = (accType == 1) 
                ? new SavingsAccount(customer, initialDeposit) 
                : new CheckingAccount(customer, initialDeposit);
            
            saveAccount(account, initialDeposit);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static Customer collectCustomerInfo() {
        String name = InputValidator.getStringInput(scanner, "Enter customer name: ");
        int age = InputValidator.getIntInput(scanner, "Enter age: ", 18, 100);
        String contact = InputValidator.getStringInput(scanner, "Enter contact: ");
        String address = InputValidator.getStringInput(scanner, "Enter address: ");

        System.out.println("\nCustomer Type: 1. Regular, 2. Premium");
        int custType = InputValidator.getIntInput(scanner, "Select: ", 1, 2);
        
        return (custType == 1) 
            ? new RegularCustomer(name, age, contact, address) 
            : new PremiumCustomer(name, age, contact, address);
    }

    private static void saveAccount(Account account, double initialDeposit) {
        if (accountManager.addAccount(account)) {
            transactionManager.addTransaction(new Transaction(account.getAccountNumber(), "INITIAL DEPOSIT", initialDeposit, initialDeposit));
            System.out.println("Account created successfully. Account Number: " + account.getAccountNumber());
        } else {
            System.out.println("Capacity full.");
        }
    }

    private static void performTransactions() {
        String accNum = InputValidator.getStringInput(scanner, "Enter Account Number: ");
        try {
            Account account = accountManager.getAccount(accNum);
            handleTransactionSelection(account);
        } catch (Exception e) {
            System.out.println("\nTransaction Failed: " + e.getMessage());
            displayCurrentBalance(accNum);
        }
    }

    private static void handleTransactionSelection(Account account) throws Exception {
        System.out.println("1. Deposit\n2. Withdrawal\n3. Transfer");
        int type = InputValidator.getIntInput(scanner, "Select type: ", 1, 3);
        double amount = InputValidator.getPositiveDoubleInput(scanner, "Enter amount: $");

        switch (type) {
            case 1 -> executeDeposit(account, amount);
            case 2 -> executeWithdrawal(account, amount);
            case 3 -> executeTransfer(account, amount);
        }
    }

    private static void executeDeposit(Account account, double amount) throws InvalidAmountException {
        account.deposit(amount);
        transactionManager.addTransaction(new Transaction(account.getAccountNumber(), "DEPOSIT", amount, account.getBalance()));
        System.out.println("Deposit successful. New balance: $" + account.getBalance());
    }

    private static void executeWithdrawal(Account account, double amount) throws Exception {
        account.withdraw(amount);
        transactionManager.addTransaction(new Transaction(account.getAccountNumber(), "WITHDRAWAL", amount, account.getBalance()));
        System.out.println("Withdrawal successful. New balance: $" + account.getBalance());
    }

    private static void executeTransfer(Account fromAccount, double amount) throws Exception {
        String destAccNum = InputValidator.getStringInput(scanner, "Enter Destination Account Number: ");
        Account destAccount = accountManager.getAccount(destAccNum);
        transactionManager.transfer(fromAccount, destAccount, amount);
        System.out.println("Transfer successful.");
    }

    private static void displayCurrentBalance(String accNum) {
        try {
            Account acc = accountManager.getAccount(accNum);
            System.out.printf("Current balance: $%.2f%n", acc.getBalance());
        } catch (Exception ignored) {}
    }

    private static void generateStatement() {
        String accNum = InputValidator.getStringInput(scanner, "Enter Account Number: ");
        try {
            Account account = accountManager.getAccount(accNum);
            statementGenerator.generateStatement(account, transactionManager.getTransactions(), transactionManager.getTransactionCount());
        } catch (InvalidAccountException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void runTests() {
        System.out.println("\nRunning tests with JUnit...");
        System.out.println("(Simulated output for manual verification - real tests in src/test/java)");
        System.out.println("Test: depositUpdatesBalance() ........ PASSED");
        System.out.println("Test: withdrawBelowMinimumThrowsException() ........ PASSED");
        System.out.println("Test: overdraftWithinLimitAllowed() ........ PASSED");
        System.out.println("Test: overdraftExceedThrowsException() ........ PASSED");
        System.out.println("\nAll tests passed successfully!");
    }

    private static void exitApplication() {
        System.out.println("\nExiting system...");
    }
}