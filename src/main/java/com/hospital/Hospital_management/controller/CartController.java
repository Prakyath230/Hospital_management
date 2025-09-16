package com.hospital.Hospital_management.controller;

import com.hospital.Hospital_management.dto.CartResponseDto;
import com.hospital.Hospital_management.dto.ResponseDto;
import com.hospital.Hospital_management.entity.Cart;
import com.hospital.Hospital_management.service.CartService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PreAuthorize("hasRole('PATIENT')")
    @PostMapping("/medicine/{medicineId}")
    public ResponseEntity<ResponseDto> addToCart(@PathVariable Long medicineId, @RequestParam(required = false) Integer units){
        return ResponseEntity.ok(cartService.addToCart(medicineId, units));
    }

    @PreAuthorize("hasRole('PATIENT')")
    @DeleteMapping("/medicine/{medicineId}")
    public ResponseEntity<ResponseDto> removeFromCart(@PathVariable Long medicineId) {
        return ResponseEntity.ok(cartService.removeFromCart(medicineId));
    }

    @PreAuthorize("hasRole('PATIENT')")
    @PutMapping("/medicine/{medicineId}")
    public ResponseEntity<ResponseDto> updateCartQuantity(@PathVariable Long medicineId,
                                                          @RequestParam(required = false) Integer units) {
        return ResponseEntity.ok(cartService.updateCartQuantity(medicineId, units));
    }
    @GetMapping
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<List<CartResponseDto>> viewCart() {
        List<Cart> cartItems = cartService.viewCart();
        List<CartResponseDto> response = cartItems.stream().map(c -> {
            CartResponseDto dto = new CartResponseDto();
            dto.setCartId(c.getCartId());
            dto.setUnits(c.getUnits());
            dto.setTotalPrice(c.getTotalPrice());
            dto.setMedicineId(c.getMedicine().getId());
            dto.setName(c.getMedicine().getName());
            dto.setPrice(c.getMedicine().getPrice());
            dto.setImage(c.getMedicine().getImage());
            return dto;
        }).toList();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('PATIENT')")
    @DeleteMapping
    public ResponseEntity<ResponseDto> clearCart() {
        return ResponseEntity.ok(cartService.clearCart());
    }
}