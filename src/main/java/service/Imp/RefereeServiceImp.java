package service.Imp;

import dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.RefereeService;
import vo.Rank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class RefereeServiceImp implements RefereeService {
    @Autowired
    private RefereeDAO refereeDAO;
    @Autowired
    private ParticipateDAO participateDAO;
    @Autowired
    private JudgeDAO judgeDAO;
    @Autowired
    private TempScoreDAO tempScoreDAO;
    @Autowired
    private SignDAO signDAO;
    @Autowired
    private TeamDAO teamDAO;
    @Autowired
    private CompetitionDAO competitionDAO;

    @Override
    public boolean canLogin(Referee referee) {
        String user = refereeDAO.getUser(referee.getName());
        return user.equals(referee.getUser());
    }

    @Override
    public Integer getRefIdByUser(String name, String user) {
        return refereeDAO.getRefIdByUser(name, user);
    }

    @Override
    public boolean isMajorReferee(int refid, int compid) {
        int refType = judgeDAO.getReftype(refid, compid);
        return refType != 0;
    }

    @Override
    public Referee getReferee(int refid) {
        return refereeDAO.getReferee(refid);
    }

    @Override
    public Judge getJudge(int jud_id) {
        return judgeDAO.getJudge(jud_id);
    }

    @Override
    public List<Judge> getJudges(int refid, int refType) {
        return judgeDAO.getJudgesByRef(refid,refType);
    }

    @Override
    public Judge getJudgeByRefComp(int compid, int refid) {
        return judgeDAO.getJudgeByRefComp(compid, refid);
    }

    @Override
    public List<Referee> getJudgeByCompGroup(int compid, int groupno) {
        return judgeDAO.getJudgeByCompGroup(compid, groupno);
    }

    @Override
    public TempScore getNewestScoreOfRef(int compid, int refid,int athid) {
        return tempScoreDAO.getNewestScoreOfRef(compid, refid, athid);
    }

    @Override
    public Participate getSingleAthById(int compid, int athid) {
        return participateDAO.getSingleAthById(compid, athid);
    }

    @Override
    public List<TempScore> getRefLogOfSingleAth(int compid, int refid, int athid) {
        return tempScoreDAO.getRefLogOfSingleAth(compid, refid, athid);
    }

    @Override
    public boolean addScore(Judge judge, int athid, double score) {
        TempScore t = new TempScore(athid,score,judge);
        return tempScoreDAO.addTempScore(t);
    }

    @Override
    public boolean updateTempScore(int tempScoreid, Integer valid) {
        return tempScoreDAO.updateTempScore(tempScoreid, valid);
    }

    @Override
    public boolean checkScore(int tempid, int isValid) {
        return tempScoreDAO.updateTempScore(tempid,isValid);
    }

    @Override
    public TempScore getAthScoreListNormal(int jud_id, int athid) {
        return tempScoreDAO.getAthScoreListNormal(jud_id, athid);
    }

    @Override
    public List<TempScore> getSingleAthTemp(int jud_id, int athid) {
        return tempScoreDAO.getSingleAthTemp(jud_id, athid);
    }

    @Override
    public List<TempScore> getAthleteTempScores(int athid,int compid) {
        return tempScoreDAO.getAthTempScores(athid, compid);
    }

    @Override
    public boolean figureResultScore(int athid, int compid,Double score) {
        return participateDAO.updateScore(athid,compid,score);
    }

    @Override
    public boolean setCompetitionEnd(int compid) {
        return competitionDAO.updateCompetitionEnd(compid);
    }

    @Override
    public List<Rank> getAthleteRank(int compid) {
        List<Participate> participates = participateDAO.getAthletesRankByComp(compid);
        List<Rank> ranks = new ArrayList<>();
        Rank r, preRank = null;
        int rank = 1;
        for (Participate p:participates){
            r = new Rank();
            r.setId(p.getAthlete().getId());
            r.setName(p.getAthlete().getName());
            r.setScore(p.getScore());
            r.setRank(rank++);

            //同分数的设置同名次
            if(preRank == null){
                preRank = r;
            }else if (p.getScore().equals(preRank.getScore()))
                r.setRank(preRank.getRank());
            else
                preRank = r;

            ranks.add(r);
        }

        return ranks;
    }

    @Override
    public List<Rank> getTeamRank(int compid) {
        List<Integer> teamIds = signDAO.getTeamidsOfComp(compid);
        List<Rank> ranks = new ArrayList<>();
        Rank r = null;
        //遍历所有队伍
        for (Integer id: teamIds){
            List<Participate> pars = participateDAO.getCompScoresByTeam(compid,id);

            //少于四个则不计算团体成绩
            if(pars.toArray().length >= 4){
                r = new Rank();
                r.setId(id);
                r.setName(teamDAO.getTeam(id).getName());

                Double score = pars.get(0).getScore() + pars.get(1).getScore()
                        + pars.get(2).getScore() + pars.get(3).getScore();
                r.setScore(score);

                ranks.add(r);
            }
        }
        //排序
        Collections.sort(ranks);
        //倒排
        Collections.reverse(ranks);

        if(ranks.toArray().length != 0){
            //设置名次
            Rank preRank = ranks.get(0);
            int rankIndex = 1;
            for(Rank rrr: ranks){
                rrr.setRank(rankIndex++);
                if(rrr.getScore().equals(preRank.getScore()))
                    rrr.setRank(preRank.getRank());
                else
                    preRank = rrr;
            }

        }

        return ranks;

    }

    @Override
    /**
    *自定义取前十名参加决赛
    * */
    public List<Participate> getFinalCompAthletes(int compid) {
        List<Participate> participates = participateDAO.getAthletesRankByComp(compid);
        return participates.subList(0,10);//包含0 不包含 10
    }
}
