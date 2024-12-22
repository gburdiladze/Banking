package ge.bog.banking.config;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class CustomErrorResponse {
    private String message;
    private List<String> errors;

}
