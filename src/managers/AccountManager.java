package managers;

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
        for (int i = 0; i < accountCount; i++) {
            if (accounts[i].getAccountNumber().equals(accountNumber)) {
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

        System.out.println("\n" + "=".repeat(100));
        System.out.println("ACCOUNT LIST");
        System.out.println("=".repeat(100));
        System.out.printf("%-10s %-15s %-10s %-10s %-15s %-10s%n",
                "Acc. No.", "Customer", "Type", "Balance", "Features", "Status");
        System.out.println("-".repeat(100));

        double totalBalance = 0;
        int savingsCount = 0;
        int checkingCount = 0;

        for (int i = 0; i < accountCount; i++) {
            Account account = accounts[i];
            totalBalance += account.getBalance();

            String features = "";
            if (account instanceof SavingsAccount) {
                SavingsAccount sa = (SavingsAccount) account;
                features = String.format("%.1f%% int, $%.0f min",
                        sa.getMinimumBalance(), sa.getMinimumBalance());
                savingsCount++;
            } else if (account instanceof CheckingAccount) {
                CheckingAccount ca = (CheckingAccount) account;
                features = String.format("$%.0f OD, $%.0f fee",
                        ca.getOverdraftLimit(), ca.getMonthlyFee());
                checkingCount++;
            }

            System.out.printf("%-10s %-15s %-10s $%-9.2f %-15s %-10s%n",
                    account.getAccountNumber(),
                    account.getCustomer().getName(),
                    account.getAccountType(),
                    account.getBalance(),
                    features,
                    account.getStatus()
            );
        }

        System.out.println("-".repeat(100));
        System.out.printf("Total Accounts: %d (Savings: %d, Checking: %d)%n",
                accountCount, savingsCount, checkingCount);
        System.out.printf("Total Bank Balance: $%.2f%n", totalBalance);
    }

    public double getTotalBalance() {
        double total = 0;
        for (int i = 0; i < accountCount; i++) {
            total += accounts[i].getBalance();
        }
        return total;
    }

    public int getAccountCount() {
        return accountCount;
    }

    public Account[] getAccounts() {
        return accounts;
    }

    public int getSavingsCount() {
        int count = 0;
        for (int i = 0; i < accountCount; i++) {
            if (accounts[i] instanceof SavingsAccount) {
                count++;
            }
        }
        return count;
    }

    public int getCheckingCount() {
        int count = 0;
        for (int i = 0; i < accountCount; i++) {
            if (accounts[i] instanceof CheckingAccount) {
                count++;
            }
        }
        return count;
    }
}