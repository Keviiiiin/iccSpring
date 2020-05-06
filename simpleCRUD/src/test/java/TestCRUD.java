import com.iccKevin.dao.AccountDao;
import com.iccKevin.domain.Account;
import com.iccKevin.service.AccountService;
import org.apache.commons.dbutils.QueryRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @description: 测试增删改查
 * @author: iccKevin
 * @create: 2020-05-06 20:48
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:bean.xml")
public class TestCRUD {

//    private ApplicationContext ac;

    @Autowired
    private AccountService accountService;

//    @Before
//    public void init(){
//        ac = new ClassPathXmlApplicationContext("bean.xml");
////        System.out.println(ac.getBean("accountDao", AccountDao.class));
////        System.out.println(ac.getBean("runner", QueryRunner.class));
//        accountService = ac.getBean("accountService", AccountService.class);
//    }
    @Test
    public void testFindAll(){
        List<Account> accounts = accountService.findAll();
        System.out.println(accounts);
    }
    @Test
    public void testFindOne(){
        Account account = accountService.findById(4);
        System.out.println(account);
    }
    @Test
    public void testSave() {
        Account account = new Account();
        account.setName("test");
        account.setMoney(12345f);
        //3.执行方法
        accountService.insert(account);

    }

    @Test
    public void testUpdate() {
        //3.执行方法
        Account account = accountService.findById(4);
        account.setMoney(23456f);
        accountService.update(account);
    }

    @Test
    public void testDelete() {
        //3.执行方法
        accountService.delete(4);
    }
}