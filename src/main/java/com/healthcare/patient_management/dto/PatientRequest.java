package com.healthcare.patient_management.dto;

import lombok.Data;

@Data
public class PatientRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String nationalId;
    private String address;
    private String password;
}
