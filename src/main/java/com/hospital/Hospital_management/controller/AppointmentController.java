package com.hospital.Hospital_management.controller;

import com.hospital.Hospital_management.dto.AppointmentRequestDto;
import com.hospital.Hospital_management.dto.AppointmentResponseDto;
import com.hospital.Hospital_management.dto.ResponseDto;
import com.hospital.Hospital_management.entity.Appointment;
import com.hospital.Hospital_management.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<ResponseDto> bookAppointmnet(@RequestBody AppointmentRequestDto requestDto) {
        return new ResponseEntity<>(appointmentService.bookAppointment(requestDto), HttpStatus.CREATED);
    }

    @GetMapping("/details")
    public ResponseEntity<AppointmentResponseDto> getMyAppoint(){
        return ResponseEntity.ok(appointmentService.getAppointmentDetails());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/approve/{id}")
    public ResponseEntity<ResponseDto> approveAppointemr(@PathVariable Long id){
        return ResponseEntity.ok(appointmentService.approveAppointment(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/reject/{id}")
    public ResponseEntity<ResponseDto> rejectAppointemr(@PathVariable Long id){
        return ResponseEntity.ok(appointmentService.rejectAppointment(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<AppointmentResponseDto>> getAllAppointments(){
        return ResponseEntity.ok(appointmentService.getAllAppoints());
    }
}