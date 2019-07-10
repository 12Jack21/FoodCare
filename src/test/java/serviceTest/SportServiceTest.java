package serviceTest;

import baseTest.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import po.Play;
import po.Sport;
import service.SportService;
import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

public class SportServiceTest extends BaseTest {
    @Autowired
    private SportService sportService;

    @Test
    public void getAllSports(){

        List<Sport> sports = sportService.getAllSports();

        assertEquals(22,sports.toArray().length);
    }

    @Test
    public void getPlayByAccDate(){

        List<Play> plays = sportService.getPlayByAccDate(5,"2019-07-10");

        assertEquals(1,plays.toArray().length);

        assertEquals("爬楼梯",plays.get(0).getSport().getName());
    }

    @Test
    public void manipulatePlay(){
        Play play = new Play();
        play.setAccount_id(5);
        play.setSport(new Sport(14));
        play.setDate(new Date());
        play.setTime(120);

        boolean m1 = sportService.addPlay(play);
        assertTrue(m1);

        boolean m2 = sportService.removePlay(5,5,"2019-07-10");
        assertTrue(m2);

//        boolean m3 = sportService.updatePlay(5,5,34,"2019-07-10");
//        assertTrue(m3);

        play.setTime(3001);
        boolean m4 = sportService.updatePlayObj(play);
        assertTrue(m4);
    }

}
