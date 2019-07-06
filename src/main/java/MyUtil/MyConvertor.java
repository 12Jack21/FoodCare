package MyUtil;

import po.Account;
import po.Food;
import vo.AccountLog;
import vo.FoodReg;

import java.sql.Timestamp;

public class MyConvertor {

    //转换成带 登陆判断 的用户对象
    public static AccountLog toAccountLog(Account account,boolean canLogin){
        return new AccountLog(account,canLogin);
    }

    //将数字转换成 性别字符串
    public static String sexString(int sex){
        if(sex == 0)
            return "女";
        else
            return "男";
    }

    //DateTime转变成 TimeStamp, 然后再通过删去TimeStamp小数点来得到显示在界面中的时间
    public static String dateString(Timestamp timestamp){
        String s = timestamp.toString();
        int index = s.lastIndexOf(".");
        return s.substring(0,index);
    }

}
