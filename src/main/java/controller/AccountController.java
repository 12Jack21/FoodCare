package controller;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import po.*;
import service.AccountService;
import service.DietService;
import service.MenuService;
import service.SportService;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import static MyUtil.MyConvertor.*;

@RestController
@RequestMapping("/acc")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private DietService dietService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private SportService sportService;

    //确认账户是否存在(在输入用户名时确定)
    @PostMapping("/exists")
    public Object exists(@RequestParam String user) {
        return accountService.getAccByUser(user) != null;
    }

    //已经确认账户存在了
    @PostMapping("/login")
    public Object login(@RequestParam("user") String user, @RequestParam("password") String password) {
        if (accountService.canLogin(user, password))
            return toAccountLog(accountService.getAccByUser(user), true);
        else
            return toAccountLog(null, false);
    }

    //TODO 加上邮箱
    @PostMapping("/register")
    public Object register(@RequestParam("user") String user, @RequestParam("password") String password) {
        return accountService.register(user, password);
    }

    @PostMapping("/info")
    public Object getInfo(@RequestParam int account_id) {
        return accountService.getAccById(account_id);
    }

    @PostMapping("/cancel")
    public Object cancel(@RequestParam int account_id) {
        return accountService.cancel(account_id);
    }

    @RequestMapping("/pic")
    //TODO 返回用户图像的图片,先用byte
    public Object getPicture(@RequestParam("img_path")String img_path){
        return null;
    }

    @RequestMapping("/picture")
    //TODO 更改Byte类型
    public Object changePic(@RequestParam("account_id") int account_id,
                            @RequestParam("picture")MultipartFile picture) throws IOException {

        //获取Web根目录
        String path = System.getProperty("myWeb.root");
        String suffix = null;

        //文件非空
        if (!picture.isEmpty()) {
            // 获取到源文件名
            String filename = picture.getOriginalFilename();
            // 获取文件的后缀名
            suffix = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();

            FileUtils.copyInputStreamToFile(picture.getInputStream(), new File(path + "//WEB-INF//image_user//", account_id + "."+suffix));
        }

        return accountService.updatePic(account_id,suffix);
    }

    @PostMapping("/updateInfo")
    public Object changeInfo(@RequestBody Account account) {
        return accountService.updateInfo(account);
    }


    /**
     * 以下为 diet相关的 URL
     * */

    @RequestMapping("/diet/add")
    //应该用不到此方法
    public Object addDiet(@RequestParam("account_id") int account_id, @RequestParam("group") int group) {
        return dietService.addDiet(group, account_id);
    }

    @RequestMapping("/diet/list")
    public List<Diet> getDietToday(@RequestParam int account_id){
        return dietService.getDietByAccToday(account_id);
    }

//    @RequestMapping("/diet/find")
//    public List<Diet> getDietByDate(@RequestParam int account_id,@DateTimeFormat(pattern="yyyy-MM-dd") Date date){
//        return dietService.getDietByAccDate(account_id, date);
//    }


    @RequestMapping("/diet/find")
    public List<Diet> getDietByDate(@RequestParam int account_id,@RequestParam String date){
        return dietService.getDietByAccDateString(account_id, date);
    }

    @RequestMapping("/dietDetail/list")
    public List<DietDetail> getDietDetailByDiet(@RequestParam int diet_id){
        return dietService.getDetailsByDiet(diet_id);
    }

    @Transactional
    @RequestMapping("/dietDetail/add")
    //方法中可能触发添加 diet
    public Object addDietDetail(@RequestParam("food_id") int food_id, @RequestParam("quantity") int quantity,
                                     @RequestParam("account_id") int account_id, @RequestParam("group") int group) {
        boolean op;
        //判断是否存在 diet
        if (dietService.getDietByAccDateGroup(account_id, group) == null) {
            op = dietService.addDiet(group, account_id);
        }
        op = dietService.addDietDetail(dietService.getDietByAccDateGroup(account_id, group).getId(), food_id, quantity);

        return op;
    }

    @Transactional
    @RequestMapping("/dietDetail/delete")
    //方法中可能触发 删除 diet
    public Object removeDietDetail(@RequestParam("diet_id")int diet_id,@RequestParam("food_id")int food_id){
        //寻找diet有多少个 dietDetail
        List<DietDetail> dietDetails = dietService.getDetailsByDiet(diet_id);
        boolean op;
        if(dietDetails.toArray().length == 1){
            //只有一项，则可以直接删除 diet，数据库级联删除最后一个 dietDetail
            op = dietService.removeDiet(diet_id);
        }else
            op = dietService.removeDietDetail(diet_id, food_id);
        return op;
    }

    @RequestMapping("/dietDetail/update")
    public Boolean updateDietDetail(@RequestParam("diet_id")int diet_id,@RequestParam("food_id")int food_id,@RequestParam("quantity")int quantity){
        return dietService.updateDietDetail(diet_id, food_id, quantity);
    }


    /**
     * 以下为运动相关的 URL
     * */

    @RequestMapping("/sport/list")
    public Object getAllSports(){
        return sportService.getAllSports();
    }

    @RequestMapping("/play/list")
    public Object getPlayByAccDate(@RequestParam int account_id,@RequestParam String date){
        return sportService.getPlayByAccDate(account_id, date);
    }

    @RequestMapping("/play/add")
    public Object addPlay(@RequestBody Play play){
        return sportService.addPlay(play);
    }

    @RequestMapping("/play/delete")
    public Object deletePlay(@RequestParam int account_id,@RequestParam int sport_id,@RequestParam String date){
        return sportService.removePlay(account_id, sport_id,date);
    }

    @RequestMapping("/play/update")
    public Object updatePlay(@RequestBody Play play){
        return sportService.updatePlayObj(play);
    }


    /**
     * 以下为MenuService相关的 URL 处理
     * */

    @RequestMapping("/menu/list")
    public List<Menu> getMenuByAcc(@RequestParam int account_id){
        return menuService.getMenuByAccId(account_id);
    }

    @RequestMapping("/menu/add")
    public Object addMenu(@RequestBody Menu menu){
        return menuService.addMenu(menu);
    }

    @RequestMapping("/menu/delete")
    public Object deleteMenu(@RequestParam int menu_id){
        return menuService.deleteMenu(menu_id);
    }

    @RequestMapping("/menuItem/list")
    public Object getMenuItemByMenu(@RequestParam int menu_id){
        return menuService.getItemById(menu_id);
    }

    @RequestMapping("/menuItem/add")
    public Object addMenuItem(@RequestBody MenuItem menuItem){
        return menuService.addItem(menuItem);
    }

    @RequestMapping("/menuItem/delete")
    public Object deleteMenuItem(@RequestParam int menuItem_id){
        return menuService.deleteItem(menuItem_id);
    }

}
