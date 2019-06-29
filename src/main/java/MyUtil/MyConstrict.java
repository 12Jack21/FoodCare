package MyUtil;
import java.util.regex.Pattern;

//TODO 思考限制条件
public class MyConstrict {

    //身份证限制,验证身份证的位数 ：18位,以及是否为数字
    public static boolean isIDLegal(String ID){
        Pattern pattern = Pattern.compile("[0-9]{18}");
        return pattern.matcher(ID).matches();
    }

    //电话号码限制
    public static boolean isPhoneLegal(String phone){
        Pattern p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
        return p.matcher(phone).matches();
    }

}
