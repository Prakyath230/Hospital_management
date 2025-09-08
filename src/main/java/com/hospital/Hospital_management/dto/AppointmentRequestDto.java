package com.hospital.Hospital_management.dto;

import lombok.Data;

import java.time.LocalTime;

@Data
public class AppointmentRequestDto {
    private String patientName;
    private int age;
    private String sex;
    private String department;
    private Long doctorId;
    private String day;
    private LocalTime time;
}
