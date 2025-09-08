package com.hospital.Hospital_management.repository;

import com.hospital.Hospital_management.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser_IdAndMedicine_Id(Long userId, Long medicineId);
    List<Cart> findByUser_Id(Long id);
}