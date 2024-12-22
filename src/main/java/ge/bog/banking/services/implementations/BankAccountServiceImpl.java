package ge.bog.banking.services.implementations;

import ge.bog.banking.Utils.Constants;
import ge.bog.banking.Utils.RandomNumberGenerator;
import ge.bog.banking.config.ApiMapper;
import ge.bog.banking.models.dto.BankAccountDto;
import ge.bog.banking.models.dto.BankAccountResponseDto;
import ge.bog.banking.models.dto.UpdateBankAccountDto;
import ge.bog.banking.models.entity.BankAccount;
import ge.bog.banking.repositories.BankAccountRepository;
import ge.bog.banking.repositories.CustomerRepository;
import ge.bog.banking.services.BankAccountService;
import io.micrometer.common.util.StringUtils;
import org.aspectj.weaver.patterns.IfPointcut;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
@Service
public class BankAccountServiceImpl implements BankAccountService {

 private  final  BankAccountRepository bankAccountRepository;
    private final CustomerRepository customerRepository;

    private final ApiMapper apiMapper;

    public BankAccountServiceImpl(BankAccountRepository  bankAccountRepository ,CustomerRepository customerRepository ,
                                  ApiMapper apiMapper) {
        this.bankAccountRepository=bankAccountRepository;
        this.apiMapper=apiMapper;
        this.customerRepository=customerRepository;
    }

    @Override
    public BankAccountResponseDto createccount(BankAccountDto dto) {

        if (!customerRepository.findById(dto.customerId).isPresent()){

            throw new RuntimeException("User Not Found");
        }

        BankAccount bankAccount=new BankAccount();
        bankAccount.customerId=dto.customerId;
        bankAccount.accountNumber=RandomNumberGenerator.generateRandomCombination(10);
        bankAccount.amount=new BigDecimal(0);
        if (StringUtils.isEmpty(dto.accountName)){
            bankAccount.accountName="SuperAccount";
        }
        else {
            bankAccount.accountName=dto.accountName;

        }

        bankAccount.status= Constants.ACCOUNT_ACTIVE;
        bankAccount.pin=RandomNumberGenerator.generateRandomAccountpin();
        bankAccountRepository.save(bankAccount);
        return  apiMapper.map(bankAccountRepository.save(bankAccount));
    }
    @Override
    public BankAccountResponseDto getAccountById(int id) {

        Optional<BankAccount> account= bankAccountRepository.findById(id);
        if (account.isPresent()) {
            return  apiMapper.map(account.get());
        }

        return null;
    }

    @Override
    public BankAccountResponseDto updateBankAccount(int id,UpdateBankAccountDto update) {
        var acc = bankAccountRepository.findById(id);

        if (acc.isPresent()) {
            BankAccount account = acc.get();
            account.setAccountName(update.getAccountName());
            account.setPin(update.getPin());

            BankAccount savedAccount = bankAccountRepository.save(account);
            return apiMapper.map(savedAccount);
        }

        return null;
    }

    @Override
    public BankAccountResponseDto blockAccount(int id) {
        Optional<BankAccount> account = bankAccountRepository.findById(id);
        if (account.isEmpty()) {
            throw new RuntimeException("Bank account not found with ID: " + id);
        }

        BankAccount bankAccount = account.get();
        bankAccount.setStatus(Constants.ACCOUNT_CUSTOMER_BLOCK); // Assuming you have a status constant for "BLOCKED"
        bankAccountRepository.save(bankAccount);
        return apiMapper.map(bankAccount);
    }

    @Override
    public BankAccountResponseDto blockByBankAccount(int id) {
        Optional<BankAccount> account = bankAccountRepository.findById(id);
        if (account.isEmpty()) {
            throw new RuntimeException("Bank account not found with ID: " + id);
        }

        BankAccount bankAccount = account.get();
        bankAccount.setStatus(Constants.ACCOUNT_BANK_BLOCK); // Assuming you have a status constant for "BLOCKED"
        bankAccountRepository.save(bankAccount);
        return apiMapper.map(bankAccount);
    }

    @Override
    public BankAccountResponseDto unblockAccount(int id) {
        Optional<BankAccount> account = bankAccountRepository.findById(id);
        if (account.isEmpty()) {
            throw new RuntimeException("Bank account not found with ID: " + id);
        }

        BankAccount bankAccount = account.get();
        bankAccount.setStatus(Constants.ACCOUNT_ACTIVE); // Assuming you have a status constant for "ACTIVE"
        bankAccountRepository.save(bankAccount);
        return apiMapper.map(bankAccount);
    }

    @Override
    public BankAccountResponseDto addMoney(int customerKey, BigDecimal amount) {
        Optional<BankAccount> acc  = bankAccountRepository.findById(customerKey);
        if (!acc.isPresent()) {
            new RuntimeException("Account not found");
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        var account=acc.get();
        account.setAmount(amount);
        bankAccountRepository.save(account);

        return apiMapper.map(account);
    }

    @Override
    public BankAccountResponseDto withdrawMoney(int customerKey, BigDecimal amount) {
        Optional<BankAccount> acc  = bankAccountRepository.findById(customerKey);
        if (!acc.isPresent()) {
            new RuntimeException("Account not found");
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        var account=acc.get();

        if (account.getAmount().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        account.setAmount(account.getAmount().subtract(amount));
        bankAccountRepository.save(account);

        return apiMapper.map(account);
    }

    @Override
    public void transferMoney(int fromAccountId, int toAccountId, BigDecimal amount) {
        if (fromAccountId == toAccountId) {
            throw new IllegalArgumentException("Source and destination accounts must be different");
        }

        Optional<BankAccount> fromAccount = bankAccountRepository.findById(fromAccountId);
        if (!fromAccount.isPresent()){
            new RuntimeException("Source account not found");
        }
        Optional<BankAccount> toAccount = bankAccountRepository.findById(toAccountId);

        if (!toAccount.isPresent()){
            new RuntimeException("Destination account not found");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        var from=fromAccount.get();
        var to=toAccount.get();
        if (from.getAmount().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance in source account");
        }

        from.setAmount(from.getAmount().subtract(amount));
        to.setAmount(to.getAmount().add(amount));

        bankAccountRepository.save(from);
        bankAccountRepository.save(to);
    }

    @Override
    public List<BankAccount> findAccountByClientId(int customerId) {
        return bankAccountRepository.findAccountByClientId(customerId);
    }
}
