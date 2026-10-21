
package com.example.bloodbank;

import com.example.bloodbank.inventory.BloodInventory;
import com.example.bloodbank.inventory.BloodInventoryRepository;
import com.example.bloodbank.donor.BloodDonor;
import com.example.bloodbank.donor.BloodDonorRepository;
import com.example.bloodbank.request.BloodRequest;
import com.example.bloodbank.request.BloodRequestRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class BloodBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(BloodBankApplication.class, args);
    }

    @Bean
    CommandLineRunner seedDemoData(
            BloodInventoryRepository inventoryRepository,
            BloodDonorRepository donorRepository,
            BloodRequestRepository requestRepository) {

        return args -> {

            List<BloodInventory> inventory = List.of(
                    new BloodInventory("A+", 50),
                    new BloodInventory("A-", 25),
                    new BloodInventory("B+", 45),
                    new BloodInventory("B-", 20),
                    new BloodInventory("AB+", 30),
                    new BloodInventory("AB-", 15),
                    new BloodInventory("O+", 60),
                    new BloodInventory("O-", 25)
            );

            for (BloodInventory item : inventory) {
                if (inventoryRepository
                        .findByBloodGroupIgnoreCase(
                                item.getBloodGroup())
                        .isEmpty()) {
                    inventoryRepository.save(item);
                }
            }

            // 2. Seed 30 demo donors
            List<BloodDonor> donors = List.of(
                    new BloodDonor("Arjun Kumar", "O+", "9000000001", "Visakhapatnam", 25),
                    new BloodDonor("Priya Sharma", "A+", "9000000002", "Visakhapatnam", 24),
                    new BloodDonor("Rahul Reddy", "B+", "9000000003", "Hyderabad", 28),
                    new BloodDonor("Sneha Rao", "AB+", "9000000004", "Vijayawada", 23),
                    new BloodDonor("Kiran Kumar", "O-", "9000000005", "Hyderabad", 30),
                    new BloodDonor("Ananya Das", "A-", "9000000006", "Rajahmundry", 26),
                    new BloodDonor("Vamsi Krishna", "B-", "9000000007", "Visakhapatnam", 27),
                    new BloodDonor("Divya Reddy", "O+", "9000000008", "Guntur", 22),
                    new BloodDonor("Sai Teja", "A+", "9000000009", "Hyderabad", 29),
                    new BloodDonor("Meghana Rao", "AB-", "9000000010", "Vijayawada", 25),
                    new BloodDonor("Rohit Varma", "B+", "9000000011", "Kakinada", 31),
                    new BloodDonor("Pooja Singh", "O+", "9000000012", "Hyderabad", 24),
                    new BloodDonor("Nikhil Raju", "A-", "9000000013", "Visakhapatnam", 28),
                    new BloodDonor("Harika Devi", "B+", "9000000014", "Tirupati", 23),
                    new BloodDonor("Manoj Kumar", "O-", "9000000015", "Vijayawada", 32),
                    new BloodDonor("Keerthi Reddy", "AB+", "9000000016", "Hyderabad", 26),
                    new BloodDonor("Abhinav Rao", "A+", "9000000017", "Guntur", 27),
                    new BloodDonor("Lakshmi Priya", "B-", "9000000018", "Visakhapatnam", 24),
                    new BloodDonor("Charan Teja", "O+", "9000000019", "Rajahmundry", 29),
                    new BloodDonor("Sravani Kumar", "A+", "9000000020", "Hyderabad", 25),
                    new BloodDonor("Aditya Varma", "AB+", "9000000021", "Kurnool", 30),
                    new BloodDonor("Bhavana Rao", "B+", "9000000022", "Visakhapatnam", 22),
                    new BloodDonor("Praveen Reddy", "O-", "9000000023", "Hyderabad", 33),
                    new BloodDonor("Nandini Das", "A-", "9000000024", "Vijayawada", 26),
                    new BloodDonor("Sandeep Kumar", "O+", "9000000025", "Tirupati", 28),
                    new BloodDonor("Kavya Sri", "B-", "9000000026", "Visakhapatnam", 24),
                    new BloodDonor("Tarun Raju", "A+", "9000000027", "Guntur", 31),
                    new BloodDonor("Mounika Devi", "AB-", "9000000028", "Hyderabad", 23),
                    new BloodDonor("Akash Sharma", "B+", "9000000029", "Kakinada", 27),
                    new BloodDonor("Sowmya Reddy", "O+", "9000000030", "Visakhapatnam", 25)
            );

            for (BloodDonor donor : donors) {
                if (!donorRepository.existsByPhone(donor.getPhone())) {
                    donorRepository.save(donor);
                }
            }

            String[][] requestData = {

                    {"Ravi Kumar", "O+", "City Care Hospital", "9100000001", "2", "PENDING"},
                    {"Sita Rao", "A+", "Sunrise Hospital", "9100000002", "1", "APPROVED"},
                    {"Mohan Reddy", "B+", "Life Care Hospital", "9100000003", "3", "PENDING"},
                    {"Anil Kumar", "AB+", "City Care Hospital", "9100000004", "1", "FULFILLED"},
                    {"Geetha Devi", "O-", "Sunrise Hospital", "9100000005", "2", "PENDING"},
                    {"Ramesh Rao", "A-", "Green Valley Hospital", "9100000006", "1", "REJECTED"},
                    {"Pavan Kumar", "B-", "Life Care Hospital", "9100000007", "2", "PENDING"},
                    {"Sujatha Reddy", "O+", "City Care Hospital", "9100000008", "1", "APPROVED"},
                    {"Karthik Varma", "A+", "Sunrise Hospital", "9100000009", "2", "PENDING"},
                    {"Deepa Sharma", "AB-", "Green Valley Hospital", "9100000010", "1", "PENDING"},
                    {"Venkat Rao", "B+", "Life Care Hospital", "9100000011", "2", "FULFILLED"},
                    {"Radha Krishna", "O+", "City Care Hospital", "9100000012", "1", "PENDING"},
                    {"Naveen Kumar", "A-", "Sunrise Hospital", "9100000013", "3", "APPROVED"},
                    {"Swathi Reddy", "B+", "Green Valley Hospital", "9100000014", "1", "PENDING"},
                    {"Gopal Rao", "O-", "Life Care Hospital", "9100000015", "2", "REJECTED"},
                    {"Tejaswi Devi", "AB+", "City Care Hospital", "9100000016", "1", "PENDING"},
                    {"Srinivas Kumar", "A+", "Sunrise Hospital", "9100000017", "2", "PENDING"},
                    {"Anusha Rao", "B-", "Green Valley Hospital", "9100000018", "1", "APPROVED"},
                    {"Mahesh Reddy", "O+", "Life Care Hospital", "9100000019", "2", "PENDING"},
                    {"Bindu Sharma", "A-", "City Care Hospital", "9100000020", "1", "FULFILLED"},
                    {"Raghu Varma", "B+", "Sunrise Hospital", "9100000021", "2", "PENDING"},
                    {"Sandhya Rao", "AB-", "Green Valley Hospital", "9100000022", "1", "PENDING"},
                    {"Chaitanya Kumar", "O+", "Life Care Hospital", "9100000023", "3", "APPROVED"},
                    {"Lavanya Reddy", "A+", "City Care Hospital", "9100000024", "1", "PENDING"},
                    {"Harsha Vardhan", "B-", "Sunrise Hospital", "9100000025", "2", "REJECTED"},
                    {"Padma Devi", "O-", "Green Valley Hospital", "9100000026", "1", "PENDING"},
                    {"Teja Krishna", "AB+", "Life Care Hospital", "9100000027", "2", "PENDING"},
                    {"Akhil Kumar", "O+", "Apollo Hospitals", "9200000001", "2", "PENDING"},
                    {"Priyanka Rao", "A+", "KIMS Hospital", "9200000002", "1", "APPROVED"},
                    {"Suresh Reddy", "B+", "Care Hospital", "9200000003", "3", "PENDING"},
                    {"Nisha Sharma", "AB+", "Medicover Hospital", "9200000004", "2", "FULFILLED"},
                    {"Ravi Teja", "O-", "Apollo Hospitals", "9200000005", "1", "PENDING"},
                    {"Swathi Kumar", "A-", "KIMS Hospital", "9200000006", "2", "REJECTED"},
                    {"Varun Krishna", "B-", "Care Hospital", "9200000007", "1", "PENDING"},
                    {"Harini Devi", "O+", "Medicover Hospital", "9200000008", "2", "APPROVED"},
                    {"Pavan Rao", "A+", "Apollo Hospitals", "9200000009", "1", "PENDING"},
                    {"Meera Reddy", "AB-", "KIMS Hospital", "9200000010", "2", "PENDING"},
                    {"Karthik Kumar", "B+", "Care Hospital", "9200000011", "1", "FULFILLED"},
                    {"Divya Sharma", "O+", "Medicover Hospital", "9200000012", "3", "PENDING"},
                    {"Naveen Rao", "A-", "Apollo Hospitals", "9200000013", "2", "APPROVED"},
                    {"Pallavi Reddy", "B+", "KIMS Hospital", "9200000014", "1", "PENDING"},
                    {"Manish Kumar", "O-", "Care Hospital", "9200000015", "2", "REJECTED"},
                    {"Sravya Devi", "AB+", "Medicover Hospital", "9200000016", "1", "PENDING"},
                    {"Tejas Rao", "A+", "Apollo Hospitals", "9200000017", "2", "PENDING"},
                    {"Anil Krishna", "B-", "KIMS Hospital", "9200000018", "1", "APPROVED"},
                    {"Kavitha Reddy", "O+", "Care Hospital", "9200000019", "2", "PENDING"},
                    {"Rahul Sharma", "A-", "Medicover Hospital", "9200000020", "1", "FULFILLED"}

            };

            // Insert each demo request only if its phone is absent.
            // See the repository note below.
            for (String[] data : requestData) {
                boolean exists = requestRepository.findAll().stream()
                        .anyMatch(request ->
                                request.getContactPhone().equals(data[3]));

                if (!exists) {
                    BloodRequest request = new BloodRequest();
                    request.setPatientName(data[0]);
                    request.setBloodGroup(data[1]);
                    request.setHospital(data[2]);
                    request.setContactPhone(data[3]);
                    request.setUnits(Integer.parseInt(data[4]));
                    request.setStatus(data[5]);

                    requestRepository.save(request);
                }
            }
        };
    }
}
