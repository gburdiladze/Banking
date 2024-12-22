package ge.bog.banking.models.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomerResponseDto {

    public  int id;
    public int customerId;
    public String firstName;
    public String lastName;
    public String email;
    public String personalId;
    public String address;
}
