package com.iccKevin.dao;

import com.iccKevin.domain.Account;

import java.util.List;

public interface AccountDao {

    /**
     * 查询所有
     * @return
     */
    List<Account> findAll();

    /**
     * 查询一个
     * @return
     */
    Account findById(Integer id);

    /**
     * 保存
     * @param account
     */
    void insert(Account account);

    /**
     * 更新
     * @param account
     */
    void update(Account account);

    /**
     * 删除
     * @param id
     */
    void delete(Integer id);
}
