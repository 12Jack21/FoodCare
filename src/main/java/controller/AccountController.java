package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import po.Account;
import po.DietDetail;
import service.AccountService;
import service.DietService;

import java.util.List;

import static MyUtil.MyConvertor.*;

@RestController
@RequestMapping("/acc")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private DietService dietService;


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
        if (accountService.getAccByUser(user) != null)
            //账户已存在
            return 0;
        else
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

    @RequestMapping("/picture")
    //TODO 更改Byte类型
    public Object changePic(@RequestParam("account_id") int account_id, @RequestParam("picture") Byte[] picture) {
        return accountService.updatePic(account_id, picture);
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


}
