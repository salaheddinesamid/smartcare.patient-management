package com.healthcare.patient_management.service.implementation;

import com.healthcare.patient_management.dto.PatientResponseDto;
import com.healthcare.patient_management.dto.PatientSearchResponseDto;
import com.healthcare.patient_management.dto.UserDto;
import com.healthcare.patient_management.model.Patient;
import com.healthcare.patient_management.repository.PatientRepository;
import com.healthcare.patient_management.service.PatientQueryService;
import com.healthcare.patient_management.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PatientQueryServiceImpl implements PatientQueryService {

    private final PatientRepository patientRepository;
    private final UserUtils userUtils;

    @Autowired
    public PatientQueryServiceImpl(PatientRepository patientRepository, UserUtils userUtils) {
        this.patientRepository = patientRepository;
        this.userUtils = userUtils;
    }

    @Override
    public List<PatientResponseDto> getAllPatients() {
        // Fetch the patients from the db:
        List<Patient> patients = patientRepository.findAll();

        // Get user ids:
        List<Integer> ids = userUtils.getUserIds(patients);

        // Get users:
        List<UserDto> users = userUtils.getUsers(ids);

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

    @Override
    public List<PatientSearchResponseDto> searchPatients(String nationalId) {
        List<Patient> patients =
                patientRepository.findAllByNationalId(nationalId);

        List<Integer> userIds = userUtils.getUserIds(patients);

        // fetch the users from the service
        List<UserDto> users = userUtils.getUsers(userIds);

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
}
