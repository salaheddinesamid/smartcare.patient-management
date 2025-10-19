package com.healthcare.patient_management.dto;

import lombok.Data;

@Data
public class NewUserResponseDto {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
}
