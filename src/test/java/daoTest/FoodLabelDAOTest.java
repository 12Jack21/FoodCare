package daoTest;

import baseTest.BaseTest;
import dao.FoodLabelDAO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import po.FoodLabel;
import static org.junit.Assert.*;
import java.util.List;

public class FoodLabelDAOTest extends BaseTest {
    @Autowired
    private FoodLabelDAO foodLabelDAO;

    @Test
    public void getLabel(){
        List<FoodLabel> labels = foodLabelDAO.getLabelByFoodId(1);

        List<FoodLabel> foods = foodLabelDAO.getLabelByFoodId(1);

        assertEquals(labels.toArray().length,1);
        assertEquals(foods.toArray().length,1);
    }

    @Test
    public void insert(){
        assertTrue(foodLabelDAO.insert(3,2));
    }

    @Test
    public void delete(){
        assertTrue(foodLabelDAO.delete(1,1));
    }

}
