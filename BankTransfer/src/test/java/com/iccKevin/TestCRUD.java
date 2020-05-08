package com.iccKevin;

import com.iccKevin.domain.Account;
import com.iccKevin.service.IAccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * 使用Junit单元测试：测试我们的配置
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:bean.xml")
public class TestCRUD {

    @Autowired
    private IAccountService accountService;

    @Test
    public void testFindAll(){
        List<Account> accounts = accountService.findAllAccount();
        System.out.println(accounts);
    }
    @Test
    public void testFindOne(){
        Account account = accountService.findAccountById(5);
        System.out.println(account);
    }
    @Test
    public void testSave() {
        Account account = new Account();
        account.setName("test");
        account.setMoney(12345f);
        //3.执行方法
        accountService.saveAccount(account);

    }

    @Test
    public void testUpdate() {
        //3.执行方法
        Account account = accountService.findAccountById(5);
        account.setMoney(23456f);
        accountService.updateAccount(account);
    }

    @Test
    public void testDelete() {
        //3.执行方法
        accountService.deleteAccount(4);
    }

    @Test
    public  void testTransfer(){
        accountService.transfer("aaa","bbb",100f);
    }
}
