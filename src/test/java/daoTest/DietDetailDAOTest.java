package daoTest;

import baseTest.BaseTest;
import dao.DietDetailDAO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import po.Diet;
import po.DietDetail;
import po.Food;

import java.util.List;
import static org.junit.Assert.*;


public class DietDetailDAOTest extends BaseTest {
    @Autowired
    private DietDetailDAO dietDetailDAO;

    @Test
    public void getById(){
        List<DietDetail> dietDetails = dietDetailDAO.getByDietId(2);
        assertEquals(dietDetails.toArray().length,1);
    }

    @Test
    public void insert(){
        DietDetail dietDetail = new DietDetail(12,new Diet(2),new Food(14));
        assertTrue(dietDetailDAO.insert(dietDetail));
    }

    @Test
    public void delete(){
        assertTrue(dietDetailDAO.delete(2,15));
    }

    @Test
    public void update(){
        assertTrue(dietDetailDAO.update(1,18,21));
    }


}
