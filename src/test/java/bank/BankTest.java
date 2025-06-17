package bank;

import main.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BankTest {

    private Bank bank;
    private Person alice;
    private Person bob;

    @BeforeEach
    public void setUp() {
        bank = new Bank();
        alice = new Person("Alice");
        bob = new Person("Bob");
    }

    @Test
    public void testMainFlow() {
        Bank bank = new Bank();

        Person alice = new Person("Alice");
        Person bob = new Person("Bob");

        bank.registerCustomer(bob, 0);
        bank.registerCustomer(alice, 100);

        boolean transfer = bank.transfer(alice, bob, 20);

        assertTrue(transfer, "Transfer should succeed when Alice has enough balance");

        assertEquals(80.0, bank.getBalance(alice), 0.001, "Alice's balance should be 80");
        assertEquals(20.0, bank.getBalance(bob), 0.001, "Bob's balance should be 20");
    }

    @Test
    public void testRegisterCustomerStoresAccountWithCorrectBalance() {
        bank.registerCustomer(alice, 100.0);
        assertEquals(100.0, bank.getBalance(alice), 0.001);
    }

    @Test
    public void testDepositIncreasesBalance() {
        bank.registerCustomer(alice, 50.0);
        BankAccount account = getAccountFor(bank, alice);
        account.deposit(30.0);
        assertEquals(80.0, account.getBalance(), 0.001);
    }

    @Test
    public void testWithdrawDecreasesBalanceIfEnoughMoney() {
        bank.registerCustomer(alice, 100.0);
        BankAccount account = getAccountFor(bank, alice);
        boolean success = account.withdraw(60.0);
        assertTrue(success);
        assertEquals(40.0, account.getBalance(), 0.001);
    }

    @Test
    public void testWithdrawFailsIfNotEnoughMoney() {
        bank.registerCustomer(alice, 40.0);
        BankAccount account = getAccountFor(bank, alice);
        boolean success = account.withdraw(50.0);
        assertFalse(success);
        assertEquals(40.0, account.getBalance(), 0.001);
    }

    @Test
    public void testTransferMovesMoneyBetweenAccounts() {
        bank.registerCustomer(alice, 100.0);
        bank.registerCustomer(bob, 50.0);

        boolean result = bank.transfer(alice, bob, 30.0);

        assertTrue(result);
        assertEquals(70.0, bank.getBalance(alice), 0.001);
        assertEquals(80.0, bank.getBalance(bob), 0.001);
    }

    @Test
    public void testTransferFailsIfInsufficientFunds() {
        bank.registerCustomer(alice, 10.0);
        bank.registerCustomer(bob, 100.0);

        boolean result = bank.transfer(alice, bob, 50.0);

        assertFalse(result);
        assertEquals(10.0, bank.getBalance(alice), 0.001);
        assertEquals(100.0, bank.getBalance(bob), 0.001);
    }

    private BankAccount getAccountFor(Bank bank, Person person) {
        try {
            var field = Bank.class.getDeclaredField("bankAccounts");
            field.setAccessible(true);
            var accounts = (java.util.Map<Person, BankAccount>) field.get(bank);
            return accounts.get(person);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get account", e);
        }
    }
}
