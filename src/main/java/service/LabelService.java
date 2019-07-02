package service;

import po.FoodLabel;
import po.Label;

import java.util.List;

public interface LabelService {

    //列出所有 label
    List<Label> getAllLabels();

    //根据食物找到其所有的标签
    List<FoodLabel> getByFoodId(int food_id);

    //根据某一标签找到符合的所有食物
    List<FoodLabel> getByLabelId(int label_id);

    //添加食物标签
    boolean addFoodLabel(int label_id,int food_id);

    //删除食物标签
    boolean removeFoodLabel(int label_id,int food_id);


    /** 以下为用户标签相关的内容*/


    


}
