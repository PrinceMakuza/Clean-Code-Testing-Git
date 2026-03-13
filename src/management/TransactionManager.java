package management;

import models.Transaction;

public class TransactionManager {
    private Transaction[] transactions;
    private int transactionCount;
    private static final int MAX_TRANSACTIONS = 200;

    public TransactionManager() {
        this.transactions = new Transaction[MAX_TRANSACTIONS];
        this.transactionCount = 0;
    }

    public boolean addTransaction(Transaction transaction) {
        if (transactionCount < MAX_TRANSACTIONS) {
            transactions[transactionCount] = transaction;
            transactionCount++;
            return true;
        }
        return false;
    }

    public void viewTransactionsByAccount(String accountNumber) {
        Transaction[] accountTransactions = new Transaction[transactionCount];
        int count = 0;

        for (int i = 0; i < transactionCount; i++) {
            if (transactions[i].getAccountNumber().equals(accountNumber)) {
                accountTransactions[count] = transactions[i];
                count++;
            }
        }

        if (count == 0) {
            System.out.println("-".repeat(50));
            System.out.println("No transactions recorded for this account.");
            System.out.println("-".repeat(50));
            return;
        }

        System.out.println("TRANSACTION HISTORY");
        System.out.println("-".repeat(80));
        System.out.printf("%-8s | %-20s | %-10s | %-12s | %-12s%n",
                "TXN ID", "DATE/TIME", "TYPE", "AMOUNT", "BALANCE");
        System.out.println("-".repeat(80));

        for (int i = count - 1; i >= 0; i--) {
            accountTransactions[i].displayTransactionDetails();
        }

        System.out.println("-".repeat(80));
        System.out.printf("Total Transactions: %d%n", count);
        System.out.printf("Total Deposits: $%.2f%n", calculateTotalDeposits(accountNumber));
        System.out.printf("Total Withdrawals: $%.2f%n", calculateTotalWithdrawals(accountNumber));

        double netChange = calculateTotalDeposits(accountNumber) - calculateTotalWithdrawals(accountNumber);
        System.out.printf("Net Change: %s$%.2f%n",
                netChange >= 0 ? "+" : "-", Math.abs(netChange));
    }

    public double calculateTotalDeposits(String accountNumber) {
        double total = 0;
        for (int i = 0; i < transactionCount; i++) {
            if (transactions[i].getAccountNumber().equals(accountNumber) &&
                    transactions[i].getType().equalsIgnoreCase("DEPOSIT")) {
                total += transactions[i].getAmount();
            }
        }
        return total;
    }

    public double calculateTotalWithdrawals(String accountNumber) {
        double total = 0;
        for (int i = 0; i < transactionCount; i++) {
            if (transactions[i].getAccountNumber().equals(accountNumber) &&
                    transactions[i].getType().equalsIgnoreCase("WITHDRAWAL")) {
                total += transactions[i].getAmount();
            }
        }
        return total;
    }
}