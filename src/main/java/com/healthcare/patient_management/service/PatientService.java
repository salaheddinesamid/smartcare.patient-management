package com.healthcare.patient_management.service;

import com.healthcare.patient_management.dto.NewPatientResponse;
import com.healthcare.patient_management.dto.PatientRequest;

public interface PatientService {

    /**
     * This method is responsible for creating new patient
     * @param patientRequest
     * @return a response
     */
    NewPatientResponse newPatient(PatientRequest patientRequest);
}
