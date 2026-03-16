package services;

import models.Account;
import models.SavingsAccount;
import models.CheckingAccount;
import exceptions.InvalidAccountException;
import utils.ValidationUtils;

/**
 * Manages account creation, retrieval, and listing.
 */
public class AccountManager {
    private final Account[] accounts;
    private int accountCount;
    private static final int MAX_ACCOUNTS = 50;

    public AccountManager() {
        this.accounts = new Account[MAX_ACCOUNTS];
        this.accountCount = 0;
    }

    /**
     * Adds a new account to the system.
     * @param account The account to add.
     * @return true if successful, false if capacity is reached.
     */
    public boolean addAccount(Account account) {
        if (accountCount < MAX_ACCOUNTS) {
            accounts[accountCount] = account;
            accountCount++;
            return true;
        }
        return false;
    }

    /**
     * Finds an account by its account number.
     * @param accountNumber The account number to search for.
     * @return The found Account.
     * @throws InvalidAccountException if the account is not found.
     */
    public Account getAccount(String accountNumber) throws InvalidAccountException {
        Account account = findAccount(accountNumber);
        ValidationUtils.validateAccountExists(account);
        return account;
    }

    /**
     * Helper method to search for an account in the array.
     * @param accountNumber The account number.
     * @return The account if found, otherwise null.
     */
    private Account findAccount(String accountNumber) {
        if (accountNumber == null) return null;
        String searchInput = accountNumber.toUpperCase();

        for (int i = 0; i < accountCount; i++) {
            if (accounts[i].getAccountNumber().equalsIgnoreCase(searchInput)) {
                return accounts[i];
            }
        }
        return null;
    }

    /**
     * Displays all registered accounts in the system.
     */
    public void viewAllAccounts() {
        if (isSystemEmpty()) {
            System.out.println("No accounts registered.");
            return;
        }

        printHeader();
        double totalBalance = displayAccountList();
        printFooter(totalBalance);
    }

    private boolean isSystemEmpty() {
        return accountCount == 0;
    }

    private void printHeader() {
        System.out.println("\nACCOUNT LISTING");
        System.out.println("-".repeat(80));
        System.out.printf("%-8s | %-22s| %-12s   | %-14s | %-18s%n",
                "ACC NO", "CUSTOMER NAME", "TYPE", "BALANCE", "STATUS");
        System.out.println("-".repeat(80));
    }

    private double displayAccountList() {
        double totalBalance = 0;
        for (int i = 0; i < accountCount; i++) {
            Account account = accounts[i];
            totalBalance += account.getBalance();
            printAccountRow(account);
            printAccountSpecialDetails(account);
        }
        return totalBalance;
    }

    private void printAccountRow(Account account) {
        System.out.printf("%-8s | %-21s | %-14s | %-14s | %-10s%n",
                account.getAccountNumber(),
                account.getCustomer().getName(),
                account.getAccountType(),
                String.format("%,.2f", account.getBalance()),
                account.getStatus()
        );
    }

    private void printAccountSpecialDetails(Account account) {
        if (account instanceof SavingsAccount sa) {
            System.out.printf("         | Interest: %.1f%% | Min Balance: $%.2f%n",
                    3.5, // Fixed for simplicity here
                    sa.getMinimumBalance()
            );
            System.out.println("-".repeat(80));
        } else if (account instanceof CheckingAccount ca) {
            System.out.printf("         | Overdraft: $%.2f | Monthly Fee: $%.2f%n",
                    ca.getOverdraftLimit(),
                    ca.getMonthlyFee()
            );
            System.out.println("-".repeat(80));
        }
    }

    private void printFooter(double totalBalance) {
        System.out.printf("\nTotal Accounts: %d%n", accountCount);
        System.out.printf("Total Bank Balance: $%,.2f%n", totalBalance);
    }

    public int getAccountCount() {
        return accountCount;
    }
}