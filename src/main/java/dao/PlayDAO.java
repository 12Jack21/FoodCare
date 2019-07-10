package dao;

import org.springframework.stereotype.Repository;
import po.Play;

import java.util.Date;
import java.util.List;

@Repository
public interface PlayDAO {

    List<Play> getPlayByAccIdDate(int account_id, Date date);

    boolean insert(Play play);

    boolean delete(int account_id,int sport_id,Date date);

    boolean update(int account_id,int sport_id,int time, Date date);

    boolean updateObj(Play play);

}
