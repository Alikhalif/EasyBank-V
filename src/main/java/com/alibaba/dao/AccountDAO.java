package com.alibaba.dao;

import com.alibaba.entities.Account;
import com.alibaba.entities.AccountStatus;
import com.alibaba.entities.CheckingAccount;
import com.alibaba.entities.SavingsAccount;

import java.util.List;
import java.util.Optional;

public interface AccountDAO {
    void createAccountChecking(Account account);
    void createAccountSavinge(Account account);
    List<Account> getAllAccount();
    public Account getAccountByNumber(int codeClient);
    void updateAccount(Account account);
    Boolean deleteAccount(int accountNumber);
    Boolean updateAccountByStatus(Account account);

    Account getAccountByClient(int code);
    List<Account> getAccountsByStatus(Account account);
    List<Account> getAccountsByDateCreation(Account account1);
    Boolean updateAccountByBalance(Account account);
    SavingsAccount getSavingsAccount(int accountNumber);
    Optional<SavingsAccount> updateSavingsAccount(SavingsAccount savingsAccount);
    Optional<CheckingAccount> updateCheckingAccount(CheckingAccount checkingAccount);
    CheckingAccount getCheckingAccount(int accountNumber);
}
