package service;

import po.*;
import vo.Rank;

import java.util.List;

public interface RefereeService {
    //登陆是否成功
    boolean canLogin(Referee referee);
    Integer getRefIdByUser(String name,String user);
    //是否为小组主裁判
    boolean isMajorReferee(int refid,int compid);
    //取得裁判信息
    Referee getReferee(int refid);
    Judge getJudge(int jud_id);
    //获得裁判 吹判的比赛(作为主裁判或者作为普通裁判)
    List<Judge> getJudges(int refid,int refType);
    //根据比赛和裁判获取该 判决的信息
    Judge getJudgeByRefComp(int compid,int refid);
    List<Referee> getJudgeByCompGroup(int compid,int groupno);
    //得到一个裁判对某位运动员最新的给分
    TempScore getNewestScoreOfRef(int compid,int refid,int athid);
    //得到具体某个运动员的信息和给分
    Participate getSingleAthById(int compid,int athid);
    //得到一个普通裁判对于某个运动员的所有评分（一般为 isValid = false)
    List<TempScore> getRefLogOfSingleAth(int compid,int refid,int athid);
    //普通裁判给运动员打分
    boolean addScore(Judge judge, int athid, double score);
    //主裁判设置 接收 或者 拒绝
    boolean updateTempScore(int tempScoreid,Integer valid);
    //主裁判检查成绩是否有效
    boolean checkScore(int tempid,int isValid);
    //得到某判决 的运动员的最新成绩
    TempScore getAthScoreListNormal(int jud_id,int athid);
    //得到某判决中 单个运动员的所有给分记录
    List<TempScore> getSingleAthTemp(int jud_id,int athid);
//    //得到所有打分信息(主裁判)
//    List<TempScore> getAllTempScores();
    //得到指定运动员在某一场比赛的打分信息（主）
    List<TempScore> getAthleteTempScores(int athid,int compid);
    //计算并设置个人最终单项成绩（主）
    boolean figureResultScore(int athid,int compid,Double score);
    boolean setCompetitionEnd(int compid);
    //得到某项比赛的 运动员单项排名表
    List<Rank> getAthleteRank(int compid);
    //得到某项比赛的 队伍单项排名表(只取前四名运动员计算总和，没有四名的就不计算)
    List<Rank> getTeamRank(int compid);
    //得到某项比赛的 决赛名单
    List<Participate> getFinalCompAthletes(int compid);
    //得到个人全能分数（分为初赛和决赛，其参加的所有初赛或者决赛）
//    double getAthleteAllScore(int compType,int athid);
//
//    //得到每个类别的团体成绩
//    double getTeamScore(int teamid,int compid);
}
