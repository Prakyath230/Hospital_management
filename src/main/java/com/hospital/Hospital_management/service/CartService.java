package com.hospital.Hospital_management.service;

import com.hospital.Hospital_management.dto.CartItemResDto;
import com.hospital.Hospital_management.dto.ResponseDto;
import com.hospital.Hospital_management.entity.Cart;
import com.hospital.Hospital_management.entity.Medicine;
import com.hospital.Hospital_management.entity.User;
import com.hospital.Hospital_management.exception.ResourceNotFoundException;
import com.hospital.Hospital_management.repository.CartRepository;
import com.hospital.Hospital_management.repository.MedicineRepository;
import com.hospital.Hospital_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final UserRepository userRepo;
    private final MedicineRepository medicineRepo;
    private final CartRepository cartRepo;


    public CartItemResDto mapToDto(Cart cart){
        CartItemResDto resDto=new CartItemResDto();
        resDto.setUnits(cart.getUnits());
        resDto.setName(cart.getMedicine().getName());
        resDto.setImageUrl(cart.getMedicine().getImage());
        resDto.setTotalPrice(cart.getTotalPrice());
        resDto.setMedicineId(cart.getMedicine().getId());

        return resDto;


    }

    public User getCurrentUser(){
        String username= SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepo.findByUserName(username)
                .orElseThrow(()-> new ResourceNotFoundException("User not found"));
    }
    public ResponseDto addToCart(Long medicineId, Integer units){
        if(units==null){
            units=1;
        }
        User user=getCurrentUser();

        Medicine medicine=medicineRepo.findById(medicineId)
                .orElseThrow(()-> new ResourceNotFoundException("Medicine Not found"));

        Cart cart= cartRepo.findByUser_IdAndMedicine_Id(user.getId(),medicine.getId())
                .orElse(new Cart(null,user, medicine,units, units*medicine.getPrice()));



        if(cart.getCartId()!=null){
            cart.setUnits(cart.getUnits()+units);
            cart.setTotalPrice(cart.getUnits()*medicine.getPrice());
        }

        Cart savedCart=cartRepo.save(cart);

        return new ResponseDto("Medicine is successfully added");

    }

    public ResponseDto removeFromCart(Long medicineId) {
        User user = getCurrentUser();
        Cart cart = cartRepo.findByUser_IdAndMedicine_Id(user.getId(), medicineId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));
        cartRepo.delete(cart);
        return new ResponseDto("Medicine removed from cart successfully");
    }

    public ResponseDto updateCartQuantity(Long medicineId, Integer units) {
        if (units == null || units <= 0) return removeFromCart(medicineId);
        User user = getCurrentUser();
        Cart cart = cartRepo.findByUser_IdAndMedicine_Id(user.getId(), medicineId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));
        cart.setUnits(units);
        cart.setTotalPrice(units * cart.getMedicine().getPrice());
        cartRepo.save(cart);
        return new ResponseDto("Cart quantity updated successfully");
    }

    public List<CartItemResDto> viewCart() {
        User user = getCurrentUser();

        return cartRepo.findByUser_Id(user.getId())
                .stream()
                .map(this::mapToDto)
                .toList();


    }

    public ResponseDto clearCart() {
        User user = getCurrentUser();
        List<Cart> carts = cartRepo.findByUser_Id(user.getId());
        cartRepo.deleteAll(carts);
        return new ResponseDto("All cart items cleared successfully");
    }
}