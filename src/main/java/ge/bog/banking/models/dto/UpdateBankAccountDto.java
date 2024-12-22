package ge.bog.banking.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBankAccountDto {
    public String accountName;
    public int pin;

}
