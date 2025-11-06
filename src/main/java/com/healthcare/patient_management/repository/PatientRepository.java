package com.healthcare.patient_management.repository;

import com.healthcare.patient_management.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient,Integer> {

    boolean existsByNationalId(String nationalId);
    List<Patient> findAllByNationalId(String nationalId);
}
