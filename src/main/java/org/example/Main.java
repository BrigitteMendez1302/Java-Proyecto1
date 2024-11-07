package org.example;

import org.example.domain.model.BankAccount;
import org.example.domain.service.BankAccountService;
import org.example.domain.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class Main implements CommandLineRunner {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private BankAccountService bankAccountService;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

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

    private void openBankAccount(Scanner scanner) {
        System.out.print("Enter customer ID: ");
        Long customerId = scanner.nextLong();
        scanner.nextLine();  // Clear newline

        // Verificar si el cliente existe antes de continuar
        try {
            var customer = customerService.getCustomerById(customerId);
            System.out.println("Customer found: " + customer);

            // Continuar con el proceso de apertura de cuenta bancaria
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
