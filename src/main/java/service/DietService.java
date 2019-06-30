package service;

import po.Diet;

public interface DietService {

    //日期用今天
    boolean addDiet(int group,int account_id);

    boolean addDietDetail(int diet_id,int food_id,int quantity);

    boolean removeDietDetail(int diet_id,int food_id);

    //在移除最后一个 dietDetail时，移除该 diet
    boolean removeDiet(int id);

    
    //更新 某标签相对于某用户的权重值

}
