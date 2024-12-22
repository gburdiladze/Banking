package ge.bog.banking.config;

import ge.bog.banking.models.dto.BankAccountResponseDto;
import ge.bog.banking.models.dto.CustomerDto;
import ge.bog.banking.models.dto.CustomerResponseDto;
import ge.bog.banking.models.entity.BankAccount;
import ge.bog.banking.models.entity.Customer;

import java.util.List;
import java.util.stream.Collectors;

public class ApimapperImpl implements  ApiMapper {
    @Override
    public Customer map(CustomerDto dto) {
        if (dto == null) {
            return null;
        }
        Customer customer = new Customer();
        customer.firstName = dto.getFirstName();
        customer.lastName = dto.getLastName();
        customer.email = dto.getEmail();
        customer.personalId = dto.getPersonalId();
        customer.address = dto.getAddress();
        return customer;
    }

    @Override
    public CustomerResponseDto map(Customer customer) {
        if (customer == null) {
            return null;
        }
        CustomerResponseDto dto = new CustomerResponseDto();
        dto.setFirstName(customer.firstName);
        dto.setLastName(customer.lastName);
        dto.setEmail(customer.email);
        dto.setPersonalId(customer.personalId);
        dto.setAddress(customer.address);
//        dto.setClientKey(customer.customerKey);
        return dto;
    }

    @Override
    public BankAccountResponseDto map(BankAccount account) {
        if (account == null) {
            return null;
        }
        BankAccountResponseDto dto = new BankAccountResponseDto();
        dto.setId(dto.getId());
        dto.setAccountNumber(account.getAccountNumber());
        dto.setAccountName(account.getAccountName());
        dto.setStatus(account.getStatus());
        dto.setAmount(account.getAmount());
        dto.setPin(account.getPin());
        return dto;
    }

    @Override
    public List<BankAccountResponseDto> map(List<BankAccount> accounts) {
        // Map List<BankAccount> to List<BankAccountResponseDto> using streams
        return accounts.stream()
                .map(this::map) // this refers to the map(BankAccount account) method
                .collect(Collectors.toList());
    }

}


