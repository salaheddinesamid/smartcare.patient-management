package com.healthcare.patient_management.controller;

import com.healthcare.patient_management.dto.ApiResponse;
import com.healthcare.patient_management.dto.NewPatientResponse;
import com.healthcare.patient_management.dto.PatientRequest;
import com.healthcare.patient_management.service.implementation.PatientServiceImpl;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
