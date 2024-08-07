package com.example.stjbackend.customer;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerDTO {
    private Long id;
    private String username;
    private String email;
    private LocalDate birthDate;
    private String tckn;
    private String phoneNumber;
}