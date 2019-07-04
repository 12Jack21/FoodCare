package service.Imp;

import MyUtil.ModelPredict;
import dao.FoodDAO;
import dao.FoodLabelDAO;
import dao.LabelDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import po.Account;
import po.Food;
import po.FoodLabel;
import po.Label;
import service.FoodService;
import vo.FoodPage;
import vo.FoodRank;
import vo.FoodReg;
import vo.Page;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FoodServiceImp implements FoodService {
    @Autowired
    private FoodDAO foodDAO;
    @Autowired
    private FoodLabelDAO foodLabelDAO;
    @Autowired
    private LabelDAO labelDAO;

    @Override
    public Food getFoodById(int id) {
        return foodDAO.getById(id);
    }

    @Override
    public List<Food> getAllFood() {
        return foodDAO.getAllFood();
    }

    @Override
    public FoodPage getAllFoodLimit(Page page) {
        FoodPage foodPage = new FoodPage();
        List<Food> foods = foodDAO.getAllFoodLimit(page.getStart(),page.getPageSize());

        //到达最后一页了
        if (foods.toArray().length < page.getPageSize())
            page.setEnd(true);
        foodPage.setFoods(foods);
        foodPage.setPage(page);
        
        return foodPage;
    }

    @Override
    public List<Food> getCommonFood() {
        return null;
    }

    @Override
    public List<Label> getAllLabel() {
        return labelDAO.getAllLabel();
    }

    @Override
    public List<Food> getFoodByLabel(int label_id) {
        List<FoodLabel> foodLabels = foodLabelDAO.getFoodByLabelId(label_id);
        List<Food> foods = new ArrayList<>();
        for (FoodLabel f : foodLabels)
            foods.add(f.getFood());
        return foods;
    }

    @Override
    public List<Food> getFoodByName(String name) {
        System.out.println("getByName....");
        System.out.println("%" + name + "%")
        ;
        return foodDAO.getByName("%" + name + "%");
    }

    @Override
    public FoodPage getFoodByNameLimit(Page page, String name) {
        return null;
    }

    @Override
    public List<Food> getAllDishes() {
        return foodDAO.getByGroup(0);
    }

    @Override
    public List<Food> getAllMeal() {
        return foodDAO.getByGroup(1);
    }

    @Override
    public List<Food> getDishesByType(String type) {
        System.out.println("getByName....");
        System.out.println('%' + type + "%");

        StringBuilder sb = new StringBuilder("%");
        sb.append(type);
        sb.append("%");
        System.out.println("StringBuilder.... " + sb.toString());
        return foodDAO.getByType(type);
    }

    @Override
    public FoodPage getFoodByTypeLimit(Page page, String type) {
        return null;
    }

    @Override
    public List<Food> getMealByCategory(String category) {
        return foodDAO.getByCategory(category);
    }

    @Override
    public FoodPage getFoodByCategoryLimit(Page page, String category) {
        return null;
    }

    @Override
    public List<Food> getSpecialFood(String special, Integer value) {
        //TODO 字符串匹配，匹配到谁就以它为标准进行设置最大值的查询
        return null;
    }

    @Override
    @Transactional
    public boolean upload() {
        return false;
    }

    @Override
    public boolean updateByName(String foodName) {
        return false;
    }

    @Override
    public List<FoodReg> recognizePicture(Byte[] picture) {


        return null;
    }

    @Override
    public List<FoodRank> recognize(byte[] picture){
        return ModelPredict.recognize(picture);
    }

    @Override
    public String advice(Account account, String foodName) {
        return null;
    }

}
