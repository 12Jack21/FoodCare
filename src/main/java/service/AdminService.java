package service;

import java.util.List;

public interface AdminService {

    //登陆成功与否
    boolean canLogin(String user,String password);
    //得到指定队伍信息
    Team getTeam(int teamid);
    //添加代表队的信息（包括名字、账号密码以及是否为 东道主）
    boolean addTeamAccount(Team team);
    /**
    *@Description 删除代表队的信息
    *@Param [teamid]
    *@Return boolean
    */
    boolean deleteTeamAccount(int teamid);
    /**
    *@Description 更新代表队的信息
    *@Param [teamid]
    *@Return boolean
    */
    boolean updateTeamAccount(Team team);
    //查询所有代表队的基本信息
    List<Team> getAllTeams();
    //查询未报名完成的代表队的基本信息
    List<Team> getUnSighTeams();
    //查询所有裁判的信息（包括所属的代表队）
    List<Referee> getAllReferees();
    //查询所有比赛的信息
    List<Competition> getAllCompetitions();
    //查询未结束的比赛
    List<Competition> getUnEndCompetition();
    //查询已结束的比赛
    List<Competition> getEndCompetition();
    //设置比赛类型（即添加比赛到赛事表中）
    boolean addCompetition(Competition competition);
    //更新比赛（如设置 地点，日期）
    boolean updateCompetition(Competition competition);
    boolean deleteCompetition(int compid);
    //得到未分组的裁判列表
    List<Referee> getUnGroupRef(int compid);
    //得到此比赛的最大运动员组号
    Integer getMaxAthGroupOfComp(int compid);
    //得到此比赛的最大裁判组号
    Integer getMaxRefGroupOfComp(int compid);
    //设置分组并生成赛事表(对运动员)
    boolean setAthGroupNo(int athid,int compid,int groupno);
    //设置某场比赛的某一组的裁判（包括一个主裁判和最多四个普通裁判）
    boolean setRefereeInComp(Judge judge);
    //设置裁判的账户名
    boolean addRefereeAccount(int refid,String user);

}
