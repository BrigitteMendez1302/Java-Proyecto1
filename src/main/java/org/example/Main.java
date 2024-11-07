package org.example;

import org.example.domain.model.BankAccount;
import org.example.domain.service.BankAccountService;
import org.example.domain.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

/**
 * Main application class for managing customers and bank accounts through a console interface.
 * Implements CommandLineRunner to provide a menu-driven interface for various banking operations.
 */
@SpringBootApplication
public class Main implements CommandLineRunner {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private BankAccountService bankAccountService;

    /**
     * Main method to launch the Spring Boot application.
     *
     * @param args Application arguments.
     */
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    /**
     * Runs the application, displaying a menu for user interaction.
     * 
     * @param args Application arguments.
     * @throws Exception if an error occurs during execution.
     */
    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Register Customer");
            System.out.println("2. Open Bank Account");
            System.out.println("3. Deposit Money");
            System.out.println("4. Withdraw Money");
            System.out.println("5. Check Balance");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Clear newline

            switch (choice) {
                case 1:
                    registerCustomer(scanner);
                    break;
                case 2:
                    openBankAccount(scanner);
                    break;
                case 3:
                    depositMoney(scanner);
                    break;
                case 4:
                    withdrawMoney(scanner);
                    break;
                case 5:
                    checkBalance(scanner);
                    break;
                case 6:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    /**
     * Registers a new customer by prompting the user for personal details.
     *
     * @param scanner Scanner for console input.
     */
    private void registerCustomer(Scanner scanner) {
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter DNI: ");
        String dni = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        try {
            var customer = customerService.registerCustomer(firstName, lastName, dni, email);
            System.out.println("Customer registered successfully: " + customer);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Opens a bank account for an existing customer, specifying the account type.
     *
     * @param scanner Scanner for console input.
     */
    private void openBankAccount(Scanner scanner) {
        System.out.print("Enter customer ID: ");
        Long customerId = scanner.nextLong();
        scanner.nextLine();  // Clear newline

        // Check if client exists before continue
        try {
            var customer = customerService.getCustomerById(customerId);
            System.out.println("Customer found: " + customer);

            // Continue with the process
            System.out.print("Enter account type (1 for SAVINGS, 2 for CHECKING): ");
            int accountTypeChoice = scanner.nextInt();
            scanner.nextLine();

            var accountType = (accountTypeChoice == 1) ? BankAccount.AccountType.SAVINGS : BankAccount.AccountType.CHECKING;

            var account = bankAccountService.openBankAccount(customerId, accountType);
            System.out.println("Bank account opened successfully: " + account);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Deposits money into an existing bank account.
     *
     * @param scanner Scanner for console input.
     */
    private void depositMoney(Scanner scanner) {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter amount to deposit: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        try {
            var account = bankAccountService.deposit(accountNumber, amount);
            System.out.println("Deposit successful. New balance: " + account.getBalance());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Withdraws money from an existing bank account, with validation checks.
     *
     * @param scanner Scanner for console input.
     */
    private void withdrawMoney(Scanner scanner) {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter amount to withdraw: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        try {
            var account = bankAccountService.withdraw(accountNumber, amount);
            System.out.println("Withdrawal successful. New balance: " + account.getBalance());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Checks the balance of an existing bank account.
     *
     * @param scanner Scanner for console input.
     */
    private void checkBalance(Scanner scanner) {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();

        try {
            double balance = bankAccountService.getBalance(accountNumber);
            System.out.println("Current balance: " + balance);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
