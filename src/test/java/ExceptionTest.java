
import models.*;
import exceptions.*;
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
}
