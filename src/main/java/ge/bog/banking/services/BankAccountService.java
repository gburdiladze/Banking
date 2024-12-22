package ge.bog.banking.services;

import ge.bog.banking.models.dto.*;
import ge.bog.banking.models.entity.BankAccount;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface BankAccountService {
    BankAccountResponseDto createccount(BankAccountDto user);
    BankAccountResponseDto updateBankAccount(int id,UpdateBankAccountDto update);
    BankAccountResponseDto getAccountById(int id);
    BankAccountResponseDto blockAccount(int id);
    BankAccountResponseDto blockByBankAccount(int id);
    BankAccountResponseDto unblockAccount(int id);

    BankAccountResponseDto addMoney(int customerKey, BigDecimal amount);
    BankAccountResponseDto withdrawMoney(int customerKey, BigDecimal amount);
    void transferMoney(int fromAccountId, int customerKey, BigDecimal amount);
    List<BankAccount> findAccountByClientId(int customerId);

    }
