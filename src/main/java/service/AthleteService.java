package service;

import java.util.List;

public interface AthleteService {

    /**
     * 设置运动员的编号（自动分配）
     * 从000到999，女运动员为双号，男运动员为单号，东道主排在最后
     */
    boolean setAthleteNo();
    //得到个人所有成绩详情
    List<Participate> getAthScores(int athid,int type);
    //得到某场比赛 该运动员的最终成绩，一般由该组别的主裁判确定
    Double getFinalScore(int compid,int athid);
    //得到相应的名称比赛的个人成绩
    List<Participate> getAthScoresByCompName(int athid,String compName);
    //得到个人全能成绩(分为初赛与决赛)
    double getPersonalScore(int athid,int compType);

}
