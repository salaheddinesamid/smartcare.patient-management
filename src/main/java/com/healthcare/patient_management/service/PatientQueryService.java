package com.healthcare.patient_management.service;

import com.healthcare.patient_management.dto.PatientResponseDto;
import com.healthcare.patient_management.dto.PatientSearchResponseDto;

import java.util.List;

public interface PatientQueryService {

    /**
     *
     * @return
     */
    List<PatientResponseDto> getAllPatients();
    /**
     *
     * @param ids
     * @return
     */
    List<PatientResponseDto> getPatients(List<Integer> ids);

    /**
     *
     * @param id
     * @return
     */
    PatientResponseDto getPatient(Integer id);

    /**
     * This function returns a list of patients that matches the name
     * @param name
     * @return a list of users that closely match a patient's national ID
     */
    List<PatientSearchResponseDto> searchPatients(String name);
}
