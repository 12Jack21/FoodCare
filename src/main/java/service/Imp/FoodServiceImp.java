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

        page.setStart(page.getStart() + page.getPageSize());
        foodPage.setFoods(foods);
        foodPage.setPage(page);

        return foodPage;
    }

    @Override
    public List<Food> getFrequentFood() {
        return foodDAO.getFrequentFood();
    }

    @Override
    public FoodPage getFrequentFoodLimit(Page page) {
        FoodPage foodPage = new FoodPage();
        List<Food> foods = foodDAO.getFrequentFoodLimit(page.getStart(),page.getPageSize());

        //到达最后一页了
        if (foods.toArray().length < page.getPageSize())
            page.setEnd(true);

        page.setStart(page.getStart() + page.getPageSize());
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
        FoodPage foodPage = new FoodPage();
        List<Food> foods = foodDAO.getByNameLimit(page.getStart(),page.getPageSize(),name);

        //到达最后一页了
        if (foods.toArray().length < page.getPageSize())
            page.setEnd(true);

        page.setStart(page.getStart() + page.getPageSize());
        foodPage.setFoods(foods);
        foodPage.setPage(page);

        return foodPage;
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
        FoodPage foodPage = new FoodPage();
        List<Food> foods = foodDAO.getByTypeLimit(page.getStart(),page.getPageSize(),type);

        //到达最后一页了
        if (foods.toArray().length < page.getPageSize())
            page.setEnd(true);

        page.setStart(page.getStart() + page.getPageSize());
        foodPage.setFoods(foods);
        foodPage.setPage(page);

        return foodPage;
    }

    @Override
    public List<Food> getMealByCategory(String category) {
        return foodDAO.getByCategory(category);
    }

    @Override
    public FoodPage getFoodByCategoryLimit(Page page, String category) {
        FoodPage foodPage = new FoodPage();
        List<Food> foods = foodDAO.getByCategoryLimit(page.getStart(),page.getPageSize(),category);

        //到达最后一页了
        if (foods.toArray().length < page.getPageSize())
            page.setEnd(true);

        page.setStart(page.getStart() + page.getPageSize());
        foodPage.setFoods(foods);
        foodPage.setPage(page);

        return foodPage;
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
    //识别图片的主函数
    public List<FoodRank> recognize(byte[] picture){
        return ModelPredict.recognize(picture);
    }


    @Override
    //识别图片 主函数
    public List<FoodReg> recognizePicture(byte[] picture){
        List<FoodRank> ranks = ModelPredict.recognize(picture);

        List<FoodReg> regs = new ArrayList<>();
        FoodReg foodReg = null;
        List<Food> foods = null;

        int heat = 0;

        for (FoodRank f : ranks){
            foods = foodDAO.getByName(f.getFoodname());

            //计算平均热量
            for (Food food : foods){
                heat += food.getHeat();
            }

            foodReg = new FoodReg(foods,f.getProbability(),f.getFoodname(),heat);

            regs.add(foodReg);

            heat = 0;
        }
        return regs;
    }

    @Override
    public String advice(Account account, String foodName) {
        return null;
    }

}
