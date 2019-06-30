package dao;

import org.springframework.stereotype.Repository;
import po.Food;

import java.util.List;

@Repository
public interface FoodDAO { //TODO 大量查询要写

    Food getById(int id);

    Food getBySpecifiedName(String name); //精确查找

    List<Food> getByName(String name);//模糊查找

    List<Food> getAllFood();

    List<Food> getByGroup(int group); // 0-菜品

    List<Food> getByType(String type);//模糊查找菜系

    List<Food> getByCategory(String category);//模糊查找类别（食品）



    Boolean insert(Food food);

    Boolean delete(int id);

    //Boolean update(Food food);

}
