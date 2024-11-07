package org.example.domain.service;

import org.example.domain.model.BankAccount;
import org.example.domain.model.Customer;
import org.example.domain.repository.BankAccountRepository;
import org.example.domain.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class for handling bank account operations. Provides methods for
 * opening accounts, depositing, withdrawing, and checking account balances.
 */
@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final CustomerRepository customerRepository;

    /**
     * Constructor for BankAccountService, injecting the required repositories.
     * 
     * @param bankAccountRepository Repository for bank account data access.
     * @param customerRepository Repository for customer data access.
     */
    @Autowired
    public BankAccountService(BankAccountRepository bankAccountRepository, CustomerRepository customerRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.customerRepository = customerRepository;
    }

    /**
     * Opens a bank account for an existing customer.
     * 
     * @param customerId The ID of the customer who will own the account.
     * @param accountType The type of account to be created (SAVINGS or CHECKING).
     * @return The newly created and saved bank account.
     * @throws IllegalArgumentException if the customer is not found.
     */
    public BankAccount openBankAccount(Long customerId, BankAccount.AccountType accountType) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        BankAccount account = new BankAccount(accountType);
        account.setCustomer(customer);
        customer.addBankAccount(account);
        return bankAccountRepository.save(account);
    }

    /**
     * Deposits money into a bank account.
     * 
     * @param accountNumber The unique account number.
     * @param amount The amount to deposit, must be positive.
     * @return The updated bank account after the deposit.
     * @throws IllegalArgumentException if the account is not found or amount is invalid.
     */
    public BankAccount deposit(String accountNumber, double amount) {
        BankAccount account = bankAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        account.deposit(amount);
        return bankAccountRepository.save(account);
    }

    /**
     * Withdraws money from a bank account, applying business rules for balance and overdraft.
     * 
     * @param accountNumber The unique account number.
     * @param amount The amount to withdraw, must be positive.
     * @return The updated bank account after the withdrawal.
     * @throws IllegalArgumentException if the account is not found or rules are violated.
     */
    public BankAccount withdraw(String accountNumber, double amount) {
        BankAccount account = bankAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        if (!account.withdraw(amount)) {
            throw new IllegalArgumentException("Insufficient funds or overdraft limit exceeded.");
        }
        return bankAccountRepository.save(account);
    }

    /**
     * Retrieves the balance of a bank account.
     * 
     * @param accountNumber The unique account number.
     * @return The current balance of the account.
     * @throws IllegalArgumentException if the account is not found.
     */
    public double getBalance(String accountNumber) {
        BankAccount account = bankAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        return account.getBalance();
    }
}
