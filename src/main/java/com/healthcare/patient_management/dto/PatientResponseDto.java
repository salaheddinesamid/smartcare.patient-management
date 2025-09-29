package com.healthcare.patient_management.dto;

import com.healthcare.patient_management.model.Patient;
import lombok.Data;

@Data
public class PatientResponseDto {

    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    private Integer patientId;
    private String nationalId;
    private String address;
    private String status;

    public PatientResponseDto(Patient patient, UserDto user){
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.patientId = patient.getPatientId();
        this.nationalId = patient.getNationalId();
        this.address = patient.getAddress();
        this.status = patient.getStatus().toString();
    }
}
