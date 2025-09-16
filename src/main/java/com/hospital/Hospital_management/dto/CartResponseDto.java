package com.hospital.Hospital_management.dto;

import lombok.Data;

@Data
public class CartResponseDto {
    private Long cartId;
    private int units;
    private double totalPrice;
    private Long medicineId;
    private String name;
    private String image;
    private double price;
}

