package ge.bog.banking.services.implementations;

import ge.bog.banking.config.ApiMapper;
import ge.bog.banking.models.entity.Customer;
import ge.bog.banking.models.entity.CustomerLoan;
import ge.bog.banking.repositories.CustomerLoanRepository;
import ge.bog.banking.services.CustomerLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class CustomerLoanServiceImpl implements CustomerLoanService {

    private final CustomerLoanRepository customerLoanRepository;
    private final ApiMapper apiMapper;

    @Autowired
    public CustomerLoanServiceImpl(CustomerLoanRepository customerLoanRepository,ApiMapper apiMapper) {
        this.customerLoanRepository = customerLoanRepository;
        this.apiMapper=apiMapper;
    }

    @Override
    public CustomerLoan createLoan(int customerId, BigDecimal amount) {
        // Validate the loan amount
        if (amount.compareTo(BigDecimal.ZERO) < 0 || amount.compareTo(BigDecimal.valueOf(5000)) > 0) {
            throw new IllegalArgumentException("Loan amount must be between 0 and 5000 GEL");
        }

        // Create the loan object
        CustomerLoan loan = new CustomerLoan();
        loan.customerId=customerId;
        loan.ammount=amount;
        loan.setLastPaymentDate(LocalDateTime.now().plusMonths(1));

        // Save the loan object to the database
        return customerLoanRepository.save(loan);
    }

    @Override
    public CustomerLoan repayLoan(int loanId, BigDecimal repaymentAmount) {
        // Find the loan by ID
        var loanInDb = customerLoanRepository.findById(loanId);
        if (!loanInDb.isPresent()) {
            new IllegalArgumentException("Loan not found");
        }
        var loan=loanInDb.get();

        // Calculate the repayment amount
        BigDecimal repaymentDue = calculateRepaymentAmount(loan.getAmmount());

        // Check if the repayment amount is correct (must be at least 110% of the loan amount)
        if (repaymentAmount.compareTo(repaymentDue) != 0) {
            throw new IllegalArgumentException("Repayment amount must be 110% of the loan amount");
        }

        // Set the last payment date
        loan.setLastPaymentDate(LocalDateTime.now());

        // Update the loan record with the repayment
        return customerLoanRepository.save(loan);
    }

    @Override
    public BigDecimal calculateRepaymentAmount(BigDecimal loanAmount) {
        // Calculate 110% of the loan amount
        return loanAmount.multiply(BigDecimal.valueOf(1.10));
    }

    @Override
    public List<CustomerLoan> findLoanByClientId(int id) {
        return  customerLoanRepository.findLoanByClientId(id);

    }

}
