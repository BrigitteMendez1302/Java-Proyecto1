package org.example.domain.model;
import javax.persistence.*;
import java.util.UUID;

/**
 * Represents a bank account belonging to a customer, which can be of type
 * savings or checking. Implements specific business rules for each account type.
 */
@Entity
@Table(name = "bank_accounts")
public class BankAccount {

    /**
     * Enum defining the types of bank accounts: Savings (SAVINGS) and Checking (CHECKING).
    */
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
    
    // Overdraft limit for checking accounts
    private static final double OVERDRAFT_LIMIT = -500.0;

    /**
     * Many-to-One relationship with the Customer entity. Each bank account belongs to a single customer.
    */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    /**
     * Default constructor required by JPA.
    */
    public BankAccount() {
    }

    /**
     * Constructor that initializes a bank account with a specific account type.
     * Generates a unique account number and sets the initial balance to 0.0.
     * 
     * @param accountType The type of account (SAVINGS or CHECKING).
     */
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

    /**
     * Deposits an amount into the account, increasing the balance.
     * 
     * @param amount The amount to deposit, must be positive.
     * @throws IllegalArgumentException if the amount is less than or equal to zero.
     */
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        this.balance += amount;
    }

    /**
     * Attempts to withdraw an amount from the account, applying specific business rules
     * for each account type.
     * 
     * @param amount The amount to withdraw, must be positive.
     * @return true if the withdrawal was successful, false if it could not be completed due to business rules.
     * @throws IllegalArgumentException if the amount is less than or equal to zero.
     */
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

    /**
     * Generates a unique account number using UUID.
     * 
     * @return A unique account number as a String.
     */
    private String generateAccountNumber() {
        return UUID.randomUUID().toString();
    }

    /**
     * Returns a String representation of the bank account, showing key account details.
     *
     * @return A string with the bank account's state.
    */
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
