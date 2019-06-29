package dao;


import org.springframework.stereotype.Repository;
import po.FoodLabel;

import java.util.List;

@Repository
public interface FoodLabelDAO {

    List<FoodLabel> getByFoodId(int food_id);

    List<String> getAllFoodLabelName(); //所有食物标签名

    List<FoodLabel> getByLabelId(int label_id);

    Boolean insert(FoodLabel foodLabel);

    Boolean delete(int id);

    Boolean update(FoodLabel foodLabel);


}
