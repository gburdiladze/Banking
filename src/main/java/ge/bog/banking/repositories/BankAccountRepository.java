package ge.bog.banking.repositories;

import ge.bog.banking.models.entity.BankAccount;
import ge.bog.banking.models.entity.Customer;
import ge.bog.banking.models.entity.CustomerLoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankAccountRepository extends JpaRepository <BankAccount,Integer>{
    @Query("SELECT c FROM BankAccount c WHERE c.customerId = :customerId")
    public List<BankAccount> findAccountByClientId(int customerId);

}
