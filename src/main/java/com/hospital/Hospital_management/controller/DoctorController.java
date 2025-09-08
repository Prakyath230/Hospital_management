package com.hospital.Hospital_management.controller;

import com.hospital.Hospital_management.dto.DoctorDetailDto;
import com.hospital.Hospital_management.entity.Doctor;
import com.hospital.Hospital_management.service.DoctorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@CrossOrigin(origins = "*")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    // Get all doctors
    @GetMapping
    public ResponseEntity<List<DoctorDetailDto>> listDoctors() {
        List<DoctorDetailDto> dtos = doctorService.getAllDoctors();
        return ResponseEntity.ok(dtos);
    }

    // Get doctor by id
    @GetMapping("/{id}")
    public ResponseEntity<DoctorDetailDto> getDoctor(@PathVariable Long id) {
        DoctorDetailDto dto = doctorService.getDoctorById(id);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    // Add new doctor
    @PostMapping
    public ResponseEntity<DoctorDetailDto> addDoctor(@RequestBody Doctor doctor) {
        Doctor saved = doctorService.saveDoctor(doctor);
        DoctorDetailDto dto = new DoctorDetailDto(
                saved.getId(),
                saved.getName(),
                saved.getSpecialty(),
                saved.getExperience(),
                saved.getEmail(),
                saved.getImage()
        );
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    // Update existing doctor
    @PutMapping("/{id}")
    public ResponseEntity<DoctorDetailDto> updateDoctor(@PathVariable Long id, @RequestBody Doctor doctor) {
        DoctorDetailDto existing = doctorService.getDoctorById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        doctor.setId(id);
        Doctor updated = doctorService.saveDoctor(doctor);
        DoctorDetailDto dto = new DoctorDetailDto(
                updated.getId(),
                updated.getName(),
                updated.getSpecialty(),
                updated.getExperience(),
                updated.getEmail(),
                updated.getImage()
        );
        return ResponseEntity.ok(dto);
    }

    // Delete doctor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        DoctorDetailDto existing = doctorService.getDoctorById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }
}
