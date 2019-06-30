package dao;


import org.springframework.stereotype.Repository;
import po.FoodLabel;

import java.util.List;

@Repository
public interface FoodLabelDAO {

    List<FoodLabel> getLabelByFoodId(int food_id);

    List<FoodLabel> getFoodByLabelId(int label_id);

    Boolean insert(int label_id,int food_id);

    Boolean delete(int label_id,int food_id);

    //Boolean update(FoodLabel foodLabel);


}
