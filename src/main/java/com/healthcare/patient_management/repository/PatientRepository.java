package com.healthcare.patient_management.repository;

import com.healthcare.patient_management.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient,Integer> {

    boolean existsByNationalId(String nationalId);
    List<Patient> findAllByNationalId(String nationalId);

    @Query(
            """
              SELECT p FROM Patient p
        WHERE 
            (:name IS NULL OR LOWER(p.nationalId) LIKE LOWER(CONCAT('%', :nationalId, '%')))
              """
    )
    List<Patient> searchAllByNationalId(@Param("nationalId") String nationalId);
}
