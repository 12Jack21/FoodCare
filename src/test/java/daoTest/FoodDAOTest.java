package daoTest;

import MyUtil.ReadCSV;
import baseTest.BaseTest;
import dao.FoodDAO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import po.Food;

import java.util.List;

import static org.junit.Assert.*;

//TODO 其他DAO测试类的编写
public class FoodDAOTest extends BaseTest {
    @Autowired
    private FoodDAO foodDAO;

    private static final double DELTA = 1e-15;

    @Test
    public void getLikeName(){
        String s = "冰淇淋";
        List<Food> foods = foodDAO.getByName(s);

        assertEquals(foods.toArray().length,5);

    }

    @Test
    public void getByName(){
        String s = "焦炸鸡腿";
        Food food = foodDAO.getBySpecifiedName(s);

        assertEquals(food.getHeat(),224);

    }

    @Test
    public void getByType(){
        String type = "四川菜";
//        List<Food> foods = foodDAO.getByType(type);

        //分页
        List<Food> foods1 = foodDAO.getByTypeLimit(50,10,type);

        assertEquals(5,foods1.toArray().length);

//        assertEquals(3,foods.toArray().length);
    }

    @Test
    public void getByCategory(){
        String category = "肉类";
        List<Food> foods = foodDAO.getByCategory(category);

        assertEquals(2,foods.toArray().length);
    }

    @Test
    public void insert(){
        Food food = new Food("water11",10.34);
        food.setGroup(1);
        Boolean op = foodDAO.insert(food);
        assertTrue(op);
    }

    @Test
    public void getById(){
        int id = 14;
        Food f = foodDAO.getById(id);
        assertEquals(4.06,f.getTanshui(),DELTA);
    }

    @Test
    public void batchInsert(){
        //TODO 将里面的 conn.commit进行调整
        ReadCSV.main(null);

    }


}
