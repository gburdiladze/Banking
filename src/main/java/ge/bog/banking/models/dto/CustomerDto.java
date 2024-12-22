package ge.bog.banking.models.dto;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDto {

    @NotNull(message = "firstName name cannot be null")
    @NotEmpty(message = "firstName  name cannot be empty")
    public String firstName;

    @NotNull(message = "lastName  cannot be null")
    @NotEmpty(message = "lastName cannot be empty")
    public String lastName;

    @NotNull(message = "email cannot be null")
    @NotEmpty(message = "email cannot be empty")
    public String email;

    @NotNull(message = "personalId cannot be null")
    @NotEmpty(message = "personalId cannot be empty")
    public String personalId;

    @NotNull(message = "address cannot be null")
    @NotEmpty(message = "address cannot be empty")
    public String address;
}
