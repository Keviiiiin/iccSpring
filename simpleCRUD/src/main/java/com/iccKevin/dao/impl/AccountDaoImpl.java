package com.iccKevin.dao.impl;

import com.iccKevin.domain.Account;
import com.iccKevin.dao.AccountDao;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.List;

/**
 * @description: dao实现类
 * @author: iccKevin
 * @create: 2020-05-06 20:41
 **/
public class AccountDaoImpl implements AccountDao {

    private QueryRunner runner;

    public void setRunner(QueryRunner runner) {
        this.runner = runner;
    }

    @Override
    public List<Account> findAll() {
        try{
            return runner.query("select * from acc",new BeanListHandler<Account>(Account.class));
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Account findById(Integer accountId) {
        try{
            return runner.query("select * from acc where id = ? ",new BeanHandler<Account>(Account.class),accountId);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insert(Account account) {
        try{
            runner.update("insert into acc(name,money)values(?,?)",account.getName(),account.getMoney());
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Account account) {
        try{
            runner.update("update acc set name=?,money=? where id=?",account.getName(),account.getMoney(),account.getId());
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Integer accountId) {
        try{
            runner.update("delete from acc where id=?",accountId);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}