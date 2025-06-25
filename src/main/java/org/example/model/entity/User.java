package org.example.model.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Builder
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    @Column(name = "is_enabled")
    private boolean enabled;
    private String role;

    @PrePersist
    protected void onCreate(){
        createdAt = LocalDate.now();
        updatedAt=LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate(){
        updatedAt=LocalDate.now();
    }
}
