
package com.example.bloodbank.donor;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BloodDonorRepository
        extends JpaRepository<BloodDonor, Long> {

    List<BloodDonor> findByBloodGroupIgnoreCase(String bloodGroup);

    boolean existsByPhone(String phone);
}
