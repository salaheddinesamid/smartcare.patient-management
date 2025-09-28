package com.healthcare.patient_management.dto;

import com.healthcare.patient_management.model.Patient;
import lombok.Data;

@Data
public class NewPatientResponse {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    private String nationalId;
    private String address;
    private String status;

    public NewPatientResponse(NewUserResponseDto newUserResponseDto, Patient patient){
        this.firstName = newUserResponseDto.getFirstName();
        this.lastName = newUserResponseDto.getLastName();
        this.email = newUserResponseDto.getEmail();
        this.userId = newUserResponseDto.getUserId();
        this.password = newUserResponseDto.getPassword();
        this.nationalId = patient.getNationalId();
        this.address = patient.getAddress();
        this.status = patient.getStatus().toString();
    }
}
