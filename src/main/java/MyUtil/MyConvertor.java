package MyUtil;

import po.Account;
import po.Food;
import vo.AccountLog;
import vo.FoodReg;

import java.sql.Timestamp;

public class MyConvertor {

    //转换成识别图片后显示食物以及概率
    public static FoodReg toFoodReg(Food food,Double probability){

        return new FoodReg(food, probability);
    }

    //转换成带 登陆判断 的用户对象
    public static AccountLog toAccountLog(Account account,boolean canLogin){
        return new AccountLog(account,canLogin);
    }

    //将数字转换成 年龄组别的字符串
    public static String ageGroupString(int ageGroup){
        if(ageGroup == 0)
            return "7-8岁组";
        else if(ageGroup == 1)
            return "9-10岁组";
        else
            return "11-12岁组";
    }

    public static String groupString(int sex,int age){
        if ((sex != 0 && sex != 1) || !(age >=7 && age<=12))
            return null;

        String group = "";
        if (sex == 0)
            group += "女子";
        else
            group += "男子";
        if (age <= 8)
            group += "7-8岁年龄组";
        else if(age <= 10)
            group += "9-10岁年龄组";
        else
            group += "11-12岁年龄组";

        return group;

    }

    //将数字转换成 性别字符串
    public static String sexString(int sex){
        if(sex == 0)
            return "女";
        else
            return "男";
    }

    //比赛项目 转换成字符串（单杠，跳马等）


    //比赛类型 转换成字符串(初赛、决赛类型)
    public static String comptypeString(int comptype){
        if(comptype == 0)
            return "初赛";
        else
            return "决赛";
    }

    //裁判类型转变成字符串
    public static String reftypeString(int reftype){
        if(reftype == 0)
            return "普通裁判";
        else
            return "主裁判";
    }

    //DateTime转变成 TimeStamp, 然后再通过删去TimeStamp小数点来得到显示在界面中的时间
    public static String dateString(Timestamp timestamp){
        String s = timestamp.toString();
        int index = s.lastIndexOf(".");
        return s.substring(0,index);
    }
}