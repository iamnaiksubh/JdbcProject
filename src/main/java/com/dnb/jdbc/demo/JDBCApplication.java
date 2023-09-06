package com.dnb.jdbc.demo;

import com.dnb.jdbc.demo.config.Config;
import com.dnb.jdbc.demo.dto.Account;
import com.dnb.jdbc.demo.services.AccountService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class JDBCApplication {
    private static AccountService accountService = null;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        accountService = applicationContext.getBean(AccountService.class);

        int choice = -1;
        do {
            System.out.println("Enter your choice :\n" +
                    "1. Create Account \n" +
                    "2. Search Account \n" +
                    "3. Delete Account \n" +
                    "4. Get All Account \n" +
                    "5. Exit");
            choice = sc.nextInt();

            if (choice < 1 || choice > 5) {
                System.out.println("Enter correct ");
                continue;
            }

            switch (choice) {
                case 1 -> createAccount();
                case 2 -> getAccount();
                case 3 -> deleteAccount();
                case 4 -> getAllAccount();
                case 5 -> {
                }
            }

        } while (choice != 5);


    }

    private static void getAllAccount() {
        List<Account> accountList = accountService.getAllAccount();

        accountList.forEach(account -> {
            System.out.println(account.getAccountId() + " " + account.getAccountHolderName() + " " + account.getAccountType() + " " +
                    account.getBalance() + " " + account.getAccountCreatedDate() + " " + account.getDob() + " " + account.isAccountStatus());
        });
    }

    private static void deleteAccount() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the account Id which you want to delete : ");
        String accountId = sc.next();
        boolean result = accountService.deleteAccount(accountId);

        if (result) {
            System.out.println("Account deleted");
        } else {
            System.out.println("No account found");
        }
    }

    public static void createAccount() {
        List<Account> accounts = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        System.out.println("How many account you want to create : ");
        int n = sc.nextInt();
        for (int i = 0; i < n; i++) {
            Account newAccount = new Account();
            System.out.println("Account Id : ");
            newAccount.setAccountId(sc.next());

            System.out.println("Account Holder Name : ");
            newAccount.setAccountHolderName(sc.next());

            System.out.println("Account Type : ");
            newAccount.setAccountType(sc.next());

            System.out.println("Balance : ");
            newAccount.setBalance(sc.nextFloat());

            System.out.println("Contact Number : ");
            newAccount.setContactNumber(sc.next());

            System.out.println("Address : ");
            newAccount.setAddress(sc.next());

            System.out.println("DOB : ");
            String dob = sc.next();
            newAccount.setDob(convertToLocalDate(dob));

//          System.out.println("Account Status : ");
            newAccount.setAccountStatus(true);

            accounts.add(newAccount);
        }

        accounts.forEach(account -> {
            accountService.createAccount(account);
        });
    }

    private static LocalDate convertToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(date, formatter);
    }

    public static void getAccount() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the account Id : ");
        String accountId = sc.next();
        Optional<Account> result = accountService.getAccountById(accountId);

        if (result.isPresent()) {
            Account account = result.get();
            System.out.println(account.getAccountId() + " " + account.getAccountHolderName() + " " + account.getAccountType() + " " +
                    account.getBalance() + " " + account.getAccountCreatedDate() + " " + account.getDob() + " " + account.isAccountStatus());
        } else {
            System.out.println("No account found for this " + accountId + " account id.");
        }
    }

}