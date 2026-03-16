
import models.*;
import services.*;
import exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for TransactionManager.
 */
public class TransactionManagerTest {
    private TransactionManager transactionManager;
    private Account acc1;
    private Account acc2;

    @BeforeEach
    void setUp() {
        transactionManager = new TransactionManager();
        Customer c1 = new RegularCustomer("User 1", 25, "111", "Addr 1");
        Customer c2 = new RegularCustomer("User 2", 35, "222", "Addr 2");
        try {
            acc1 = new SavingsAccount(c1, 1000.0);
            acc2 = new CheckingAccount(c2, 500.0);
        } catch (Exception ignored) {}
    }

    @Test
    void transferUpdatesBalances() throws Exception {
        transactionManager.transfer(acc1, acc2, 300.0);
        assertEquals(700.0, acc1.getBalance());
        assertEquals(800.0, acc2.getBalance());
    }

    @Test
    void transferRecordsTransactions() throws Exception {
        int initialCount = transactionManager.getTransactionCount();
        transactionManager.transfer(acc1, acc2, 100.0);
        assertEquals(initialCount + 2, transactionManager.getTransactionCount());
    }

    @Test
    void transferFailsOnInsufficientFunds() {
        assertThrows(InsufficientFundsException.class, () -> {
            transactionManager.transfer(acc1, acc2, 1500.0);
        });
    }
}
