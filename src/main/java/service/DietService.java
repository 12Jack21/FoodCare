package service;

import po.Diet;
import po.DietDetail;

import java.util.Date;
import java.util.List;

public interface DietService {

    //创建日期用今天
    boolean addDiet(int group,int account_id);

    boolean addDietDetail(int diet_id,int food_id,int quantity);

    boolean removeDietDetail(int diet_id,int food_id);

    //在移除最后一个 dietDetail时，移除该 diet
    boolean removeDiet(int id);

    //得到某用户当天的所有 diet
    List<Diet> getDietByAccToday(int account_id);

    //得到某用户当天的某个 diet (0-早餐，1-午餐，2-晚餐)
    Diet getDietByAccDateGroup(int account_id,int group);

    List<Diet> getDietByAccDate(int account_id, Date date);

    //得到某个 Diet中的所有明细
    List<DietDetail> getDetailsByDiet(int diet_id);

    //更新 某标签相对于某用户的权重值(添加了dietDetail后需要同步更新)


}
