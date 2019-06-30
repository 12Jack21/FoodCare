package MyUtil;

import com.csvreader.CsvReader;

import javax.transaction.Transaction;
import java.nio.charset.Charset;
import java.sql.*;

public class ReadCSV {

    public static void main(String[] args) {
        readFile("F:\\IDEApro\\FoodCare\\src\\test\\my.csv");
    }

    public static void readFile(String filePath) {

        Connection conn = null;
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/compemanage?useSSL=false&characterEncoding=utf-8&serverTimezone=GMT%2B8";
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

            String sql = "insert into test(name,quantity) values(?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            while (read.readRecord()) {
                System.out.println(read.getRawRecord());

                String lineTxt = read.getRawRecord();
                String[] columns = lineTxt.split(",");

                ps.setString(1, columns[0]);
                ps.setString(2, columns[1]);

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

