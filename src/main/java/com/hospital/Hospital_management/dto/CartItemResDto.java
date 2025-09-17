package com.hospital.Hospital_management.dto;

import lombok.Data;

@Data
public class CartItemResDto {
    private Long medicineId;
    private String name;
    private String imageUrl;
    private double totalPrice;
    private int units;

}
