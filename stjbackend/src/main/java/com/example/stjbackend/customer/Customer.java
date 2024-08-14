package com.example.stjbackend.customer;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private LocalDate birthdate;

    @Column(nullable = false)
    private String tckn;

    @Column(nullable = false)
    private String phoneNumber;






    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + username + '\'' +
                ", password='" + password+ '\'' +
                ", email=" + email +
                '}';
    }


}
