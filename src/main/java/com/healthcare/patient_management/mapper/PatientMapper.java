package com.healthcare.patient_management.mapper;

import com.healthcare.patient_management.dto.PatientRequest;
import com.healthcare.patient_management.model.Patient;
import com.healthcare.patient_management.model.PatientStatus;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper {

    public Patient mapToPatient(PatientRequest request){
        Patient patient = new Patient();
        patient.setNationalId(request.getNationalId());
        patient.setAge(request.getAge());
        patient.setAddress(request.getAddress());
        patient.setStatus(PatientStatus.ACTIVE); // set the patient status to active by default

        return patient;
    }
}
