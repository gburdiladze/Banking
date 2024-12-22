package ge.bog.banking.repositories;

import ge.bog.banking.models.entity.Customer;
import ge.bog.banking.models.entity.CustomerLoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerLoanRepository extends JpaRepository<CustomerLoan, Integer> {
    @Query("SELECT c FROM CustomerLoan c WHERE c.customerId = :customerId")
    public List<CustomerLoan> findLoanByClientId(int customerId);
}
