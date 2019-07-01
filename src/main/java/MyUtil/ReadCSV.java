package MyUtil;

import com.csvreader.CsvReader;

import javax.transaction.Transaction;
import java.nio.charset.Charset;
import java.sql.*;

public class ReadCSV {

    public static void main(String[] args) {
        readFile("F:\\Study\\FourthTerm\\实训\\薄荷网官网2810种食物csv\\csv\\dishes\\湖北菜 .csv");
    }

    //用于过滤 主料、辅料、做法等
    public static String filterString(String str){
        String[] s = str.split("\"\"");

        StringBuilder sb = new StringBuilder();
        for (int n = 0;n < s.length;n++){
            if(n != 0 && n != s.length - 1)
                sb.append(s[n]);
        }

        return sb.toString();
    }

    //用于过滤蛋白质、纤维素等的横杠 '-'
    public static Double filterDouble(String str){
        if(str.equals("一") || str.equals(""))
            return null;
        return  Double.parseDouble(str);
    }

    public static void readFile(String filePath) {

        Connection conn = null;
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/foodcare?useSSL=false&characterEncoding=utf-8&serverTimezone=GMT%2B8";
        CsvReader read = null;

        Savepoint sp = null;
        try {
            Class.forName(driver);

            conn = DriverManager.getConnection(url, "root", "qaz147");

            sp = conn.setSavepoint();

            // 创建CSV读对象 例如:CsvReader(文件路径，分隔符，编码格式);  
            read = new CsvReader(filePath, ',', Charset.forName("UTF-8"));

            // 跳过第一行（标题） 如果需要，可以忽略  
            read.readHeaders();

//            String sql = "insert into test(name,quantity) values(?,?)";

//            String sql = "INSERT INTO food(`group`,`name`,picture,category,`type`,heat,tanshui," +
//                    "        fat,protein,cellulose,vitaminA,vitaminC,vitaminE,carotene,liuan,hehuang,yansuan,cholesterol," +
//                    "        mei,gai,tie,xin,tong,meng,jia,lin,na,xi,measure,evaluate,ingredient,excipient,practice)" +
//                    "        VALUES (?,?,?,?,?,?,?," + "?,?,?,?,?,?,?,?,?,?,?," +"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            String sql = "INSERT INTO food(`group`,`name`,`type`,heat,tanshui," +
                    "        fat,protein,cellulose,measure,ingredient,excipient,seasoning,practice,cook)" +
                    "        VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


            PreparedStatement ps = conn.prepareStatement(sql);

            while (read.readRecord()) {
                System.out.println(read.getRawRecord());

                String lineTxt = read.getRawRecord();

                String csvSplitBy = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
//                String[] columns = lineTxt.split(",");
                String[] columns = lineTxt.split(csvSplitBy);

                //名称	分类	烹饪工艺	红绿灯地址	热量	碳水化合物	脂肪	蛋白质	纤维素	度量单位	主料	辅料	调料	做法	相关食物

//                ps.setObject(5,filterDouble(columns[5]));
                ps.setInt(1,0);
                ps.setString(2, columns[0]);
                ps.setString(3,columns[1]);
                ps.setInt(4,Integer.parseInt(columns[4]));
                ps.setObject(5,filterDouble(columns[5]));
                ps.setObject(6,filterDouble(columns[6]));
                ps.setObject(7,filterDouble(columns[7]));
                ps.setObject(8,filterDouble(columns[8]));

                ps.setString(9,filterString(columns[9]));
                ps.setString(10,filterString(columns[10]));
                ps.setString(11,filterString(columns[11]));
                ps.setString(12,filterString(columns[12]));
                ps.setString(13,filterString(columns[13]));
//                ps.setString(9,columns[9]);
//                ps.setString(10,columns[10]);
//                ps.setString(11,columns[11]);
//                ps.setString(12,columns[12]);
//                ps.setString(13,columns[13]);
                ps.setString(14,columns[2]);

                ps.executeUpdate();

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

