package ge.bog.banking.models.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name="CustomerLoans")
public class CustomerLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-generate values
    public  int id;
    @Column(name = "customer_Id")
    public  int customerId;
    public BigDecimal ammount;
    private LocalDateTime lastPaymentDate;
}
