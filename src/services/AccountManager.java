package services;

import models.Account;
import models.SavingsAccount;
import models.CheckingAccount;

public class AccountManager {
    private Account[] accounts;
    private int accountCount;
    private static final int MAX_ACCOUNTS = 50;

    public AccountManager() {
        this.accounts = new Account[MAX_ACCOUNTS];
        this.accountCount = 0;
    }

    public boolean addAccount(Account account) {
        if (accountCount < MAX_ACCOUNTS) {
            accounts[accountCount] = account;
            accountCount++;
            return true;
        }
        return false;
    }

    public Account findAccount(String accountNumber) {
        String searchInput = accountNumber.toUpperCase();

        for (int i = 0; i < accountCount; i++) {
            if (accounts[i].getAccountNumber().toUpperCase().equals(searchInput)) {
                return accounts[i];
            }
        }
        return null;
    }

    public void viewAllAccounts() {
        if (accountCount == 0) {
            System.out.println("No accounts registered.");
            return;
        }

        System.out.println("\nACCOUNT LISTING");
        System.out.println("-".repeat(80));
        System.out.printf("%-8s | %-22s| %-12s   | %-14s | %-18s%n",
                "ACC NO", "CUSTOMER NAME", "TYPE", "BALANCE", "STATUS");
        System.out.println("-".repeat(80));

        double totalBalance = 0;

        for (int i = 0; i < accountCount; i++) {
            Account account = accounts[i];
            totalBalance += account.getBalance();

            System.out.printf("%-8s | %-21s | %-14s | %-14s | %-10s%n",
                    account.getAccountNumber(),
                    account.getCustomer().getName(),
                    account.getAccountType(),
                    String.format("%,.2f", account.getBalance()),
                    account.getStatus()
            );

            if (account instanceof SavingsAccount) {
                SavingsAccount sa = (SavingsAccount) account;
                System.out.printf("         | %s %.1f%% | %s $%.2f%n",
                        "Interest Rate:",
                        sa.getMinimumBalance(),
                        "Min Balance: $",
                        sa.getMinimumBalance()
                );
                System.out.println("-".repeat(80));
            } else if (account instanceof CheckingAccount) {
                CheckingAccount ca = (CheckingAccount) account;
                System.out.printf("         | %s $%.2f | %s $%.2f%n",
                        "Overdraft Limit: $",
                        ca.getOverdraftLimit(),
                        "Monthly Fee: $",
                        ca.getMonthlyFee()
                );
                System.out.println("-".repeat(80));
            }
        }
        System.out.printf("\nTotal Accounts: %d%n", accountCount);
        System.out.printf("Total Bank Balance: $%,.2f%n", totalBalance);
    }

    public int getAccountCount() {
        return accountCount;
    }
}