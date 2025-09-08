package com.hospital.Hospital_management.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MedicineRequestDto {
    private String name;
    private String image;
    private String description;
    private double price;
    private int quantity;
    private LocalDate expiryDate;
}
