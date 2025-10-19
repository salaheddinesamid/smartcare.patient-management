package com.healthcare.patient_management.exception;

import com.healthcare.patient_management.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PatientExceptionController {

    @ExceptionHandler(PatientAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<?>> handlePatientExists(PatientAlreadyExistsException exception){
        ApiResponse<?> response  = new ApiResponse<>(
                false,
                exception.getMessage(),
                null
        );

        return ResponseEntity
                .status(409)
                .body(response);
    }
}
