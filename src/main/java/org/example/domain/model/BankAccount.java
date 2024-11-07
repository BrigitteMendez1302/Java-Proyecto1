package org.example.domain.model;
import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "bank_accounts")
public class BankAccount {

    public enum AccountType {
        SAVINGS, CHECKING
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_number", nullable = false, unique = true)
    private String accountNumber;

    @Column(name = "balance", nullable = false)
    private double balance;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false)
    private AccountType accountType;

    private static final double OVERDRAFT_LIMIT = -500.0;

    // Many-to-One relationship with Customer
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    // Default constructor required by JPA
    public BankAccount() {
    }

    // Constructor with parameters
    public BankAccount(AccountType accountType) {
        this.accountType = accountType;
        this.accountNumber = generateAccountNumber();
        this.balance = 0.0;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    // Deposit money into the account
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        this.balance += amount;
    }

    // Withdraw money from the account
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }

        // Apply withdrawal rules based on account type
        if (accountType == AccountType.SAVINGS) {
            // Savings accounts cannot have a negative balance
            if (balance - amount < 0) {
                System.out.println("Insufficient funds. Savings accounts cannot have a negative balance.");
                System.out.println("Current balance: " + balance);
                return false;
            }
        } else if (accountType == AccountType.CHECKING) {
            // Checking accounts can go negative up to the overdraft limit
            if (balance - amount < OVERDRAFT_LIMIT) {
                System.out.println("Overdraft limit exceeded. Maximum overdraft is " + OVERDRAFT_LIMIT);
                System.out.println("Current balance: " + balance);
                return false;
            }
        }

        this.balance -= amount;
        System.out.println("Withdrawal successful. New balance: " + this.balance);
        return true;
    }

    // Generate a unique account number
    private String generateAccountNumber() {
        return UUID.randomUUID().toString();
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "id=" + id +
                ", accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                ", accountType=" + accountType +
                ", customerId=" + (customer != null ? customer.getId() : "null") +
                '}';
    }
}