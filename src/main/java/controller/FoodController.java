package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.DietService;
import service.FoodService;

@RestController
@RequestMapping("/food")
public class FoodController {
    @Autowired
    private FoodService foodService;

    @RequestMapping("/list")
    public Object getAllFood(){
        return foodService.getAllFood();
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

    //得到某个类别的食品（肉类）
    @RequestMapping("/meal/category")
    public Object getMealByCategory(@RequestParam String category){
        return foodService.getMealByCategory(category);
    }

    @PostMapping("/reg")
    //TODO 完善图片识别
    public Object recognizePicture(@RequestParam Byte[] picture){

        return foodService.recognizePicture(picture);
    }


}
