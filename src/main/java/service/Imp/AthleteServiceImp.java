package service.Imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.AthleteService;

import java.util.List;

@Service
public class AthleteServiceImp implements AthleteService {
    @Autowired
    private ParticipateDAO participateDAO;
    @Autowired
    private AthleteDAO athleteDAO;
    @Autowired
    private TeamDAO teamDAO;

    @Override
    @Transactional
    public boolean setAthleteNo() {
        int hostTeam = teamDAO.getHostTeam().getId();
        List<Athlete> males = athleteDAO.getMaleAthletes(hostTeam);
        List<Athlete> females = athleteDAO.getFemaleAthletes(hostTeam);
        //组装id 和 no
        //female
        int intNo = 0;
        String no = null;
        for (Athlete a : females) {
            no = String.format("%03d", intNo);
            a.setNo(no);
            intNo += 2;
        }
        boolean result1 = athleteDAO.updateNoBatch(females);
        //male
        intNo = 1;
        for (Athlete a : males) {
            no = String.format("%03d", intNo);
            a.setNo(no);
            intNo += 2;
        }
        boolean result2 = athleteDAO.updateNoBatch(males);
        return result1 && result2;
    }

    public List<Participate> getAthScores(int athid,int type) {
        return participateDAO.getScoresByAth(athid,type);
    }

    @Override
    public Double getFinalScore(int compid, int athid) {
        return participateDAO.getFinalScore(compid, athid);
    }

    @Override
    public List<Participate> getAthScoresByCompName(int athid, String compName) {
        return participateDAO.getAthScoresByCompName(athid, compName);
    }

    @Override
    public double getPersonalScore(int athid, int compType) {
        return participateDAO.getPersonalScore(athid, compType);
    }
}
