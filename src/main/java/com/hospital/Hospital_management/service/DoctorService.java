package com.hospital.Hospital_management.service;

import com.hospital.Hospital_management.dto.DoctorDetailDto;
import com.hospital.Hospital_management.entity.Doctor;
import com.hospital.Hospital_management.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public DoctorDetailDto getDoctorById(Long id) {
        Optional<Doctor> opt = doctorRepository.findById(id);
        if (opt.isEmpty()) {
            return null;
        }
        Doctor d = opt.get();
        return new DoctorDetailDto(
                d.getId(),
                d.getName(),
                d.getSpecialty(),
                d.getExperience(),
                d.getEmail(),
                d.getImage()
        );
    }

    public List<DoctorDetailDto> getAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(d -> new DoctorDetailDto(
                        d.getId(),
                        d.getName(),
                        d.getSpecialty(),
                        d.getExperience(),
                        d.getEmail(),
                        d.getImage()
                ))
                .collect(Collectors.toList());
    }

    public Doctor saveDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }
}
