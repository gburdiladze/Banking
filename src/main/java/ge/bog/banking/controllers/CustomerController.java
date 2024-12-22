package ge.bog.banking.controllers;
import ge.bog.banking.models.dto.CustomerDto;
import ge.bog.banking.models.dto.CustomerResponseDto;
import ge.bog.banking.services.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Customer")
public class CustomerController {

    private final CustomerService userService;

    @Autowired
    public CustomerController(CustomerService userService) {
        this.userService = userService;
    }

    @PostMapping("/createUser")
    public ResponseEntity<CustomerResponseDto> createUser(@Valid @RequestBody CustomerDto user) {
        try {
            CustomerResponseDto createdUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser); // HTTP 201 Created
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // HTTP 500 for errors
        }
    }

    @GetMapping("getAllUsers")
    public ResponseEntity<List<CustomerResponseDto>> getAllUsers() {
        try {
            List<CustomerResponseDto> users = userService.getAllUsers();
            return ResponseEntity.ok(users); // HTTP 200 OK
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // HTTP 500 for errors
        }
    }

    @GetMapping("getUserById/{id}")
    public ResponseEntity<CustomerResponseDto> getUserById(@PathVariable int id) {
        try {
            CustomerResponseDto customer = userService.getUserById(id);
            return ResponseEntity.ok(customer); // HTTP 200 OK
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // HTTP 404 Not Found if customer doesn't exist
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // HTTP 500 for other errors
        }
    }

    @PutMapping("updateCustomer/{id}")
    public ResponseEntity<CustomerResponseDto> updateCustomer(@PathVariable int id, @RequestBody CustomerDto updatedCustomer) {
        try {
            CustomerResponseDto updated = userService.updateCustomer(id, updatedCustomer);
            return ResponseEntity.ok(updated); // HTTP 200 OK with the updated customer
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // HTTP 404 Not Found if customer doesn't exist
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // HTTP 500 for other errors
        }
    }

    @DeleteMapping("deleteCustomer/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable int id) {
        try {
            userService.deleteUserById(id); // Attempt to delete the customer
            return ResponseEntity.ok(String.format("Customer with id %d successfully deleted.", id)); // HTTP 200 OK with message
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // HTTP 404 Not Found if customer doesn't exist
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // HTTP 500 for other errors
        }
    }
}