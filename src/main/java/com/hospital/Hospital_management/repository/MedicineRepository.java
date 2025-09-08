package com.hospital.Hospital_management.repository;

import com.hospital.Hospital_management.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {
    Optional<Medicine> findByName(String name);
    List<Medicine> findByExpiryDateBefore(LocalDate date);
    List<Medicine> findByQuantityLessThan(int quantity);
}
