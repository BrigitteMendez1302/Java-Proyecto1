package org.example.domain.repository;

import org.example.domain.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // Find customer by DNI
    Optional<Customer> findByDni(String dni);

    // Check if a customer does not have another email
    boolean existsByEmail(String email);
}
