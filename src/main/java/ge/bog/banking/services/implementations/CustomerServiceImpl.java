package ge.bog.banking.services.implementations;

import ge.bog.banking.Utils.RandomNumberGenerator;
import ge.bog.banking.config.ApiMapper;
import ge.bog.banking.models.dto.CustomerDto;
import ge.bog.banking.models.dto.CustomerResponseDto;
import ge.bog.banking.models.entity.Customer;
import ge.bog.banking.repositories.CustomerRepository;
import ge.bog.banking.services.CustomerService;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {


    private List<CustomerResponseDto> users = new ArrayList<>();
    private final CustomerRepository customerRepository;
    private final ApiMapper apiMapper;
    public CustomerServiceImpl(CustomerRepository customerRepository, ApiMapper apiMapper) {
        this.customerRepository=customerRepository;
        this.apiMapper=apiMapper;
    }

    @Override
    public CustomerResponseDto createUser(CustomerDto customerDto) {
        Customer cust=apiMapper.map(customerDto);
//        cust.customerId= RandomNumberGenerator.generateRandomCombination(9);
        var result=apiMapper.map(customerRepository.save(cust));
        return result;
    }

    @Override
    public List<CustomerResponseDto> getAllUsers(){

    List<Customer> customers= customerRepository.findAll();
// Map the list of Customer entities to CustomerResponseDto objects
        return customers.stream()
                .map(customer -> {
                    CustomerResponseDto dto = new CustomerResponseDto();
                    dto.setId(customer.getId());
                    dto.setFirstName(customer.getFirstName());
                    dto.setLastName(customer.getLastName());
                    dto.setEmail(customer.getEmail());
                    dto.setPersonalId(customer.getPersonalId());
                    dto.setAddress(customer.getAddress());
//                    dto.setClientKey(customer.getCustomerKey());

                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CustomerResponseDto getUserById(int id) {

        Optional<Customer> cust= customerRepository.findById(id);
        if (cust.isPresent()) {
            return  apiMapper.map(cust.get());
        }

        return null;
    }

    @Override
    public CustomerResponseDto updateCustomer(int id, CustomerDto cust) {
        var existingCustomer = customerRepository.findById(id);

        if (existingCustomer.isPresent()) {
            Customer customer = existingCustomer.get();
            customer.setFirstName(cust.getFirstName());
            customer.setLastName(cust.getLastName());
            customer.setEmail(cust.getEmail());
            customer.setPersonalId(cust.getPersonalId());
            customer.setAddress(cust.getAddress());
            Customer savedCustomer = customerRepository.save(customer);
            customerRepository.save(customer);
            return apiMapper.map(customer);
        }

        return null;
    }

    @Override
    public void deleteUserById(int id) {
        customerRepository.deleteById(id);
    }



    @Override
    public CustomerResponseDto getUserByClientPersolanId(String id) {
        var customer = customerRepository.findByClientPersonalId(id);

        if (customer == null) {
            return null;
        }
        return apiMapper.map(customer.get(0));
    }

}
