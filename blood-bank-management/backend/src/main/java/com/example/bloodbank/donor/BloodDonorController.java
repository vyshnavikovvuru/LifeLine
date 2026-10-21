package com.example.bloodbank.donor;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api/donors")
public class BloodDonorController {
    private final BloodDonorRepository repository;

    public BloodDonorController(BloodDonorRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<BloodDonor> getDonors(@RequestParam(required = false) String bloodGroup) {
        if (bloodGroup != null && !bloodGroup.isBlank()) {
            return repository.findByBloodGroupIgnoreCase(bloodGroup.trim());
        }
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BloodDonor addDonor(@Valid @RequestBody BloodDonor donor) {
        donor.setName(donor.getName().trim());
        donor.setBloodGroup(donor.getBloodGroup().trim().toUpperCase());
        donor.setPhone(donor.getPhone().trim());
        donor.setCity(donor.getCity().trim());
        return repository.save(donor);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDonor(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Donor not found");
        }
        repository.deleteById(id);
    }
}
