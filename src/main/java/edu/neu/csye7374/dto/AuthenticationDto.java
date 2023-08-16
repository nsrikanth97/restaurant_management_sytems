package edu.neu.csye7374.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthenticationDto {

    private String username;

    private String password;

    private String email;

    private String fingerPrint;

    private String userInput;

    private String phone;

    private String role;


}
