
package com.example.bloodbank.inventory;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class BloodInventoryController {

    private final BloodInventoryService inventoryService;

    public BloodInventoryController(
            BloodInventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public List<BloodInventory> getAllInventory() {
        return inventoryService.getAllInventory();
    }

    @PostMapping("/stock")
    public ResponseEntity<?> addStock(
            @RequestBody StockUpdateRequest request) {
        try {
            BloodInventory inventory = inventoryService.addStock(
                    request.bloodGroup(),
                    request.units()
            );

            return ResponseEntity.ok(inventory);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ArithmeticException e) {
            return ResponseEntity.badRequest()
                    .body("The requested stock exceeds the supported limit");
        }
    }

    public record StockUpdateRequest(String bloodGroup, int units) {
    }

    @GetMapping("/low-stock")
    public List<BloodInventory> getLowStockInventory() {
        return inventoryService.getLowStockInventory();
    }

}
