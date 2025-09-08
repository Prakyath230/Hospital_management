package com.hospital.Hospital_management.dto;

import lombok.Data;

import java.time.LocalTime;

@Data
public class AppointmentResponseDto {
    private Long appointmentId;
    private String patientName;
    private int age;
    private String sex;
    private String department;
    private String day;
    private LocalTime time;
    private String appointmentStatus;
    private String doctorName;


}
