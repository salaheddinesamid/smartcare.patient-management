package com.healthcare.patient_management.repository;

import com.healthcare.patient_management.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient,Integer> {

    boolean existsByNationalId(String nationalId);
}
