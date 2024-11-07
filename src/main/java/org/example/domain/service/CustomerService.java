package org.example.domain.service;

import org.example.domain.model.Customer;
import org.example.domain.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for managing customer-related operations.
 * Provides methods to register, find, update, and delete customers.
 */
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    /**
     * Constructor for CustomerService, injecting the required repository.
     *
     * @param customerRepository Repository for customer data access.
     */
    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Registers a new customer after validating unique DNI.
     *
     * @param firstName The customer's first name.
     * @param lastName The customer's last name.
     * @param dni The customer's unique identification number.
     * @param email The customer's email address.
     * @return The newly created customer.
     * @throws IllegalArgumentException if any rule is not met
     */
    public Customer registerCustomer(String firstName, String lastName, String dni, String email) {
        // Check DNI is unique
        if (customerRepository.findByDni(dni).isPresent()) {
            throw new IllegalArgumentException("DNI already exists.");
        }

        // Save the client
        Customer customer = new Customer(firstName, lastName, dni, email);
        return customerRepository.save(customer);
    }

    /**
     * Finds a customer by their ID.
     *
     * @param id The ID of the customer to find.
     * @return The found customer.
     * @throws IllegalArgumentException if the customer is not found.
     */
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
    }

     /**
     * Finds a customer by their DNI.
     *
     * @param dni The unique DNI of the customer.
     * @return The found customer.
     * @throws IllegalArgumentException if no customer with the given DNI is found.
     */
    public Customer getCustomerByDni(String dni) {
        return customerRepository.findByDni(dni)
                .orElseThrow(() -> new IllegalArgumentException("Customer with given DNI not found"));
    }

    /**
     * Updates the details of an existing customer.
     *
     * @param id The ID of the customer to update.
     * @param firstName The updated first name.
     * @param lastName The updated last name.
     * @param email The updated email address.
     * @return The updated customer.
     * @throws IllegalArgumentException if the customer is not found.
     */
    public Customer updateCustomer(Long id, String firstName, String lastName, String email) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);

        return customerRepository.save(customer);
    }

    /**
     * Deletes a customer by their ID.
     *
     * @param id The ID of the customer to delete.
     * @throws IllegalArgumentException if the customer is not found.
     */
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new IllegalArgumentException("Customer not found");
        }
        customerRepository.deleteById(id);
    }
}
