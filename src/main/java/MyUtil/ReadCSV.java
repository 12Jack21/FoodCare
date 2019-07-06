package MyUtil;

import com.csvreader.CsvReader;

import javax.transaction.Transaction;
import java.io.File;
import java.nio.charset.Charset;
import java.sql.*;

public class ReadCSV {

    public static void main(String[] args) {
        readDishes("F:\\Study\\FourthTerm\\实训\\薄荷网官网2810种食物csv\\csv最终版\\csv最终版（mid）\\dishes");

        readMeal("F:\\Study\\FourthTerm\\实训\\薄荷网官网2810种食物csv\\csv最终版\\csv最终版（mid）\\others");

    }

    //用于过滤 主料、辅料、做法等
    public static String filterString(String str) {
        String[] s = str.split("\"\"");

        StringBuilder sb = new StringBuilder();
        for (int n = 0; n < s.length; n++) {
            if (n != 0 && n != s.length - 1)
                sb.append(s[n].trim());
        }

        return sb.toString();
    }

    //用于过滤 做法
    public static String filterPractice(String str) {
        String[] s = str.split("\", \"");//包括逗号的

        StringBuilder sb = new StringBuilder();
        for (int n = 0; n < s.length; n++) {
            if (n != 0 && n != s.length - 1)
                sb.append(s[n].trim());
        }

        return sb.toString();
    }
    //用于过滤 烹饪工艺
    public static String filterCook(String str) {
        String[] s = str.split("\"");//包括双引号的

        StringBuilder sb = new StringBuilder();
        for (int n = 0; n < s.length; n++) {
            if (s[n].equals("\"\"") || s[n].equals("\""))
                continue;
            sb.append(s[n].trim());
        }

        return sb.toString();
    }

    //用于过滤蛋白质、纤维素等的横杠 '-'
    public static Double filterDouble(String str) {
        if (str.equals("一") || str.equals(""))
            return null;
        return Double.parseDouble(str);
    }

