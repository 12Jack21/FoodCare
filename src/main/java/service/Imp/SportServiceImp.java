package service.Imp;

import dao.PlayDAO;
import dao.SportDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import po.Play;
import po.Sport;
import service.SportService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class SportServiceImp implements SportService {
    @Autowired
    private SportDAO sportDAO;
    @Autowired
    private PlayDAO playDAO;

    @Override
    public List<Sport> getAllSports() {
        return sportDAO.getAllSports();
    }

    @Override
    public List<Play> getPlayByAccDate(int account_id, String date) {
        try {
            Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);

            java.sql.Date date2 = new java.sql.Date(date1.getTime());

            List<Play> plays = playDAO.getPlayByAccIdDate(account_id,date2);

            return plays;

        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean addPlay(Play play) {
        return playDAO.insert(play);
    }

    @Override
    public boolean removePlay(int account_id, int sport_id,String date) {
        try {
            Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);

            java.sql.Date date2 = new java.sql.Date(date1.getTime());

            return playDAO.delete(account_id,sport_id,date2);

        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updatePlay(int account_id, int sport_id, int time,String date) {
        try {
            Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);

            java.sql.Date date2 = new java.sql.Date(date1.getTime());

            return playDAO.update(account_id,sport_id,time,date2);

        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updatePlayObj(Play play) {
        return playDAO.updateObj(play);
    }
}
