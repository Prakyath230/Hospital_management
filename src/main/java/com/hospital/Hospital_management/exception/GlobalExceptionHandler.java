package com.hospital.Hospital_management.exception;

import com.hospital.Hospital_management.dto.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public ResponseEntity<ErrorResponseDto> buildError(Exception ex, HttpStatus status, String path){
        ErrorResponseDto error=new ErrorResponseDto(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                path
        );

        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFound(Exception ex, HttpServletRequest request){
        return buildError(ex,HttpStatus.NOT_FOUND,request.getRequestURI());
    }

    @ExceptionHandler(ResourceAlreadyExists.class)
    public ResponseEntity<ErrorResponseDto> handleResourceAlreadyExistsEx(Exception ex, HttpServletRequest request){
        return buildError(ex, HttpStatus.BAD_REQUEST, request.getRequestURI());
    }

    @ExceptionHandler(InvalidCredentialsExpection.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidCredentials(Exception ex, HttpServletRequest request){
        return buildError(ex, HttpStatus.UNAUTHORIZED, request.getRequestURI());
    }




}
