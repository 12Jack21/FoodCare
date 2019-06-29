package MyUtil;

import org.springframework.beans.BeanUtils;
import vo.CompetitionVO;
import vo.RefereeVO;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class MyConvertor {

    public static RefereeVO convertRef(Referee referee){
        RefereeVO rVO = new RefereeVO();
        BeanUtils.copyProperties(referee,rVO);

        rVO.setTeam(referee.getTeam().getName());

        return rVO;
    }

    public static CompetitionVO convertComp(Competition competition){
        CompetitionVO c = new CompetitionVO();
        c.setId(competition.getId());
        c.setProject(competition.getProject());
        if(competition.getDate() != null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            c.setDate(sdf.format(competition.getDate()));
        }
        c.setPlace(competition.getPlace());

        if(competition.getSexgroup() == 0)
            c.setSexgroup("女子");
        else
            c.setSexgroup("男子");

        int ageGroup = competition.getAgegroup();
        if(ageGroup == 0)
            c.setAgegroup("7-8岁");
        else if (ageGroup == 1)
            c.setAgegroup("9-10岁");
        else
            c.setAgegroup("11-12岁");

        if(competition.getType() == 0)
            c.setType("初赛");
        else
            c.setType("决赛");

        if(competition.getIsEnd() == 0)
            c.setIsEnd("否");
        else
            c.setIsEnd("是");

        return c;
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
