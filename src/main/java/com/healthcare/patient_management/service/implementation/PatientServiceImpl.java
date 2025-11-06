package com.healthcare.patient_management.service.implementation;

import com.healthcare.patient_management.dto.*;
import com.healthcare.patient_management.exception.PatientAlreadyExistsException;
import com.healthcare.patient_management.model.Patient;
import com.healthcare.patient_management.model.PatientStatus;
import com.healthcare.patient_management.repository.PatientRepository;
import com.healthcare.patient_management.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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

    @Override
    public Boolean verifyPatientExistence(String nationalId) {
        return
                patientRepository.existsByNationalId(nationalId);
    }

    @Override
    public List<PatientResponseDto> getAllPatients() {

        // Fetch the patients from the db:
        List<Patient> patients = patientRepository.findAll();

        // Get user ids:
        List<Integer> ids = getUserIds(patients);

        // Get users:
        List<UserDto> users = getUsers(ids);

        Map<Integer, UserDto> usersMap =
                users.stream().collect(Collectors.toMap(UserDto::getUserId, u-> u));

        return
                patients.stream()
                        .map(patient -> {
                            UserDto user = usersMap.get(patient.getUserId());

                            return new PatientResponseDto(patient,user);
                        }).toList();
    }

    @Override
    public List<PatientResponseDto> getPatients(List<Integer> ids) {
        List<PatientResponseDto> patients = getAllPatients();
        return
                patients.stream()
                        .filter(patientResponseDto -> ids.contains(patientResponseDto.getPatientId()))
                        .toList();
    }

    @Override
    public PatientResponseDto getPatient(Integer id) {
        return null;
    }

    private List<UserDto> getUsers(List<Integer> ids){
        String uri = USER_MANAGEMENT_URI + "/api/user/get-users";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<List<Integer>> entity = new HttpEntity<>(ids,headers);

        ResponseEntity<ApiResponse<List<UserDto>>> response =
                restTemplate.exchange(
                        uri,
                        HttpMethod.POST,
                        entity,
                        new ParameterizedTypeReference<>() {}
                );

        return response.getBody().getData();
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
                        new ParameterizedTypeReference<>() {}
                );

        return response.getBody().getData();
    }


    @Override
    public List<PatientSearchResponseDto> searchPatients(String nationalId) {
        List<Patient> patients =
                patientRepository.findAllByNationalId(nationalId);

        List<Integer> userIds = getUserIds(patients);

        List<UserDto> users = getUsers(userIds);

        // Map for quick look up:
        Map<Integer,UserDto> usersMap = users.stream()
                .collect(Collectors.toMap(UserDto::getUserId, u-> u));


        return patients.stream()
                .map(patient -> {
                    UserDto userDto = usersMap.get(patient.getUserId());
                    return new PatientSearchResponseDto(
                            patient,
                            userDto
                    );
                }).toList();
    }

    private List<Integer> getUserIds(List<Patient> patients){
        return patients.stream()
                .map(Patient::getUserId).toList();
    }
}
