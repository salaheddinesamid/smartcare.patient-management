package com.healthcare.patient_management.service.implementation;

import com.healthcare.patient_management.dto.*;
import com.healthcare.patient_management.exception.PatientAlreadyExistsException;
import com.healthcare.patient_management.mapper.PatientMapper;
import com.healthcare.patient_management.model.Patient;
import com.healthcare.patient_management.repository.PatientRepository;
import com.healthcare.patient_management.service.PatientService;
import com.healthcare.patient_management.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final RestTemplate restTemplate;
    private final UserUtils userUtils;
    private final PatientMapper patientMapper;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository, RestTemplate restTemplate, UserUtils userUtils, PatientMapper patientMapper) {
        this.patientRepository = patientRepository;
        this.restTemplate = restTemplate;
        this.userUtils = userUtils;
        this.patientMapper = patientMapper;
    }


    @Override
    public NewPatientResponse newPatient(PatientRequest patientRequest) {

        // Check if the patient already exists:
        if(patientRepository.existsByNationalId(patientRequest.getNationalId())){
            throw new PatientAlreadyExistsException(patientRequest.getNationalId());
        }

        // Create new user:
        NewUserRequestDto newUserRequestDto = new NewUserRequestDto(
                patientRequest.getFirstName(),
                patientRequest.getLastName(),
                patientRequest.getEmail(),
                patientRequest.getPassword(),
                "PATIENT"
        );

        NewUserResponseDto user = userUtils.createUser(newUserRequestDto);
        // Create new patient:
        Patient patient = patientMapper.mapToPatient(patientRequest);
        patient.setUserId(user.getUserId()); // set the user id from the new created user
        patientRepository.save(patient); // save the patient in the database

        return new NewPatientResponse(
                user,
                patient
        );

    }

    @Override
    public Boolean verifyPatientExistence(String nationalId) {
        return
                patientRepository.existsByNationalId(nationalId);
    }
}
