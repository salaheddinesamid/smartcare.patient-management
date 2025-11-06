package com.healthcare.patient_management.dto;

import com.healthcare.patient_management.model.Patient;
import lombok.Data;

@Data
public class PatientSearchResponseDto {

    private Integer patientId;
    private Integer userId;
    private String fullName;

    public PatientSearchResponseDto(Patient patient, UserDto user){
        this.fullName = String.format("%s %s",user.getFirstName(),user.getLastName());
        this.userId = user.getUserId();
        this.patientId  = patient.getPatientId();
    }
}
