package org.example.domain.service;

import org.example.domain.model.BankAccount;
import org.example.domain.model.Customer;
import org.example.domain.repository.BankAccountRepository;
import org.example.domain.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public BankAccountService(BankAccountRepository bankAccountRepository, CustomerRepository customerRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.customerRepository = customerRepository;
    }

    // Método para abrir una cuenta bancaria para un cliente existente
    public BankAccount openBankAccount(Long customerId, BankAccount.AccountType accountType) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        BankAccount account = new BankAccount(accountType);
        account.setCustomer(customer);
        customer.addBankAccount(account);
        return bankAccountRepository.save(account);
    }

    // Método para depositar dinero en una cuenta bancaria
    public BankAccount deposit(String accountNumber, double amount) {
        BankAccount account = bankAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        account.deposit(amount);
        return bankAccountRepository.save(account);
    }

    // Método para retirar dinero de una cuenta bancaria con validación de reglas de negocio
    public BankAccount withdraw(String accountNumber, double amount) {
        BankAccount account = bankAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        if (!account.withdraw(amount)) {
            throw new IllegalArgumentException("Insufficient funds or overdraft limit exceeded.");
        }
        return bankAccountRepository.save(account);
    }

    // Método para consultar el saldo de una cuenta bancaria
    public double getBalance(String accountNumber) {
        BankAccount account = bankAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        return account.getBalance();
    }
}
