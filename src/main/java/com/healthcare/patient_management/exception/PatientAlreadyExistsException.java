package com.healthcare.patient_management.exception;

public class PatientAlreadyExistsException extends RuntimeException{

    public PatientAlreadyExistsException(String nationalId) {
        super("This patient already exists with national id:" + nationalId);
    }
}
