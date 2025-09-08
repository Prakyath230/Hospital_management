package com.hospital.Hospital_management.dto;

public class DoctorDetailDto {
    private Long id;
    private String name;
    private String specialty;
    private int experience;
    private String email;
    private String image;

    public DoctorDetailDto(Long id, String name, String specialty, int experience, String email, String image) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
        this.experience = experience;
        this.email = email;
        this.image = image;
    }

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getSpecialty() { return specialty; }
    public int getExperience() { return experience; }
    public String getEmail() { return email; }
    public String getImage() { return image; }
}
