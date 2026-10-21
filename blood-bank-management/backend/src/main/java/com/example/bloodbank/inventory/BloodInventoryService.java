
package com.example.bloodbank.inventory;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@Service
public class BloodInventoryService {

    private final BloodInventoryRepository inventoryRepository;

    private static final Set<String> VALID_BLOOD_GROUPS = Set.of(
            "A+", "A-", "B+", "B-",
            "AB+", "AB-", "O+", "O-"
    );

    public BloodInventoryService(
            BloodInventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public List<BloodInventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    public BloodInventory addStock(String bloodGroup, int units) {

        if (bloodGroup == null ||
                !VALID_BLOOD_GROUPS.contains(
                        bloodGroup.trim().toUpperCase())) {
            throw new IllegalArgumentException("Invalid blood group");
        }

        if (units <= 0) {
            throw new IllegalArgumentException(
                    "Units must be greater than zero");
        }

        String normalizedBloodGroup =
                bloodGroup.trim().toUpperCase();

        BloodInventory inventory = inventoryRepository
                .findByBloodGroupIgnoreCase(normalizedBloodGroup)
                .orElse(new BloodInventory(normalizedBloodGroup, 0));

        inventory.setUnitsAvailable(
                Math.addExact(inventory.getUnitsAvailable(), units));

        return inventoryRepository.save(inventory);
    }

    @Transactional
    public BloodInventory deductStock(String bloodGroup, int units) {

        if (bloodGroup == null ||
                !VALID_BLOOD_GROUPS.contains(
                        bloodGroup.trim().toUpperCase())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid blood group");
        }

        if (units <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Units must be greater than zero");
        }

        String normalizedBloodGroup =
                bloodGroup.trim().toUpperCase();

        BloodInventory inventory = inventoryRepository
                .findByBloodGroupIgnoreCase(normalizedBloodGroup)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "No inventory found for blood group "
                                + normalizedBloodGroup));

        if (inventory.getUnitsAvailable() < units) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Insufficient stock for blood group "
                            + normalizedBloodGroup
                            + ". Available: "
                            + inventory.getUnitsAvailable()
                            + ", requested: " + units);
        }

        inventory.setUnitsAvailable(
                inventory.getUnitsAvailable() - units);

        return inventoryRepository.save(inventory);
    }

    public List<BloodInventory> getLowStockInventory() {
        return inventoryRepository.findAll()
                .stream()
                .filter(inventory ->
                        inventory.getUnitsAvailable() < 5)
                .toList();
    }
}
