package services;

import models.Account;
import models.Transaction;
import java.util.stream.IntStream;

/**
 * Service for generating and displaying account statements.
 */
public class StatementGenerator {

    /**
     * Generates and prints a formatted account statement.
     * @param account The account for which to generate the statement.
     * @param transactions All transactions in the system.
     * @param transactionCount Total number of transactions.
     */
    public void generateStatement(Account account, Transaction[] transactions, int transactionCount) {
        String accNum = account.getAccountNumber();
        
        System.out.println("\nGENERATE ACCOUNT STATEMENT");
        System.out.println("-".repeat(30));
        System.out.println("\nAccount: " + account.getCustomer().getName() + " (" + account.getAccountType() + ")");
        System.out.printf("Current Balance: $%.2f%n", account.getBalance());

        System.out.println("\nTransactions:");
        
        double deposits = 0;
        double withdrawals = 0;
        int count = 0;

        // Logic to filter and display transactions for this account
        for (int i = 0; i < transactionCount; i++) {
            Transaction txn = transactions[i];
            if (txn.getAccountNumber().equals(accNum)) {
                txn.displayTransactionDetails();
                
                if (txn.getType().contains("DEPOSIT") || txn.getType().equals("TRANSFER-IN")) {
                    deposits += txn.getAmount();
                } else {
                    withdrawals += txn.getAmount();
                }
                count++;
            }
        }

        if (count == 0) {
            System.out.println("No transactions found for this account.");
        }

        double netChange = deposits - withdrawals;
        System.out.println("\n" + "-".repeat(50));
        System.out.printf("Net Change: %s$%.2f%n", (netChange >= 0 ? "+" : "-"), Math.abs(netChange));
        System.out.println("-".repeat(50));
        System.out.println("Statement generated successfully.");
    }
}
