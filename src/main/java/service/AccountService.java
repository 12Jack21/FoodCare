package service;

import org.springframework.web.multipart.MultipartFile;
import po.Account;
import po.Food;

import java.util.List;

public interface AccountService {

    Account getAccById(int id);

    //检测是否存在该用户
    Account getAccByUser(String user);

    //检测用户是否能登陆
    boolean canLogin(String user,String password);

    //用户注册
    boolean register(String user,String password);

    //用户注销
    boolean cancel(int id);

    //根据路径得到图片
    byte[] getPic(String path);

    //更新头像(根据后缀名)
    boolean updatePic(int id, String suffix);

    //更新个人信息
    boolean updateInfo(Account account);

    //根据用户找到个人所有的标签


    //TODO 更新用户的标签


    //食物推荐
    List<Food> recommend(int account_id,int group);

}
