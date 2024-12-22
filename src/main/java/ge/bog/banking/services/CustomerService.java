package ge.bog.banking.services;
import ge.bog.banking.models.dto.CustomerDto;
import ge.bog.banking.models.dto.CustomerResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface CustomerService {
     CustomerResponseDto createUser(CustomerDto user);
     List<CustomerResponseDto> getAllUsers();
     CustomerResponseDto getUserById(int id);
     CustomerResponseDto updateCustomer(int id, CustomerDto customer);
     void deleteUserById(int id);
     CustomerResponseDto getUserByClientPersolanId(String id);

}
