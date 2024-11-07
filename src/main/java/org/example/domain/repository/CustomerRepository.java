package org.example.domain.repository;

import org.example.domain.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for the Customer entity, providing methods for CRUD operations.
 * Extends JpaRepository to leverage standard data access operations.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * Finds a customer by their unique DNI.
     *
     * @param dni The unique identification number of the customer.
     * @return An Optional containing the Customer if found, or empty if not found.
     */
    Optional<Customer> findByDni(String dni);

    /**
     * Checks if a customer exists with the given email.
     *
     * @param email The email to check for uniqueness.
     * @return true if a customer with the email exists, false otherwise.
     */
    boolean existsByEmail(String email);
}