    public static void readDishes(String dirPath) {

        Connection conn = null;
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/foodcare?useSSL=false&characterEncoding=utf-8&serverTimezone=GMT%2B8";
        CsvReader read = null;

        Savepoint sp = null;
        try {
            Class.forName(driver);

            conn = DriverManager.getConnection(url, "root", "qaz147");

            sp = conn.setSavepoint();

            //得到该文件夹下的所有文件名
            File dir = new File(dirPath);

            File[] files = dir.listFiles();

            //遍历读取文件夹的文件
            for (int n = 0; n < files.length; n++) {

                // 创建CSV读对象 例如:CsvReader(文件路径，分隔符，编码格式);  
                read = new CsvReader(dirPath + "\\" + files[n].getName(), ',', Charset.forName("UTF-8"));

                System.out.println("CSV file: " + files[n].getName());//打印打开文件的名字

                // 跳过第一行（标题） 如果需要，可以忽略  
                read.readHeaders();

//            String sql = "insert into test(name,quantity) values(?,?)";

//            String sql = "INSERT INTO food(`group`,`name`,picture,category,`type`,heat,tanshui," +
//                    "        fat,protein,cellulose,vitaminA,vitaminC,vitaminE,carotene,liuan,hehuang,yansuan,cholesterol," +
//                    "        mei,gai,tie,xin,tong,meng,jia,lin,na,xi,measure,evaluate,ingredient,excipient,practice)" +
//                    "        VALUES (?,?,?,?,?,?,?," + "?,?,?,?,?,?,?,?,?,?,?," +"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                //菜品的插入 SQL语句 ,存图片路径
                String sql = "INSERT INTO food(`group`,`name`,`type`,heat,tanshui," +
                        "        fat,protein,cellulose,measure,ingredient,excipient,seasoning,practice,cook,picture_mid,picture_high,light)" +
                        "        VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


                PreparedStatement ps = conn.prepareStatement(sql);

                while (read.readRecord()) {
                    //显示从csv文件中读出来的数据
//                    System.out.println(read.getRawRecord());

                    String lineTxt = read.getRawRecord();

                    String csvSplitBy = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"; //用于过滤两边的 [ ]的正则表达式
//                String[] columns = lineTxt.split(",");
                    String[] columns = lineTxt.split(csvSplitBy);

                    //名称	分类	烹饪工艺	红绿灯地址 预览图地址	热量	碳水化合物	脂肪	蛋白质	纤维素	度量单位	主料	辅料	调料	做法	相关食物 拼音

//                ps.setObject(5,filterDouble(columns[5]));
                    ps.setInt(1, 0);
                    ps.setString(2, columns[0]);
                    ps.setString(3, columns[1]);
                    ps.setInt(4, Integer.parseInt(columns[5]));
                    ps.setObject(5, filterDouble(columns[6]));
                    ps.setObject(6, filterDouble(columns[7]));
                    ps.setObject(7, filterDouble(columns[8]));
                    ps.setObject(8, filterDouble(columns[9]));

                    ps.setString(9, filterString(columns[10]));
                    ps.setString(10, filterString(columns[11]));
                    ps.setString(11, filterString(columns[12]));
                    ps.setString(12, filterString(columns[13]));
                    ps.setString(13, filterPractice(columns[14])); //practice

                    //图片路径
                    ps.setString(15, "/img_mid.action/" + columns[16] + ".jpg");
                    ps.setString(16, "/img_high.action/" + columns[16] + ".jpg");
                    ps.setString(17, "/img_light.action/" + columns[16] + ".png");

                    //烹饪工艺
                    ps.setString(14, filterCook(columns[2]));

                    try {
                        ps.executeUpdate();
                    }catch (SQLIntegrityConstraintViolationException e){
                        System.out.println("主键冲突：" + columns[0] + "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                    }

                }


            }

            conn.commit();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            try {
                conn.rollback(sp);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                conn.close();
                read.close();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                System.out.println("读取文件出错");
                e.printStackTrace();
            }
        }

    }

    public static void readMeal(String dirPath) {

        Connection conn = null;
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/foodcare?useSSL=false&characterEncoding=utf-8&serverTimezone=GMT%2B8";
        CsvReader read = null;

        Savepoint sp = null;
        try {
            Class.forName(driver);

            conn = DriverManager.getConnection(url, "root", "qaz147");

            sp = conn.setSavepoint();

            //得到该文件夹下的所有文件名
            File dir = new File(dirPath);

            File[] files = dir.listFiles();

            //遍历读取文件夹的文件
            for (int n = 0; n < files.length; n++) {

                // 创建CSV读对象 例如:CsvReader(文件路径，分隔符，编码格式);  
                read = new CsvReader(dirPath + "\\" + files[n].getName(), ',', Charset.forName("UTF-8"));

                System.out.println("CSV file: " + files[n].getName());//打印打开文件的名字

                // 跳过第一行（标题） 如果需要，可以忽略  
                read.readHeaders();

//            String sql = "insert into test(name,quantity) values(?,?)";

                //食品的SQL语句，保存图片路径 ,共31项
                String sql = "INSERT INTO food(`group`,`name`,category,heat,tanshui," +
                        "        fat,protein,cellulose,vitaminA,vitaminC,vitaminE,carotene,liuan,hehuang,yansuan,cholesterol," +
                        "        mei,gai,tie,xin,tong,meng,jia,lin,na,xi,measure,evaluate,picture_mid,picture_high,light)" +
                        "        VALUES (?,?,?,?,?,?," + "?,?,?,?,?,?,?,?,?,?,?," + "?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


                PreparedStatement ps = conn.prepareStatement(sql);

                while (read.readRecord()) {
//                    System.out.println(read.getRawRecord());

                    String lineTxt = read.getRawRecord();

                    String csvSplitBy = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"; //用于过滤两边的 [ ]的正则表达式

                    String[] columns = lineTxt.split(csvSplitBy);

//名称	分类	评价	红绿灯地址 预览图地址 热量 碳水化合物	脂肪	蛋白质 纤维素	维生素A	维生素C 维生素E 胡萝卜素 硫胺素	核黄素 烟酸 胆固醇	镁	钙	铁	锌	铜	锰	钾	磷	钠	硒	度量单位 相关食物 拼音

//                ps.setObject(5,filterDouble(columns[5]));
                    ps.setInt(1, 1); //设置食物的组别，食品即为 1
                    ps.setString(2, columns[0]);
                    ps.setString(3, columns[1]);
                    ps.setInt(4, Integer.parseInt(columns[5]));  //heat
                    ps.setObject(5, filterDouble(columns[6]));
                    ps.setObject(6, filterDouble(columns[7]));
                    ps.setObject(7, filterDouble(columns[8]));
                    ps.setObject(8, filterDouble(columns[9]));//纤维素

                    ps.setObject(9, filterDouble(columns[10]));
                    ps.setObject(10, filterDouble(columns[11]));
                    ps.setObject(11, filterDouble(columns[12]));
                    ps.setObject(12, filterDouble(columns[13]));
                    ps.setObject(13, filterDouble(columns[14]));
                    ps.setObject(14, filterDouble(columns[15]));
                    ps.setObject(15, filterDouble(columns[16]));
                    ps.setObject(16, filterDouble(columns[17]));
                    ps.setObject(17, filterDouble(columns[18]));//mei
                    ps.setObject(18, filterDouble(columns[19]));
                    ps.setObject(19, filterDouble(columns[20]));
                    ps.setObject(20, filterDouble(columns[21]));
                    ps.setObject(21, filterDouble(columns[22]));
                    ps.setObject(22, filterDouble(columns[23]));
                    ps.setObject(23, filterDouble(columns[24]));
                    ps.setObject(24, filterDouble(columns[25]));
                    ps.setObject(25, filterDouble(columns[26]));
                    ps.setObject(26, filterDouble(columns[27])); //xi

                    ps.setString(27, filterString(columns[28]));//measure
                    ps.setString(28, filterString(columns[2]));//evaluate

                    //图片路径
                    ps.setString(29, "/img_mid.action/" + columns[30] + ".jpg");
                    ps.setString(30, "/img_high.action/" + columns[30] + ".jpg");
                    ps.setString(31, "/img_light.action/" + columns[30] + ".png");

                    try {

                        ps.executeUpdate();
                    }catch (SQLIntegrityConstraintViolationException e){
                        System.out.println("主键冲突：" + columns[0] + "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                    }

                }
            }
            conn.commit();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            try {
                conn.rollback(sp);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                conn.close();
                read.close();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                System.out.println("读取文件出错");
                e.printStackTrace();
            }
        }

    }


}

