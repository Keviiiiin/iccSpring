package com.iccKevin.service.impl;

import com.iccKevin.dao.AccountDao;
import com.iccKevin.domain.Account;
import com.iccKevin.service.AccountService;

import java.util.List;

/**
 * @description: service实现类
 * @author: iccKevin
 * @create: 2020-05-06 20:45
 **/
public class AccountServiceImpl implements AccountService {

    private AccountDao accountDao;

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public List<Account> findAll() {
        return accountDao.findAll();
    }

    @Override
    public Account findById(Integer id) {
        return accountDao.findById(id);
    }

    @Override
    public void insert(Account account) {
        accountDao.insert(account);
    }

    @Override
    public void update(Account account) {
        accountDao.update(account);
    }

    @Override
    public void delete(Integer id) {
        accountDao.delete(id);
    }
}