package serviceTest;

import baseTest.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import service.Imp.FoodServiceImp;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class FoodServiceTest extends BaseTest {
    @Autowired
    private FoodServiceImp foodServiceImp;

    private static final double DELTA = 1e-15;


    @Test
    public void recoginizePic(){

        try {
            //读取图片到 Byte数组
            BufferedInputStream in = new BufferedInputStream(new FileInputStream("fanqie.jpg"));
            ByteArrayOutputStream out = new ByteArrayOutputStream(1024);

            byte[] temp = new byte[1024];
            int size = 0;
            while ((size = in.read(temp)) != -1) {
                out.write(temp, 0, size);
            }
            in.close();
            byte[] content = out.toByteArray();





        } catch (IOException e) {
            e.printStackTrace();
        }

    }

//
//    @Test
//    //读取 csv 数据文件并存储到数据库中
//    public void readCSV(){
//        File csv = new File("E:\\GeoIP2-City-Locations-en.csv"); // CSV文件路径
//        BufferedReader br = null;
//        br = new BufferedReader(new FileReader(csv));
//        String line = "";
//        String everyLine = "";
//        int num = 0;
//
//        ArrayList<Geoip2CityLlocations> list = new ArrayList<>();
//        while ((line = br.readLine()) != null) { // 读取到的内容给line变量
//
//
//
//            Geoip2CityLlocations gcl = new Geoip2CityLlocations();
//
//            everyLine = line;
//
//            if (everyLine.contains(", ")) {
//                everyLine = everyLine.replace(", ", ";");
//            }
//
//            String[] split = everyLine.split(",");
//            if (split[0].equals("geoname_id")) {
//                continue;
//            }
//            if (StringUtils.isNotBlank(split[0])) {
//                gcl.setGeonameID(Integer.valueOf(split[0]));
//            }
//
//            list.add(gcl);
//            //	testMapper.insert(gcl);
//
//            if (list.size() % 1000 == 0) {
//                geoip2CityLlocationsMapper.insertList(list);
//                num++;
//                list.clear();
//            }
//
//            if(num ==129 && list.size() == 20)  {
//                geoip2CityLlocationsMapper.insertList(list);
//            }
//        }
//
//    }


}
