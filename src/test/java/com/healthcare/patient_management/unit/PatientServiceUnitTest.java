package com.healthcare.patient_management.unit;

import com.healthcare.patient_management.dto.PatientRequest;
import com.healthcare.patient_management.repository.PatientRepository;
import com.healthcare.patient_management.service.implementation.PatientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.when;

public class PatientServiceUnitTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientServiceImpl patientService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void createPatientSuccessfully(){

        // Mock the request dto:
        PatientRequest REQUEST = new PatientRequest(
                "Salaheddine",
                "Samid",
                "samid@gmal.com",
                "PATIENT",
                "TTTTRRREEE",
                "",
                "samid2025"
        );

        // Arrange:
        when(patientRepository.findAllByNationalId("TTTTRRREEE")).thenReturn(null);
    }

    @Test
    void createPatientShouldThrow(){}

    @Test
    void verifyPatientExistenceSuccessfully(){}

    @Test
    void verifyPatientExistenceShouldThrow(){}

    @Test
    void searchPatientSuccessfully(){}
}
