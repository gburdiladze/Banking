package ge.bog.banking.services;

import ge.bog.banking.models.entity.Customer;
import ge.bog.banking.models.entity.CustomerLoan;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface CustomerLoanService {

    CustomerLoan createLoan(int customerId, BigDecimal amount);

    CustomerLoan repayLoan(int loanId, BigDecimal repaymentAmount);

    BigDecimal calculateRepaymentAmount(BigDecimal loanAmount);
    List<CustomerLoan> findLoanByClientId(int id);
}
