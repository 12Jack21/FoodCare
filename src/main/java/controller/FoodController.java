package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import po.Food;
import service.DietService;
import service.FoodService;
import vo.FoodRank;
import vo.FoodReg;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

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

    @CrossOrigin //跨域访问的注解
    @PostMapping("/reg")
    //TODO 完善图片识别
    public Object recognizePicture(@RequestParam("img") Byte[] picture){
        System.out.println("Connecting...");
        return foodService.recognizePicture(picture);
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
