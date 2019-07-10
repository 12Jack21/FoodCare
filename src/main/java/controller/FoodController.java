package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import po.Food;
import service.DietService;
import service.FoodService;
import vo.FoodPage;
import vo.FoodRank;
import vo.FoodReg;
import vo.Page;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/food")
public class FoodController {
    @Autowired
    private FoodService foodService;

    @RequestMapping("/{food_id}")
    public Food getFoodById(@PathVariable int food_id){
        return foodService.getFoodById(food_id);
    }

    @RequestMapping("/map/{food_id}")
    public Object getFoodMapById(@PathVariable int food_id){
        return foodService.getFoodMapById(food_id);
    }

    @RequestMapping("/list")
    public Object getAllFood(){
        return foodService.getAllFood();
    }

    @RequestMapping("/list/limit")
    public Object getAllFoodLimit(@RequestBody Page page, ModelMap map){

        return foodService.getAllFoodLimit(page);
    }

    @RequestMapping("/list/frequent")
    public List<Food> getFrequentFood(){
        return foodService.getFrequentFood();
    }

    @RequestMapping("/list/frequent/limit")
    public FoodPage getFrequentFoodLimit(@RequestBody Page page){
        return foodService.getFrequentFoodLimit(page);
    }

    @RequestMapping("/dishes")
    public Object getDishes(){
        return foodService.getAllDishes();
    }

    @RequestMapping("/meal")
    public Object getMeal(){
        return foodService.getAllMeal();
    }

    //根据食物名进行查找
    //TODO 可以分为菜品 和 食品
    @RequestMapping("/search")
    public Object getFoodByName(@RequestParam String name){
        return foodService.getFoodByName(name);
    }

//    //根据食物名进行查找
//    @RequestMapping("/search/limit")
//    public Object getFoodByNameLimit(@RequestParam String name,Page page){
//        return foodService.getFoodByNameLimit(page,name);
//    }

    //根据食物名进行查找
    @RequestMapping("/search/limit")
    public Object getFoodByNameLimit(@RequestParam String name,@RequestParam int start){
        return foodService.getFoodByNameLimit(start,name);
    }

    @RequestMapping("/label/list")
    public Object getAllLabel(){
        return foodService.getAllLabel();
    }

    @RequestMapping("/label/{label_id}")
    public Object getFoodByLabel(@PathVariable int label_id){
        return foodService.getFoodByLabel(label_id);
    }

    //得到某种菜系的菜品
    @RequestMapping("/dishes/type")
    public Object getDishesByType(@RequestParam String type){
        return foodService.getDishesByType(type);
    }

//    //分页查询菜系的菜品
//    @RequestMapping("/dishes/type/limit")
//    public FoodPage getDishesByTypeLimit(@RequestParam String type,Page page){
//        return foodService.getFoodByTypeLimit(page, type);
//    }

    //分页查询菜系的菜品
    @RequestMapping("/dishes/type/limit")
    public FoodPage getDishesByTypeLimit(@RequestParam String type,@RequestParam int start){
        return foodService.getFoodByTypeLimit(start, type);
    }

    //得到某个类别的食品（肉类）
    @RequestMapping("/meal/category")
    public Object getMealByCategory(@RequestParam String category){
        return foodService.getMealByCategory(category);
    }

//    //分页查询菜系的菜品
//    @RequestMapping("/meal/category/limit")
//    public FoodPage getMealByCategoryLimit(@RequestParam String category,Page page){
//        return foodService.getFoodByCategoryLimit(page, category);
//    }

    //分页查询菜系的菜品
    @RequestMapping("/meal/category/limit")
    public FoodPage getMealByCategoryLimit(@RequestParam String category,@RequestParam int start){
        return foodService.getFoodByCategoryLimit(start, category);
    }

    @CrossOrigin //跨域访问的注解
    @PostMapping("/reg1")
    //TODO 完善图片识别
    public Object recognizePicture1(@RequestParam("img") byte[] picture){
        System.out.println("Connecting...");
        return foodService.recognize(picture);
    }

    @CrossOrigin
    @RequestMapping("/reggg")
    //本地测试时使用
    public Object recognizeName(@RequestParam("img") MultipartFile file) throws IOException {

        String filename = file.getOriginalFilename();
        System.out.println(filename);

        byte[] bs = file.getBytes();

        return foodService.recognize(bs);
    }

    @CrossOrigin
    @RequestMapping("/reg")
    //完整识别单物体的图片
    public Object recognizePicture(@RequestParam("img") MultipartFile file) throws IOException {

        String filename = file.getOriginalFilename();
        System.out.println(filename);

        byte[] bs = file.getBytes();

        return foodService.recognizePicture(bs);
    }

    @CrossOrigin
    @RequestMapping("/multiple/reg")
    // 识别多物体的图片
    public Object multipleReg(@RequestParam("img") MultipartFile file) throws IOException {

        return foodService.multipleReg(file);
    }

    /**
     * 以下为测试安卓的网络请求框架用的
     * */

    @CrossOrigin
    @RequestMapping("/text")
    //测试用的 修饰字符串
    public Object stringgg(@RequestParam("text")String text){
        String newText = "%%%%%%" + text + "%%%%%%%";
        System.out.println(newText + "Connect....................");

        return newText;
    }

    @CrossOrigin
    @RequestMapping("/json")
    //测试用的 返回json
    public Object json(@RequestParam("text")String text){
        String newText = "%%%%%%" + text + "%%%%%%%";
        return new FoodRank(newText,0.14f);
    }


}
