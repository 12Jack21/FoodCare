package service.Imp;

import MyUtil.ModelPredict;
import MyUtil.MultipleReg;
import dao.FoodDAO;
import dao.FoodLabelDAO;
import dao.LabelDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import po.Account;
import po.Food;
import po.FoodLabel;
import po.Label;
import service.FoodService;
import vo.*;

import java.util.ArrayList;
import java.util.List;
import static MyUtil.StrToJson.*;

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
    public FoodMap getFoodMapById(int id) {
        Food food = foodDAO.getById(id);

        FoodMap foodMap = new FoodMap();
        foodMap.setFood(food);

        if(food.getIngredient() != null)
            foodMap.setIngredients(materialToMap(food.getIngredient()));
        if (food.getExcipient() != null)
            foodMap.setExcipients(materialToMap(food.getExcipient()));
        if (food.getSeasoning() != null)
            foodMap.setSeasoning(materialToMap(food.getSeasoning()));
        if (food.getPractice() != null && !food.getPractice().equals("") && !food.getPractice().equals(" "))
            foodMap.setPractice(zuofaToMap(food.getPractice()));

        return foodMap;
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
        return foodDAO.getByName(name);
    }

//    @Override
//    public FoodPage getFoodByNameLimit(Page page, String name) {
//        FoodPage foodPage = new FoodPage();
//        List<Food> foods = null;
//
//        //判断来过滤 引号
//        if (name.contains("\"")){
//            String[] s = name.split("\"");
//            foods = foodDAO.getByCategoryLimit(page.getStart(),page.getPageSize(),s[1]);
//        }else
//            foods = foodDAO.getByCategoryLimit(page.getStart(),page.getPageSize(),name);
//
//        //到达最后一页了
//        if (foods.toArray().length < page.getPageSize())
//            page.setEnd(true);
//
//        page.setStart(page.getStart() + page.getPageSize());
//        foodPage.setFoods(foods);
//        foodPage.setPage(page);
//
//        return foodPage;
//    }

    @Override
    public FoodPage getFoodByNameLimit(int start, String name) {
        FoodPage foodPage = new FoodPage();
        List<Food> foods;

        Page page = new Page();
        page.setStart(start);

        //判断来过滤 引号
        if (name.contains("\"")){
            String[] s = name.split("\"");
            foods = foodDAO.getByNameLimit(page.getStart(),page.getPageSize(),s[1]);
        }else
            foods = foodDAO.getByNameLimit(page.getStart(),page.getPageSize(),name);

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

//    @Override
//    public FoodPage getFoodByTypeLimit(Page page, String type) {
//        FoodPage foodPage = new FoodPage();
//        List<Food> foods = null;
//
//        //调试用
//        System.out.println("更新前页数的开始位置：" + page.getStart() + "------------------------------------------------------");
//
//        //判断来过滤 引号
//        if (type.contains("\"")){
//            String[] s = type.split("\"");
//            foods = foodDAO.getByTypeLimit(page.getStart(),page.getPageSize(),s[1]);
//        }else
//            foods = foodDAO.getByTypeLimit(page.getStart(),page.getPageSize(),type);
//
//        //到达最后一页了
//        if (foods.toArray().length < page.getPageSize())
//            page.setEnd(true);
//
//        page.setStart(page.getStart() + page.getPageSize());
//
//        System.out.println("更新后页数的开始位置：" + page.getStart() + "------------------------------------------------------");
//
//        foodPage.setFoods(foods);
//        foodPage.setPage(page);
//
//        return foodPage;
//    }

    @Override
    public FoodPage getFoodByTypeLimit(int start, String type) {
        FoodPage foodPage = new FoodPage();
        List<Food> foods = null;
        Page page = new Page();
        page.setStart(start);

        //调试用
        System.out.println("更新前页数的开始位置：" + page.getStart() + "------------------------------------------------------");

        //判断来过滤 引号
        if (type.contains("\"")){
            String[] s = type.split("\"");
            foods = foodDAO.getByTypeLimit(page.getStart(),page.getPageSize(),s[1]);
        }else
            foods = foodDAO.getByTypeLimit(page.getStart(),page.getPageSize(),type);

        //到达最后一页了
        if (foods.toArray().length < page.getPageSize())
            page.setEnd(true);

        page.setStart(page.getStart() + page.getPageSize());

        System.out.println("更新后页数的开始位置：" + page.getStart() + "------------------------------------------------------");

        foodPage.setFoods(foods);
        foodPage.setPage(page);

        return foodPage;
    }

    @Override
    public List<Food> getMealByCategory(String category) {
        return foodDAO.getByCategory(category);
    }

//    @Override
//    public FoodPage getFoodByCategoryLimit(Page page, String category) {
//        FoodPage foodPage = new FoodPage();
//        List<Food> foods = null;
//        //判断来过滤 引号
//        if (category.contains("\"")){
//            String[] s = category.split("\"");
//            foods = foodDAO.getByCategoryLimit(page.getStart(),page.getPageSize(),s[1]);
//        }else
//            foods = foodDAO.getByCategoryLimit(page.getStart(),page.getPageSize(),category);
//
//        //到达最后一页了
//        if (foods.toArray().length < page.getPageSize())
//            page.setEnd(true);
//
//        page.setStart(page.getStart() + page.getPageSize());
//        foodPage.setFoods(foods);
//        foodPage.setPage(page);
//
//        return foodPage;
//    }

    @Override
    public FoodPage getFoodByCategoryLimit(int start, String category) {
        FoodPage foodPage = new FoodPage();
        List<Food> foods = null;
        Page page = new Page();
        page.setStart(start);

        //判断来过滤 引号
        if (category.contains("\"")){
            String[] s = category.split("\"");
            foods = foodDAO.getByCategoryLimit(page.getStart(),page.getPageSize(),s[1]);
        }else
            foods = foodDAO.getByCategoryLimit(page.getStart(),page.getPageSize(),category);

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
            //过滤标签名（带index和冒号的）
            String[] s = f.getFoodname().split(":");
            foods = foodDAO.getByName(s[s.length - 1]);


            //计算平均热量
            for (Food food : foods){
                heat += food.getHeat();
            }
            //平均热量
            foodReg = new FoodReg(foods,f.getProbability(),s[s.length - 1],foods.toArray().length == 0 ? 0 : (heat/foods.toArray().length));

            regs.add(foodReg);

            heat = 0;
        }
        return regs;
    }

    @Override
    public List<FoodPosition> multipleReg(MultipartFile file) {
        return MultipleReg.MultiReg(file);
    }

    @Override
    public String advice(Account account, String foodName) {
        return null;
    }

}
