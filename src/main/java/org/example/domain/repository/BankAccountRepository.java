package org.example.domain.repository;

import org.example.domain.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for BankAccount entity, providing methods for CRUD operations.
 * Extends JpaRepository to inherit standard data access operations.
 */
@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    /**
     * Retrieves a bank account by its unique account number.
     *
     * @param accountNumber The unique account number to search for.
     * @return An Optional containing the BankAccount if found, or empty if not found.
     */
    Optional<BankAccount> findByAccountNumber(String accountNumber);
}
