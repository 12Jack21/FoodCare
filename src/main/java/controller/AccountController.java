package controller;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import po.Account;
import po.DietDetail;
import po.Menu;
import po.MenuItem;
import service.AccountService;
import service.DietService;
import service.MenuService;

import java.io.File;
import java.io.IOException;
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

//            // 如果后缀为mp3的，一上传文件原名保存，否则以时间戳文文件名进行保存
//            if (!suffix.equals("jpg")) {
//                //FileUtils.copyInputStreamToFile(multipartFile.getInputStream(),new File(path + "//upload//", System.currentTimeMillis() + "." + suffix));
//                picture.transferTo(new File(path + "//WEB-INF//image_user//", System.currentTimeMillis() + "." + suffix));
//            } else {
//                FileUtils.copyInputStreamToFile(picture.getInputStream(), new File(path + "//WEB-INF//image_user//", filename));
//            }

            FileUtils.copyInputStreamToFile(picture.getInputStream(), new File(path + "//WEB-INF//image_user//", account_id + "."+suffix));


        }

        return accountService.updatePic(account_id,suffix);
    }

    @PostMapping("/updateInfo")
    public Object changeInfo(@RequestParam Account account) {
        return accountService.updateInfo(account);
    }


    @RequestMapping("/diet/add")
    //应该用不到此方法
    public Object addDiet(@RequestParam("account_id") int account_id, @RequestParam("group") int group) {
        return dietService.addDiet(group, account_id);
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


    /**
     * 以下为MenuService相关的 URL 处理
     * */

    @RequestMapping("/menu/list")
    public List<Menu> getMenuByAcc(@RequestParam int account_id){
        return menuService.getMenuByAccId(account_id);
    }

    @RequestMapping("/menu/add")
    public Object addMenu(@RequestParam Menu menu){
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
    public Object addMenuItem(@RequestParam MenuItem menuItem){
        return menuService.addItem(menuItem);
    }

    @RequestMapping("/menuItem/delete")
    public Object deleteMenuItem(@RequestParam int menuItem_id){
        return menuService.deleteItem(menuItem_id);
    }
}
