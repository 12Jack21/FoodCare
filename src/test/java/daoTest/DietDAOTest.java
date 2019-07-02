package daoTest;

import baseTest.BaseTest;
import dao.DietDAO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import po.Account;
import po.Diet;

//import java.sql.Date;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class DietDAOTest extends BaseTest {
    @Autowired
    private DietDAO dietDAO;

    @Test
    public void getByAcc(){
        List<Diet> diets1 = dietDAO.getByAccount(1);

        java.sql.Date dateee = new java.sql.Date(new Date().getTime());
        List<Diet> date = dietDAO.getByAccDate(1,dateee);

        Diet group = dietDAO.getByAccGroup(1,2,dateee);

        assertEquals(3,diets1.toArray().length);

        assertEquals(1,date.toArray().length);

        assertEquals(3,group.getId());

    }

    @Test
    public void insert(){
        java.sql.Date dateee = new java.sql.Date(new Date().getTime());

        Diet diet = new Diet(1,dateee,new Account(1));

        assertTrue(dietDAO.insert(diet));
    }

    @Test
    public void delete(){
        assertTrue(dietDAO.delete(2));
    }


}
