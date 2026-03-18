
import models.*;
import exceptions.*;
import services.AccountManager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for custom Exception throwing.
 */
public class ExceptionTest {

    @Test
    void depositNegativeAmountThrowsException() {
        Customer customer = new RegularCustomer("Test", 20, "123", "Addr");
        Account account = new CheckingAccount(customer, 100.0);
        assertThrows(InvalidAmountException.class, () -> {
            account.deposit(-50.0);
        });
    }

    @Test
    void savingsAccountInitialDepositInvalidThrowsException() {
        Customer customer = new RegularCustomer("Test", 20, "123", "Addr");
        assertThrows(IllegalArgumentException.class, () -> {
            new SavingsAccount(customer, 100.0); // 100 < 500 min
        });
    }

    @Test
    void savingsAccountWithdrawUnderMinimumThrowsException() {
        Customer customer = new RegularCustomer("Test", 20, "123", "Addr");
        Account account = new SavingsAccount(customer, 600.0);
        assertThrows(InsufficientFundsException.class, () -> {
            account.withdraw(200.0); // 600 - 200 = 400 < 500
        });
    }

    @Test
    void checkingAccountOverdraftExceededThrowsException() {
        Customer customer = new RegularCustomer("Test", 20, "123", "Addr");
        Account account = new CheckingAccount(customer, 100.0);
        assertThrows(OverdraftExceededException.class, () -> {
            account.withdraw(1200.0); // 100 - 1200 = -1100 < -1000
        });
    }

    @Test
    void accountManagerInvalidAccountLookupThrowsException() {
        AccountManager manager = new AccountManager();
        assertThrows(InvalidAccountException.class, () -> {
            manager.getAccount("NONEXISTENT");
        });
    }
}
