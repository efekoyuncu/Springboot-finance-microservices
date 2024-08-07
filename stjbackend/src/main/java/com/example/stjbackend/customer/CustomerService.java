package com.example.stjbackend.customer;

import com.example.stjbackend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RestTemplate restTemplate;


    @Transactional
    public Customer registerCustomer(Customer customer) {
        if (customerRepository.findByUsername(customer.getUsername()).isPresent()) {
            throw new IllegalStateException("Username already taken");
        }
        if (customerRepository.findByEmail(customer.getEmail()).isPresent()) {
            throw new IllegalStateException("Email already taken");
        }
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        Customer registeredCustomer = customerRepository.save(customer);

        // Create wallet for the new customer
        try {
            String walletServiceUrl = "http://localhost:8081/api/wallets"; // Adjust the port and URL as needed
            Wallet newWallet = new Wallet();
            newWallet.setCustomerId(registeredCustomer.getId());
            restTemplate.postForObject(walletServiceUrl, newWallet, Wallet.class);
            System.out.println("Wallet created successfully for customer " + registeredCustomer.getId());
        } catch (Exception e) {
            throw new IllegalStateException("Failed to create wallet for customer " + registeredCustomer.getId(), e);
        }

        return registeredCustomer;
    }


    public String loginCustomer(String username, String password) {
        Optional<Customer> customerOpt = Optional.ofNullable(customerRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Customer not found")));
        if (customerOpt.isPresent() && passwordEncoder.matches(password, customerOpt.get().getPassword())) {
            // Generate JWT token (simplified for now)
            return "Login successful";
        } else {
            throw new RuntimeException("Invalid username or password");
        }
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
    }

    public CustomerDTO getCustomerDTOById(Long id) {
        Customer customer = getCustomerById(id);
        return mapToDTO(customer);
    }

    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(this::mapToDTO).collect(Collectors.toList());
    }


    private CustomerDTO mapToDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setUsername(customer.getUsername());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setBirthDate(customer.getBirthdate());
        customerDTO.setTckn(customer.getTckn());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());
        return customerDTO;
    }


    public Customer updateCustomer(Long id, Customer customerDetails) {

            Customer customer = getCustomerById(id);
            if (customer == null){
                throw new ResourceNotFoundException("Customer not found");
            }
            customer.setUsername(customerDetails.getUsername());
            customer.setPassword(passwordEncoder.encode(customerDetails.getPassword()));
            customer.setEmail(customerDetails.getEmail());
            customer.setBirthdate(customerDetails.getBirthdate());
            customer.setTckn(customerDetails.getTckn());
            customer.setPhoneNumber(customerDetails.getPhoneNumber());
            return customerRepository.save(customer);
        }



    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        customerRepository.delete(customer);
    }

    public List<Customer> getTopLoaders(int topN) {
        List<Wallet> topLoaders = restTemplate.getForObject("http://localhost:8081/api/wallets/top-loaders?topN=" + topN, List.class);
        List<Long> customerIds = topLoaders.stream().map(Wallet::getCustomerId).collect(Collectors.toList());
        return customerRepository.findAllById(customerIds);
    }



}
