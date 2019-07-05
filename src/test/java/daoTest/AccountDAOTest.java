package daoTest;

import baseTest.BaseTest;
import dao.AccountDAO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import po.Account;
import static org.junit.Assert.*;

public class AccountDAOTest extends BaseTest {
    @Autowired
    private AccountDAO accountDAO;

    @Test
    public void getByUser(){
        String user = "ui";
        Account account = accountDAO.getAccountByUser(user);

        assertEquals(account.getPassword(),accountDAO.getPassword(user));
    }

    @Rollback(true)
    @Test
    public void insert(){

        assertTrue(accountDAO.insertAccount("uiui","112213"));
    }

    @Test
    public void update(){

        assertTrue(accountDAO.updatePassword(1,"qwe"));
        Account account = new Account(1);
        account.setAge(11);
        assertTrue(accountDAO.updateInfo(account));
    }

    @Test
    public void updatePic(){
        int id = 1;

    }

    @Test
    public void delete(){
        assertTrue(accountDAO.delete(1));
    }



}
