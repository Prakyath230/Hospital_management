package com.hospital.Hospital_management.service;

import com.hospital.Hospital_management.dto.AppointmentRequestDto;
import com.hospital.Hospital_management.dto.AppointmentResponseDto;
import com.hospital.Hospital_management.dto.ResponseDto;
import com.hospital.Hospital_management.entity.Appointment;
import com.hospital.Hospital_management.entity.Doctor;
import com.hospital.Hospital_management.entity.User;
import com.hospital.Hospital_management.exception.ResourceNotFoundException;
import com.hospital.Hospital_management.repository.AppointmentRepository;
import com.hospital.Hospital_management.repository.DoctorRepository;
import com.hospital.Hospital_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;

    public User getCurrentUser(){
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUserName(username)
                .orElseThrow(()-> new ResourceNotFoundException("User not found"));

    }

    public AppointmentResponseDto mapToDto(Appointment appointment){
        AppointmentResponseDto responseDto=new AppointmentResponseDto();
        responseDto.setAppointmentId(appointment.getId());
        responseDto.setDay(appointment.getDay());
        responseDto.setSex(appointment.getSex());
        responseDto.setDepartment(appointment.getDepartment());
        responseDto.setDoctorName(appointment.getDoctor().getName());
        responseDto.setPatientName(appointment.getPatientName());
        responseDto.setAge(appointment.getAge());
        responseDto.setTime(appointment.getTime());
        responseDto.setAppointmentStatus(appointment.getAppointmentStatus());
        return  responseDto;
    }

    public ResponseDto bookAppointment(AppointmentRequestDto requestDto){
        Appointment appointment = new Appointment();
        appointment.setPatientName(requestDto.getPatientName());
        appointment.setAge(requestDto.getAge());
        appointment.setDay(requestDto.getDay());
        appointment.setTime(requestDto.getTime());
        appointment.setAppointmentStatus("PENDING");
        appointment.setDepartment(requestDto.getDepartment());
        appointment.setSex(requestDto.getSex());

        Doctor doctor=doctorRepository.findById(requestDto.getDoctorId())
                        .orElseThrow(()-> new ResourceNotFoundException("Doctor not found with id "+requestDto.getDoctorId()));

        appointment.setUser(getCurrentUser());

        appointment.setDoctor(doctor);

        Appointment savedAppointment= appointmentRepository.save(appointment);
        return new ResponseDto("Successfully Appointment Registered with id "+savedAppointment.getId());
    }

    public AppointmentResponseDto getAppointmentDetails(){
        User user=getCurrentUser();
        Appointment appointment=appointmentRepository.findByUser_Id(user.getId())
                .orElseThrow(()-> new ResourceNotFoundException("You do not have an appointment"));

        return mapToDto(appointment);
    }

    public ResponseDto approveAppointment(Long id){
        Appointment appointment=appointmentRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Appointment does not exists with id "+id));

        appointment.setAppointmentStatus("APPROVED");

        appointmentRepository.save(appointment);
        return new ResponseDto("Successfully Approved Appointment");
    }

    public ResponseDto rejectAppointment(Long id){
        Appointment appointment=appointmentRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Appointment does not exists with id "+id));

        appointment.setAppointmentStatus("REJECTED");

        appointmentRepository.save(appointment);
        return new ResponseDto("Successfully Rejected Appointment");
    }

    public List<AppointmentResponseDto> getAllAppoints(){
        return appointmentRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

}