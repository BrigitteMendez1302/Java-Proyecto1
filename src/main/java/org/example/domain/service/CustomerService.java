package org.example.domain.service;

import org.example.domain.model.Customer;
import org.example.domain.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Método para registrar un nuevo cliente
    public Customer registerCustomer(String firstName, String lastName, String dni, String email) {
        // Validación de unicidad del DNI
        if (customerRepository.findByDni(dni).isPresent()) {
            throw new IllegalArgumentException("DNI already exists.");
        }

        // Crear y guardar el cliente después de pasar las validaciones
        Customer customer = new Customer(firstName, lastName, dni, email);
        return customerRepository.save(customer);
    }

    // Método para buscar un cliente por su ID
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
    }

    // Método para buscar un cliente por su DNI
    public Customer getCustomerByDni(String dni) {
        return customerRepository.findByDni(dni)
                .orElseThrow(() -> new IllegalArgumentException("Customer with given DNI not found"));
    }

    // Método para actualizar los datos de un cliente
    public Customer updateCustomer(Long id, String firstName, String lastName, String email) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);

        return customerRepository.save(customer);
    }

    // Método para eliminar un cliente por ID
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new IllegalArgumentException("Customer not found");
        }
        customerRepository.deleteById(id);
    }
}
