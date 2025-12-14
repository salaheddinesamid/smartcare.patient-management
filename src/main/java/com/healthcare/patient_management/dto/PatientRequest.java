package com.healthcare.patient_management.dto;

import lombok.Data;


@Data
public class PatientRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String roleName;
    private String nationalId;
    private String address;
    private Integer age;
    private String password;

    public PatientRequest(
            String firstName,
            String lastName,
            String email,
            String roleName,
            String nationalId,
            String address,
            String password
    ){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.roleName = roleName;
        this.nationalId = nationalId;
        this.address  = address;
        this.password = password;
    }
}
