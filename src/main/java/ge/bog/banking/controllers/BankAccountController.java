package ge.bog.banking.controllers;
import ge.bog.banking.models.dto.BankAccountDto;
import ge.bog.banking.models.dto.BankAccountResponseDto;
import ge.bog.banking.models.dto.UpdateBankAccountDto;
import ge.bog.banking.services.BankAccountService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/BankAccount")
public class BankAccountController {

    private final BankAccountService bankAccountService;

    @Autowired
    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @PostMapping("/createBankAcount")
    public ResponseEntity<BankAccountResponseDto> createBankAcount(@Valid @RequestBody BankAccountDto account) {
        try {

            BankAccountResponseDto  responseDto = bankAccountService.createccount(account);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto); // HTTP 201 Created
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // HTTP 500 for errors
        }
    }

    @GetMapping("getAccountById/{id}")
    public ResponseEntity<BankAccountResponseDto> getUserById(@PathVariable int id) {
        try {
            BankAccountResponseDto responseDto = bankAccountService.getAccountById(id);
            return ResponseEntity.ok(responseDto); // HTTP 200 OK
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // HTTP 404 Not Found if customer doesn't exist
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // HTTP 500 for other errors
        }
    }

    @PutMapping("updateCustomer/{id}")
    public ResponseEntity<BankAccountResponseDto> updateCustomer(@PathVariable int id, @RequestBody UpdateBankAccountDto update) {
        try {
            BankAccountResponseDto updated = bankAccountService.updateBankAccount(id, update);
            return ResponseEntity.ok(updated); // HTTP 200 OK with the updated customer
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // HTTP 404 Not Found if customer doesn't exist
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // HTTP 500 for other errors
        }
    }

    @PutMapping("blockAccount/{id}")
    public ResponseEntity<BankAccountResponseDto> blockAccount(@PathVariable int id) {
        try {
            BankAccountResponseDto response = bankAccountService.blockAccount(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .build(); // Return error message
        }
    }

    @PutMapping("blockByBankAccount/{id}")
    public ResponseEntity<BankAccountResponseDto> blockByBankAccount(@PathVariable int id) {
        try {
            BankAccountResponseDto response = bankAccountService.blockByBankAccount(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .build(); // Return error message
        }
    }


    @PutMapping("unblockAccount{id}")
    public ResponseEntity<BankAccountResponseDto> unblockAccount(@PathVariable int id) {
        try {
            BankAccountResponseDto response = bankAccountService.unblockAccount(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .build(); // Return error message
        }
    }

    @PostMapping("/{id}/addMoney")
    public ResponseEntity<BankAccountResponseDto> addMoney(@PathVariable int id,
            @RequestParam BigDecimal amount) {

        try {
            BankAccountResponseDto response = bankAccountService.addMoney(id, amount);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null); // Add custom error message if needed
        }
    }

    @PostMapping("/{id}/withdrawMoney")
    public ResponseEntity<BankAccountResponseDto> withdrawMoney(@PathVariable int id,
                                                                @RequestParam BigDecimal amount) {
        try {
            BankAccountResponseDto response = bankAccountService.withdrawMoney(id, amount);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null); // Add custom error message if needed
        }
    }

    @PostMapping("/transferMoney")
    public ResponseEntity<String> transferMoney(@RequestParam int fromAccountId, @RequestParam int toAccountId,
                                                @RequestParam BigDecimal amount) {
        try {
            bankAccountService.transferMoney(fromAccountId, toAccountId, amount);
            return ResponseEntity.ok("Transfer successful");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}