package com.dnb.jdbc.demo.services;

import com.dnb.jdbc.demo.dto.Account;
import com.dnb.jdbc.demo.repo.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    public AccountRepository accountRepository;

    @Override
    public Account createAccount(Account account) {
        return accountRepository.createAccount(account);
    }

    @Override
    public Optional<Account> getAccountById(String accountId) {
        return accountRepository.getAccountById(accountId);
    }

    @Override
    public boolean deleteAccount(String accountId) {
        return accountRepository.deleteAccount(accountId);
    }

    @Override
    public boolean updateAccount(String accountId) {

        return false;
    }

    @Override
    public List<Account> getAllAccount() {
        return accountRepository.getAllAccount();
    }
}
