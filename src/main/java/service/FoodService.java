package service;

import po.Account;
import po.Food;
import po.FoodLabel;
import po.Label;
import vo.FoodReg;

import java.util.List;

public interface FoodService {

    Food getFoodById(int id);

    List<Food> getAllFood();

    //按照常用的级别来得到排序后的食物列表
    List<Food> getCommonFood();

    //得到所有食物的标签
    List<Label> getAllLabel();

    //根据标签查找食物
    List<Food> getFoodByLabel(int label_id);

    //根据名称找食物（模糊查找）
    List<Food> getFoodByName(String name);

    //得到所有菜品
    List<Food> getAllDishes();
    //得到所有食品
    List<Food> getAllMeal();

    //得到某个菜系的菜品（广东菜）
    List<Food> getDishesByType(String type);

    //得到某个类别的食品（肉类，蛋类，奶类） -LIKE
    List<Food> getMealByCategory(String category);

    //得到具有某种特征的食物，如高脂肪，高蛋白质,高碳水； value代表阈值
    List<Food> getSpecialFood(String special,Integer value);

    //批量上传食品(事务级)
    boolean upload();

    //替换某一食品
    boolean updateByName(String foodName);

    //根据一张图片识别食物
    List<FoodReg> recognizePicture(Byte[] picture);

    //提供饮食建议（根据用户个人信息和根据图片识别出的食品）
    String advice(Account account, String foodName);

}
