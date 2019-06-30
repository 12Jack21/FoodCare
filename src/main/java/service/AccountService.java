package service;

import po.Account;

public interface AccountService {

    //检测是否存在该用户
    Account getAccByUser(String user);

    //检测用户是否能登陆
    boolean canLogin(String user,String password);

    //用户注册
    boolean register(String user,String password);

    //用户注销
    boolean cancel(int id);

    //更新头像
    boolean updatePic(int id,Byte[] picture);

    //更新个人信息
    boolean updateInfo(Account account);

    //根据用户找到个人所有的标签


    //更新用户的标签



}
