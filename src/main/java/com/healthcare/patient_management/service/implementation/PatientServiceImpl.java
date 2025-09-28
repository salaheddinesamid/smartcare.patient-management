package com.healthcare.patient_management.service.implementation;

import com.healthcare.patient_management.dto.*;
import com.healthcare.patient_management.exception.PatientAlreadyExistsException;
import com.healthcare.patient_management.model.Patient;
import com.healthcare.patient_management.model.PatientStatus;
import com.healthcare.patient_management.repository.PatientRepository;
import com.healthcare.patient_management.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;


@Service
public class PatientServiceImpl implements PatientService {

    private static final String USER_MANAGEMENT_URI = "http://localhost:8080";
    private final PatientRepository patientRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository, RestTemplate restTemplate) {
        this.patientRepository = patientRepository;
        this.restTemplate = restTemplate;
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

        NewUserResponseDto user = createUser(newUserRequestDto);
        // Create new patient:
        Patient patient = new Patient();
        patient.setNationalId(patientRequest.getNationalId());
        patient.setUserId(user.getUserId());
        patient.setNationalId(patientRequest.getNationalId());
        patient.setAddress(patientRequest.getAddress());
        patient.setStatus(PatientStatus.REGISTERED);

        patientRepository.save(patient);

        return new NewPatientResponse(
                user,
                patient
        );

    }

    public NewUserResponseDto createUser(NewUserRequestDto newUserRequestDto){

        String uri = USER_MANAGEMENT_URI + "/api/user/new";

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<NewUserRequestDto> entity = new HttpEntity<>(newUserRequestDto,headers);
        ResponseEntity<ApiResponse<NewUserResponseDto>> response =
                restTemplate.exchange(
                        uri,
                        HttpMethod.POST,
                        entity,
                        new ParameterizedTypeReference<ApiResponse<NewUserResponseDto>>() {
                        }
                );

        return response.getBody().getData();
    }
}
