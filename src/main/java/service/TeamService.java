package service;

import org.springframework.web.multipart.MultipartFile;
import po.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface TeamService {

    //登陆成功与否
    boolean canLogin(Team team);
    Integer getTeamId(String user,String password);
    //更改密码
    boolean updatePassWord(Team team);
    //取得运动员的信息
    Athlete getAthlete(int athid);
    //根据年龄组获取运动员
    List<Athlete> getAthletesByAgeGroup(int ageGroup, int teamid);
    //根据年龄组和性别组获取运动员
    List<Athlete> getAthletesByGroup(int ageGroup, int sexGroup,int teamid);
    //添加运动员
    boolean addAthlete(Athlete athlete);
    //添加队医
    boolean addDoctor(Doctor doctor);
    //添加教练
    boolean addCoach(Coach coach);
    //添加队长
    boolean addCaptain(Captain captain);
    //添加裁判
    boolean addReferee(Referee referee);
    //更新运动员
    boolean updateAthlete(Athlete athlete);
    //更新队医
    boolean updateDoctor(Doctor doctor);
    //更新教练
    boolean updateCoach(Coach coach);
    //更新队长
    boolean updateCaptain(Captain captain);
    //更新裁判
    boolean updateReferee(Referee referee);
    //删除运动员
    boolean deleteAthlete(int id);
    //删除队医
    boolean deleteDoctor(int id);
    //删除教练
    boolean deleteCoach(int id);
    //删除队长
    boolean deleteCaptain(int id);
    //删除裁判
    boolean deleteReferee(int id);
    //判断是否报名了某比赛
    Integer getSignByTeamComp(int teamid,int compid);
    //添加队伍对某场比赛的报名
    boolean addCompSign(int teamid,int compid);
    //给运动员报名
    boolean signUpAthlete(int athid,int compid);
    //设置报名完成
    boolean setSignUpFinish(int teamid);
    //查询某比赛是否已报名
    boolean ifSignUpForComp(int teamid,int compid);
    //查询所有队伍中的运动员
    List<Athlete> getTeamAthletes(int teamid);
    //查询队伍中的所有队医
    List<Doctor> getTeamDoctors(int teamid);
    //查询队伍中的所有教练
    List<Coach> getTeamCoaches(int teamid);
    //查询队伍中的所有裁判
    List<Referee> getTeamReferees(int teamid);
    //查询队伍中的队长
    Captain getTeamCaptain(int teamid);
    //查询所有比赛的信息
    List<Competition> getAllCompetition();
    //查询特定的比赛信息
    Competition getCompetition(int compid);
    //查询某比赛的运动员
    List<Participate> getParticipateByComp(int compid);
    List<Integer> getAthByCompGroup(int compid,int groupno);
    //查询某比赛的裁判
    List<Judge> getJudgeByComp(int compid);
    //根据比赛类型进行查询
    List<Competition> getCompetitionByType(int type);
    //查询未结束的比赛
    List<Competition> getUnEndCompetition();
    //查询已结束的比赛
    List<Competition> getEndCompetition();
    //得到每个类别比赛的团体成绩
    List<Double> getCompScoresByTeam(int teamid);
    //上传附件
    boolean uploadFile(MultipartFile file,int teamid) throws IOException;
}
