
package com.example.bloodbank.request;

import com.example.bloodbank.inventory.BloodInventoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/requests")
public class BloodRequestController {

    private final BloodRequestRepository repository;
    private final BloodInventoryService inventoryService;

    public BloodRequestController(
            BloodRequestRepository repository,
            BloodInventoryService inventoryService) {
        this.repository = repository;
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public List<BloodRequest> getRequests() {
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BloodRequest createRequest(
            @Valid @RequestBody BloodRequest request) {

        request.setPatientName(request.getPatientName().trim());
        request.setBloodGroup(
                request.getBloodGroup().trim().toUpperCase());
        request.setHospital(request.getHospital().trim());
        request.setContactPhone(request.getContactPhone().trim());
        request.setStatus("PENDING");

        return repository.save(request);
    }

    @PatchMapping("/{id}/status")
    public BloodRequest updateRequestStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {

        BloodRequest request = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Blood request not found"));

        if (!"PENDING".equals(request.getStatus())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Only pending requests can be approved or rejected");
        }

        String status = body.get("status");

        if (status == null ||
                (!status.equalsIgnoreCase("APPROVED")
                        && !status.equalsIgnoreCase("REJECTED"))) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Status must be APPROVED or REJECTED");
        }

        request.setStatus(status.toUpperCase());

        return repository.save(request);
    }

    @PatchMapping("/{id}/fulfill")
    @Transactional
    public BloodRequest fulfillRequest(@PathVariable Long id) {

        BloodRequest request = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Blood request not found"));

        if (!"APPROVED".equals(request.getStatus())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Only approved requests can be fulfilled");
        }

        // Deduct stock only when fulfillment is recorded.
        inventoryService.deductStock(
                request.getBloodGroup(),
                request.getUnits());

        // Save the new status in the same transaction.
        request.setStatus("FULFILLED");

        return repository.save(request);
    }
}
