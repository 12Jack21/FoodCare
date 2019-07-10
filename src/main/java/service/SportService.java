package service;


import po.Play;
import po.Sport;

import java.util.List;

public interface SportService {

    List<Sport> getAllSports();

    //日期转换成 util.date，再转成 sql.Date
    List<Play> getPlayByAccDate(int account_id,String date);

    boolean addPlay(Play play);

    boolean removePlay(int account_id,int sport_id,String date);

    boolean updatePlay(int account_id,int sport_id, int time,String date);

    boolean updatePlayObj(Play play);


}
