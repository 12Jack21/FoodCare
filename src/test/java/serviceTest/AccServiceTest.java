package serviceTest;

import baseTest.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import service.AccountService;

public class AccServiceTest extends BaseTest {
    @Autowired
    private AccountService accountService;

    @Test
    public void updatePic(){

        accountService.updatePic(1,"/");


    }

}
