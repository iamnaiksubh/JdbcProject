package com.dnb.jdbc.demo.repo;

import com.dnb.jdbc.demo.dto.Account;
import com.dnb.jdbc.demo.utils.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountRepositoryImpl implements AccountRepository {

    private static AccountRepository accountRepository;

    private AccountRepositoryImpl() {

    }

    public static AccountRepository getInstance() {
        synchronized (AccountRepositoryImpl.class) {
            if (accountRepository == null) accountRepository = new AccountRepositoryImpl();
        }

        return accountRepository;
    }

    @Override
    public Account createAccount(Account account) {
        Optional<Connection> connection = DBUtils.getConnection();
        String createAccountStatement = "insert into account " + "(accountId, accountHolderName, accountType, balance, contactNumber, address, accountCreatedDate, dob, accountStatus) " + "values (?,?,?,?,?,?,?,?,?)";

        PreparedStatement preparedStatement = null;
        if (connection.isPresent()) {
            try {

                preparedStatement = connection.get().prepareStatement(createAccountStatement);
                preparedStatement.setString(1, account.getAccountId());
                preparedStatement.setString(2, account.getAccountHolderName());
                preparedStatement.setString(3, account.getAccountType());
                preparedStatement.setFloat(4, account.getBalance());
                preparedStatement.setString(5, account.getContactNumber());
                preparedStatement.setString(6, account.getAddress());
                preparedStatement.setDate(7, Date.valueOf(account.getAccountCreatedDate()));
                preparedStatement.setDate(8, Date.valueOf(account.getDob()));
                preparedStatement.setBoolean(9, account.isAccountStatus());
                int result = preparedStatement.executeUpdate();

                if (result > 0) return account;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (connection.isPresent()) {
                    DBUtils.closeConnection(connection.get());
                }
            }
        }

        return null;
    }

    @Override
    public Optional<Account> getAccountById(String accountId) {
        Optional<Connection> connection = DBUtils.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from account where accountId = ?";

        if (connection.isPresent()) {
            try {
                preparedStatement = connection.get().prepareStatement(query);
                preparedStatement.setString(1, accountId);
                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    Account account = new Account();
                    account.setAccountId(resultSet.getString("accountId"));
                    account.setAccountHolderName(resultSet.getString("accountHolderName"));
                    account.setAccountType(resultSet.getString("accountType"));
                    account.setBalance(resultSet.getFloat("balance"));
                    account.setContactNumber(resultSet.getString("contactNumber"));
                    account.setAddress(resultSet.getString("address"));
                    account.setAccountCreatedDate(resultSet.getDate("accountCreatedDate").toLocalDate());
                    account.setDob(resultSet.getDate("dob").toLocalDate());
                    account.setAccountStatus(resultSet.getBoolean("accountStatus"));

                    return Optional.of(account);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                connection.ifPresent(DBUtils::closeConnection);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteAccount(String accountId) {
        Optional<Connection> connection = DBUtils.getConnection();
        PreparedStatement preparedStatement = null;
        String deleteQuery = "delete from account where accountId = ?";

        if (connection.isPresent()) {
            try {
                preparedStatement = connection.get().prepareStatement(deleteQuery);
                preparedStatement.setString(1, accountId);
                int effectedRow = preparedStatement.executeUpdate(deleteQuery);

                if(effectedRow > 0)
                    return true;

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connection.ifPresent(DBUtils::closeConnection);
            }
        }

        return false;
    }

    @Override
    public boolean updateAccount(String accountId) {
        Optional<Connection> connection = DBUtils.getConnection();
        String deleteQuery = "alter account where accountId=?";

        PreparedStatement preparedStatement = null;
        if (connection.isPresent()) {
            try {
                preparedStatement.setString(1, accountId);
                preparedStatement = connection.get().prepareStatement(deleteQuery);
                int effectedRow = preparedStatement.executeUpdate(deleteQuery);

                if(effectedRow > 0)
                    return true;

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connection.ifPresent(DBUtils::closeConnection);
            }
        }
        return false;
    }

    @Override
    public List<Account> getAllAccount() {
        Optional<Connection> connection = DBUtils.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from account";
        List<Account> accountList = new ArrayList<>();

        if (connection.isPresent()) {
            try {
                preparedStatement = connection.get().prepareStatement(query);
                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Account account = new Account();
                    account.setAccountId(resultSet.getString("accountId"));
                    account.setAccountHolderName(resultSet.getString("accountHolderName"));
                    account.setAccountType(resultSet.getString("accountType"));
                    account.setBalance(resultSet.getFloat("balance"));
                    account.setContactNumber(resultSet.getString("contactNumber"));
                    account.setAddress(resultSet.getString("address"));
                    account.setAccountCreatedDate(resultSet.getDate("accountCreatedDate").toLocalDate());
                    account.setDob(resultSet.getDate("dob").toLocalDate());
                    account.setAccountStatus(resultSet.getBoolean("accountStatus"));

                    accountList.add(account);
                }

                return accountList;
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                connection.ifPresent(DBUtils::closeConnection);
            }
        }
        return null;
    }
}
