package ge.bog.banking.controllers;

import ge.bog.banking.models.entity.CustomerLoan;
import ge.bog.banking.services.CustomerLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/loans")
public class LoanController {
    private final CustomerLoanService customerLoanService;

    @Autowired
    public LoanController(CustomerLoanService customerLoanService) {
        this.customerLoanService = customerLoanService;
    }

    @PostMapping("/create")
    public ResponseEntity<CustomerLoan> createLoan(@RequestParam int customerId, @RequestParam BigDecimal amount) {
        CustomerLoan loan = customerLoanService.createLoan(customerId, amount);
        return ResponseEntity.ok(loan);
    }

    @PostMapping("/repay")
    public ResponseEntity<CustomerLoan> repayLoan(@RequestParam int loanId, @RequestParam BigDecimal repaymentAmount) {
        CustomerLoan loan = customerLoanService.repayLoan(loanId, repaymentAmount);
        return ResponseEntity.ok(loan);
    }
}
