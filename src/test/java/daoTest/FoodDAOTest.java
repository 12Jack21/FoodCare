package daoTest;

import MyUtil.ReadCSV;
import baseTest.BaseTest;
import dao.FoodDAO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import po.Food;
import static org.junit.Assert.*;

//TODO 其他DAO测试类的编写
public class FoodDAOTest extends BaseTest {
    @Autowired
    private FoodDAO foodDAO;

    @Test
    public void getByName(){
        String s = "焦炸鸡腿";
        Food food = foodDAO.getBySpecifiedName(s);

        assertEquals(food.getHeat(),224);

    }

    @Test
    public void getByType(){

    }

    @Test
    public void insert(){
        Food food = new Food("water11",10.34);
        food.setGroup(1);
        Boolean op = foodDAO.insert(food);
        assertTrue(op);
    }

    @Test
    public void batchInsert(){
        ReadCSV.main(null);

    }


}
