package ge.bog.banking.config;

import ge.bog.banking.models.dto.BankAccountResponseDto;
import ge.bog.banking.models.dto.CustomerDto;
import ge.bog.banking.models.dto.CustomerResponseDto;
import ge.bog.banking.models.entity.BankAccount;
import ge.bog.banking.models.entity.Customer;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ApiMapper {
    Customer map(CustomerDto customerDto);
    CustomerResponseDto map(Customer customer);
    BankAccountResponseDto map(BankAccount account);
    List<BankAccountResponseDto> map(List<BankAccount> accounts);

}
