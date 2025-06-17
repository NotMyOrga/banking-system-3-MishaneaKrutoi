package bank;

import java.util.*;
import main.Person;

public class Bank {
    private final Map<Person, BankAccount> bankAccounts = new HashMap<>();

    public void registerCustomer(Person person, double initialDeposit) {
        if (!bankAccounts.containsKey(person)) {
            bankAccounts.put(person, new BankAccount(person, initialDeposit));
        }
    }

    public boolean transfer(Person from, Person to, double amount) {
        BankAccount sender = bankAccounts.get(from);
        BankAccount receiver = bankAccounts.get(to);

        if (sender == null || receiver == null) {
            return false;
        }

        if (sender.withdraw(amount)) {
            receiver.deposit(amount);
            return true;
        }

        return false;
    }

    public double getBalance(Person customer) {
        if (bankAccounts.containsKey(customer)) {
            return bankAccounts.get(customer).getBalance();
        }
        return 0.0;
    }
}

