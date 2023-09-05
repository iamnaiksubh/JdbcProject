package com.dnb.jdbc.demo.services;

import com.dnb.jdbc.demo.dto.Account;
import com.dnb.jdbc.demo.repo.AccountRepository;
import com.dnb.jdbc.demo.repo.AccountRepositoryImpl;

import java.util.List;
import java.util.Optional;

public class AccountServiceImpl implements AccountService {

    private static AccountService accountService = null;
    private AccountServiceImpl(){

    }

    public static AccountService getInstance(){
        synchronized (AccountServiceImpl.class){
            if (accountService == null){
                accountService = new AccountServiceImpl();
            }
        }

        return accountService;
    }
    @Override
    public Account createAccount(Account account) {
        AccountRepository accountRepository = AccountRepositoryImpl.getInstance();
        return accountRepository.createAccount(account);
    }

    @Override
    public Optional<Account> getAccountById(String accountId) {
        AccountRepository accountRepository = AccountRepositoryImpl.getInstance();
        return  accountRepository.getAccountById(accountId);
    }

    @Override
    public boolean deleteAccount(String accountId) {
        AccountRepository accountRepository = AccountRepositoryImpl.getInstance();
        return accountRepository.deleteAccount(accountId);
    }

    @Override
    public boolean updateAccount(String accountId) {

        return false;
    }

    @Override
    public List<Account> getAllAccount() {
        AccountRepository accountRepository = AccountRepositoryImpl.getInstance();
        return accountRepository.getAllAccount();
    }
}
