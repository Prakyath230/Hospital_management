package com.hospital.Hospital_management.service;

import com.hospital.Hospital_management.dto.MedicineRequestDto;
import com.hospital.Hospital_management.dto.MedicineResponseDto;
import com.hospital.Hospital_management.dto.ResponseDto;
import com.hospital.Hospital_management.entity.Medicine;
import com.hospital.Hospital_management.exception.ResourceNotFoundException;
import com.hospital.Hospital_management.repository.MedicineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicineService {
    private final MedicineRepository medicineRepository;

    public MedicineResponseDto mapToDto(Medicine medicine) {
        MedicineResponseDto dto = new MedicineResponseDto();
        dto.setMedicineId(medicine.getId());
        dto.setName(medicine.getName());
        dto.setImage(medicine.getImage());
        dto.setDescription(medicine.getDescription());
        dto.setPrice(medicine.getPrice());
        dto.setQuantity(medicine.getQuantity());
        dto.setExpiryDate(medicine.getExpiryDate());
        return dto;
    }

    public ResponseDto addMedicine(MedicineRequestDto requestDto) {
        Medicine medicine = new Medicine();
        medicine.setName(requestDto.getName());
        medicine.setImage(requestDto.getImage());
        medicine.setDescription(requestDto.getDescription());
        medicine.setPrice(requestDto.getPrice());
        medicine.setQuantity(requestDto.getQuantity());
        medicine.setExpiryDate(requestDto.getExpiryDate());

        Medicine saved = medicineRepository.save(medicine);
        return new ResponseDto("Successfully added Medicine with id " + saved.getId());
    }

    public ResponseDto updateMedicine(Long id, MedicineRequestDto requestDto) {
        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medicine not found with id " + id));

        medicine.setName(requestDto.getName());
        medicine.setImage(requestDto.getImage());
        medicine.setDescription(requestDto.getDescription());
        medicine.setPrice(requestDto.getPrice());
        medicine.setQuantity(requestDto.getQuantity());
        medicine.setExpiryDate(requestDto.getExpiryDate());

        medicineRepository.save(medicine);
        return new ResponseDto("Successfully updated Medicine with id " + id);
    }

    public ResponseDto deleteMedicine(Long id) {
        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medicine not found with id " + id));

        medicineRepository.delete(medicine);
        return new ResponseDto("Successfully deleted Medicine with id " + id);
    }

    public MedicineResponseDto getMedicineById(Long id) {
        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medicine not found with id " + id));
        return mapToDto(medicine);
    }

    public List<MedicineResponseDto> getAllMedicines() {
        return medicineRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public List<MedicineResponseDto> getLowStockOrExpiredMedicines() {
        LocalDate today = LocalDate.now();
        int lowStock = 10;

        List<Medicine> medicines = medicineRepository.findAll();
        List<MedicineResponseDto> result = new ArrayList<>();

        for (Medicine m : medicines) {
            if (m.getQuantity() <= lowStock || m.getExpiryDate().isBefore(today)) {
                result.add(mapToDto(m));
            }
        }

        return result;
    }

}