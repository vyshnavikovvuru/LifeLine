package com.example.bloodbank.donor;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "blood_donors")
public class BloodDonor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Column(nullable = false)
    private String bloodGroup;

    @NotBlank
    @Column(nullable = false)
    private String phone;

    @NotBlank
    @Column(nullable = false)
    private String city;

    private int age;

    public BloodDonor() {}

    public BloodDonor(String name, String bloodGroup, String phone, String city, int age) {
        this.name = name;
        this.bloodGroup = bloodGroup;
        this.phone = phone;
        this.city = city;
        this.age = age;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
}
