package dao;

import org.springframework.stereotype.Repository;
import po.Food;

import java.util.List;

@Repository
public interface FoodDAO { //TODO 大量查询要写

    Food getById(int id);

    List<Food> getAllFood();

    List<Food> getByGroup(int group); // 0-菜品

    List<Food> getFoodByLabelId(int label_id);

    Boolean insert(Food food);

    Boolean delete(int id);

    //Boolean update(Food food);

}
