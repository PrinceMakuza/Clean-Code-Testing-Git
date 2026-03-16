
import models.*;
import exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Account classes.
 */
public class AccountTest {
    private Customer customer;
    private Account savingsAccount;
    private Account checkingAccount;

    @BeforeEach
    void setUp() {
        customer = new RegularCustomer("Test User", 30, "123456", "Test Addr");
        try {
            savingsAccount = new SavingsAccount(customer, 1000.0);
            checkingAccount = new CheckingAccount(customer, 500.0);
        } catch (Exception ignored) {}
    }

    @Test
    void depositUpdatesBalance() throws InvalidAmountException {
        savingsAccount.deposit(500.0);
        assertEquals(1500.0, savingsAccount.getBalance());
    }

    @Test
    void withdrawUpdatesBalance() throws Exception {
        savingsAccount.withdraw(200.0);
        assertEquals(800.0, savingsAccount.getBalance());
    }

    @Test
    void withdrawBelowMinimumThrowsException() {
        assertThrows(InsufficientFundsException.class, () -> {
            savingsAccount.withdraw(600.0); // 1000 - 600 = 400 < 500 min
        });
    }

    @Test
    void overdraftWithinLimitAllowed() throws Exception {
        checkingAccount.withdraw(1200.0); // 500 - 1200 = -700 (within 1000 limit)
        assertEquals(-700.0, checkingAccount.getBalance());
    }

    @Test
    void overdraftExceedThrowsException() {
        assertThrows(OverdraftExceededException.class, () -> {
            checkingAccount.withdraw(1600.0); // 500 - 1600 = -1100 (exceeds 1000 limit)
        });
    }
}
