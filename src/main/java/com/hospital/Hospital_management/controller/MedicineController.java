package com.hospital.Hospital_management.controller;

import com.hospital.Hospital_management.dto.MedicineRequestDto;
import com.hospital.Hospital_management.dto.MedicineResponseDto;
import com.hospital.Hospital_management.dto.ResponseDto;
import com.hospital.Hospital_management.service.MedicineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/medicines")
public class MedicineController {
    private final MedicineService medicineService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ResponseDto> addMedicine(@RequestBody MedicineRequestDto requestDto) {
        return new ResponseEntity<>(medicineService.addMedicine(requestDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicineResponseDto> getMedicineById(@PathVariable Long id) {
        return ResponseEntity.ok(medicineService.getMedicineById(id));
    }

    @GetMapping
    public ResponseEntity<List<MedicineResponseDto>> getAllMedicines() {
        return ResponseEntity.ok(medicineService.getAllMedicines());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto> updateMedicine(@PathVariable Long id, @RequestBody MedicineRequestDto requestDto) {
        return ResponseEntity.ok(medicineService.updateMedicine(id, requestDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteMedicine(@PathVariable Long id) {
        return ResponseEntity.ok(medicineService.deleteMedicine(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/check-stock-expiry")
    public ResponseEntity<List<MedicineResponseDto>> checkStockAndExpiry() {
        return ResponseEntity.ok(medicineService.getLowStockOrExpiredMedicines());
    }
}
