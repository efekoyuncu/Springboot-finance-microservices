package com.example.stjbackend.config;

import com.example.stjbackend.customer.Customer;
import com.example.stjbackend.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerConfig {

    @Autowired
    private CustomerRepository customerRepository;

}
