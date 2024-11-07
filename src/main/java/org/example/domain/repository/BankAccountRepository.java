package org.example.domain.repository;

import org.example.domain.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    // Método para buscar una cuenta bancaria por su número de cuenta (debe ser único)
    Optional<BankAccount> findByAccountNumber(String accountNumber);
}