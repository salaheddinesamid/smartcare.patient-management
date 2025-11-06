package com.healthcare.patient_management.dto;

import lombok.Data;

@Data
public class PatientSearchResponseDto {

    private Integer patientId;
    private Integer userId;
    private String fullName;
}
