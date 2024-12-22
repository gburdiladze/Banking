package ge.bog.banking.models.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name="bankAccounts")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-generate values
    public  int id;
    public int customerId;
    public String accountNumber;
    public String accountName;
    public String status;
    public BigDecimal amount;
    public int pin;

}
