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

}
