package com.dnb.jdbc.demo.services;

import com.dnb.jdbc.demo.dto.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    public Account createAccount(Account account);
    public Optional<Account> getAccountById(String accountId);
    public boolean deleteAccount(String accountId);
    public boolean updateAccount(String accountId);
    public List<Account> getAllAccount();

}
