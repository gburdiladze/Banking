package ge.bog.banking.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankAccountDto {
    public int customerId;
    public String accountName;
    public String pin;
}
