package com.healthcare.patient_management.controller;

import com.healthcare.patient_management.dto.ApiResponse;
import com.healthcare.patient_management.dto.NewPatientResponse;
import com.healthcare.patient_management.dto.PatientRequest;
import com.healthcare.patient_management.dto.PatientResponseDto;
import com.healthcare.patient_management.service.implementation.PatientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patient-management")
public class PatientController {

    private final PatientServiceImpl patientService;

    @Autowired
    public PatientController(PatientServiceImpl patientService) {
        this.patientService = patientService;
    }

    @PostMapping("new")
    public ResponseEntity<ApiResponse<NewPatientResponse>> newPatient(@RequestBody PatientRequest newPatient){
        NewPatientResponse patient = patientService.newPatient(newPatient);

        ApiResponse<NewPatientResponse> response = new ApiResponse<>(
                true,
                "Patient has been added successfully",
                patient
        );

        return ResponseEntity
                .status(200)
                .body(response);
    }

    @GetMapping("get_all")
    public ResponseEntity<ApiResponse<?>> getAll(){

        List<PatientResponseDto> patients = patientService.getAllPatients();

        ApiResponse<List<PatientResponseDto>> response = new ApiResponse<>(
                true,
                "",
                patients
        );
        return ResponseEntity.status(200)
                .body(response);
    }

    @GetMapping("verify-existence")
    public ResponseEntity<ApiResponse<?>> verifyPatient(@RequestParam String nationalId){

        boolean patientExists = patientService.verifyPatientExistence(nationalId);
        ApiResponse<Boolean> response = new ApiResponse<>(
                true,
                "",
                patientExists
        );
        return ResponseEntity.status(200)
                .body(response);
    }

    @PostMapping("get-patients")
    public ResponseEntity<ApiResponse<?>> getPatients(@RequestBody List<Integer> ids){
        List<PatientResponseDto> patients = patientService.getPatients(ids);

        ApiResponse<?> response = new ApiResponse<>(
                true,
                "",
                patients
        );

        return ResponseEntity
                .ok().body(response);
    }

    @GetMapping("get")
    public ResponseEntity<ApiResponse<?>> fetchPatient(@RequestParam Integer patientId){
        return null;
    }

    @GetMapping("search")
    public ResponseEntity<ApiResponse<?>> searchPatients(@RequestParam String name){
        return null;
    }
}
