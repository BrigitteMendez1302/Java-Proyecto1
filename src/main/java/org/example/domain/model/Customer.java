package org.example.domain.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Represents a customer entity with personal details and associated bank accounts.
 * A customer can hold multiple bank accounts.
*/
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "dni", nullable = false, unique = true)
    private String dni;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /**
     * One-to-Many relationship with the BankAccount entity. A customer can have multiple bank accounts.
     * All associated accounts are removed if the customer is deleted.
    */
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<BankAccount> bankAccounts = new ArrayList<>();

    /**
     * Default constructor required by JPA.
    */
    public Customer() {
    }

    /**
     * Constructor that initializes a customer with basic information.
     *
     * @param firstName The customer's first name.
     * @param lastName The customer's last name.
     * @param dni The unique identification number for the customer.
     * @param email The customer's email address, with format validation.
     */
    public Customer(String firstName, String lastName, String dni, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dni = dni;
        setEmail(email); // Applies validation
    }

    // Getters and Setters
    
    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the customer's first name, ensuring it is not null or empty.
     * 
     * @param firstName The customer's first name.
     * @throws IllegalArgumentException if firstName is null or empty.
     */
    public void setFirstName(String firstName) {
        if (firstName == null || firstName.isEmpty()) {
            throw new IllegalArgumentException("First name is required.");
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the customer's last name, ensuring it is not null or empty.
     * 
     * @param lastName The customer's last name.
     * @throws IllegalArgumentException if lastName is null or empty.
     */
    public void setLastName(String lastName) {
        if (lastName == null || lastName.isEmpty()) {
            throw new IllegalArgumentException("Last name is required.");
        }
        this.lastName = lastName;
    }

    public String getDni() {
        return dni;
    }

    /**
     * Sets the customer's unique identification number (DNI).
     * 
     * @param dni The customer's DNI, must be unique and not null.
     * @throws IllegalArgumentException if dni is null or empty.
     */
    public void setDni(String dni) {
        if (dni == null || dni.isEmpty()) {
            throw new IllegalArgumentException("DNI is required.");
        }
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    /**
     * Sets the customer's email address with format validation.
     * 
     * @param email The customer's email address.
     * @throws IllegalArgumentException if email is invalid in format.
     */
    public void setEmail(String email) {
        if (email == null || !isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        this.email = email;
    }

    /**
     * Adds a bank account to the customer's list of accounts.
     * 
     * @param account The bank account to add.
     */
    public void addBankAccount(BankAccount account) {
        bankAccounts.add(account);
        account.setCustomer(this);
    }

    /**
     * Retrieves all bank accounts with a positive balance.
     * 
     * @return A list of bank accounts with a balance greater than zero.
     */
    public List<BankAccount> getPositiveBalanceAccounts() {
        return bankAccounts.stream()
                .filter(account -> account.getBalance() > 0)
                .collect(Collectors.toList());
    }

    /**
     * Validates the format of the email address.
     * 
     * @param email The email address to validate.
     * @return true if the email is valid, false otherwise.
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email.matches(emailRegex);
    }

    /**
     * Provides a string representation of the customer's key details.
     *
     * @return A string with the customer's main information.
     */
    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dni='" + dni + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
