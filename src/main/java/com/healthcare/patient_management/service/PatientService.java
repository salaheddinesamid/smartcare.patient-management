package com.healthcare.patient_management.service;

import com.healthcare.patient_management.dto.NewPatientResponse;
import com.healthcare.patient_management.dto.PatientRequest;
import com.healthcare.patient_management.dto.PatientResponseDto;

import java.util.List;

public interface PatientService {

    /**
     * This method is responsible for creating new patient
     * @param patientRequest
     * @return a response
     */
    NewPatientResponse newPatient(PatientRequest patientRequest);

    /**
     * This method is used to verify whether a specific patient exists in the system or not
     * @param nationalId
     * @return
     */
    Boolean verifyPatientExistence(String nationalId);

    /**
     * This method retrieves all the existing patients in the system
     * @return
     */
    List<PatientResponseDto> getAllPatients();


    /**
     * This function fetch a list of specific patients by ids
     * @return
     */
    List<PatientResponseDto> getPatients(List<Integer> ids);
}
