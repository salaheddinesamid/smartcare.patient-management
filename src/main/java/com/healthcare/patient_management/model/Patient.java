package com.healthcare.patient_management.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer patientId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "national_id")
    private String nationalId;

    @Column(name = "age", columnDefinition = "integer default null")
    private Integer age;

    @Column(name = "address")
    private String address;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PatientStatus status;
}

