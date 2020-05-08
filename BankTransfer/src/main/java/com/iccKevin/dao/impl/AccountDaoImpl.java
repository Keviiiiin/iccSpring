package com.iccKevin.dao.impl;

import com.iccKevin.dao.IAccountDao;
import com.iccKevin.domain.Account;
//import com.iccKevin.utils.ConnectionUtils;
//import org.apache.commons.dbutils.QueryRunner;
//import org.apache.commons.dbutils.handlers.BeanHandler;
//import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 账户的持久层实现类
 */
@Repository("accountDao")
public class AccountDaoImpl implements IAccountDao {

//    @Autowired
//    private QueryRunner runner;

//    @Autowired
//    private ConnectionUtils cu;

    @Autowired
    private JdbcTemplate jt;

    @Override
    public List<Account> findAllAccount() {
        return jt.query("select * from acc", new BeanPropertyRowMapper<>(Account.class));
    }

    @Override
    public Account findAccountById(Integer accountId) {
        List<Account> accounts = jt.query("select * from acc where id = ?", new BeanPropertyRowMapper<>(Account.class), accountId);
        return accounts.isEmpty()?null:accounts.get(0);
    }

    @Override
    public Account findAccountByName(String accountName) {
        List<Account> accounts = jt.query("select * from acc where name = ?", new BeanPropertyRowMapper<>(Account.class), accountName);
        if(accounts.isEmpty())
            return null;
        if(accounts.size() > 1)
            throw new RuntimeException("结果集不唯一，数据有问题");
        return accounts.get(0);
    }

    @Override
    public void saveAccount(Account account) {
        jt.update("insert into acc (name,money) values(?,?)", new BeanPropertyRowMapper<>(Account.class), account.getName(),account.getMoney());
    }

    @Override
    public void updateAccount(Account account) {
        jt.update("update acc set name = ?,money=? where id = ?", account.getName(),account.getMoney(),account.getId());
    }

    @Override
    public void deleteAccount(Integer accountId) {
        jt.update("delete from acc where id=?", new BeanPropertyRowMapper<>(Account.class), accountId);
    }
}
