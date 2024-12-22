package ge.bog.banking.models.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Setter
@Getter
public class BankAccountResponseDto {
    public int id;
    public String customerId;
    public String accountNumber;
    public String accountName;
    public String status;
    public BigDecimal amount;
    public int pin;

}
