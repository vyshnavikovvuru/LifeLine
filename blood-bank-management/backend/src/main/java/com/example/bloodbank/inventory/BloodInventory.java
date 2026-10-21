
package com.example.bloodbank.inventory;

import jakarta.persistence.*;

@Entity
@Table(name = "blood_inventory")
public class BloodInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String bloodGroup;

    @Column(nullable = false)
    private int unitsAvailable;

    public BloodInventory() {
    }

    public BloodInventory(String bloodGroup, int unitsAvailable) {
        this.bloodGroup = bloodGroup;
        this.unitsAvailable = unitsAvailable;
    }

    public Long getId() {
        return id;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public int getUnitsAvailable() {
        return unitsAvailable;
    }

    public void setUnitsAvailable(int unitsAvailable) {
        this.unitsAvailable = unitsAvailable;
    }
}