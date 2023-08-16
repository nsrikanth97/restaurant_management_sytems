package edu.neu.csye7374.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity(name = "user_details")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    @Column(name = "phone")
    private String phone;

    @Column(name = "username")
    private String username;

    @Column(name = "finger_print")
    private String fingerPrint;

}
