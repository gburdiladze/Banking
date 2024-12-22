package ge.bog.banking.controllers;

import ge.bog.banking.models.dto.BankAccountResponseDto;
import ge.bog.banking.models.dto.CustomerResponseDto;
import ge.bog.banking.models.entity.BankAccount;
import ge.bog.banking.models.entity.CustomerLoan;
import ge.bog.banking.repositories.BankAccountRepository;
import ge.bog.banking.services.BankAccountService;
import ge.bog.banking.services.CustomerLoanService;
import ge.bog.banking.services.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Report")
public class ReportController {

    private final CustomerService userService;
    private final CustomerLoanService customerLoanService;
    private  final BankAccountService bankAccountService ;

    @Autowired
    public ReportController(CustomerService userService,CustomerLoanService customerLoanService,
                            BankAccountService bankAccountService) {
        this.userService = userService;
        this.customerLoanService=customerLoanService;
        this.bankAccountService=bankAccountService;

    }

    @GetMapping("getUserByClientPersonalId/{id}")
    public ResponseEntity<CustomerResponseDto> getUserByClientPersonalId(@PathVariable String id) {
        try {
            var cust = userService.getUserByClientPersolanId(id.trim());
            return ResponseEntity.ok(cust); // HTTP 200 OK
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // HTTP 404 Not Found if customer doesn't exist
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // HTTP 500 for other errors
        }
    }

    @GetMapping("getUserById/{id}")
    public ResponseEntity<CustomerResponseDto> getUserById(@PathVariable int  id) {
        try {
            CustomerResponseDto customer = userService.getUserById(id);
            return ResponseEntity.ok(customer); // HTTP 200 OK
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // HTTP 404 Not Found if customer doesn't exist
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // HTTP 500 for other errors
        }
    }


    @GetMapping("getLoanByCustomerId/{id}")
    public ResponseEntity<List<CustomerLoan>> getLoanByCustomerId(@PathVariable int id) {
        try {
            var loans = customerLoanService.findLoanByClientId(id);
            return ResponseEntity.ok(loans); // HTTP 200 OK
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // HTTP 404 Not Found if customer doesn't exist
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // HTTP 500 for other errors
        }
    }

    @GetMapping("findAccountByClientId/{id}")
    public ResponseEntity<List<BankAccount>> findAccountByClientId(@PathVariable int id) {
        try {
            var accs = bankAccountService.findAccountByClientId(id);
            return ResponseEntity.ok(accs); // HTTP 200 OK
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // HTTP 404 Not Found if customer doesn't exist
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // HTTP 500 for other errors
        }
    }

    @GetMapping("getAccountById/{id}")
    public ResponseEntity<BankAccountResponseDto> getAccountById(@PathVariable int id) {
        try {
            BankAccountResponseDto responseDto = bankAccountService.getAccountById(id);
            return ResponseEntity.ok(responseDto); // HTTP 200 OK
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // HTTP 404 Not Found if customer doesn't exist
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // HTTP 500 for other errors
        }
    }
}
