//package controller;
//
//import MyUtil.MyConvertor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.*;
//import service.AthleteService;
//import service.TeamService;
//
//import java.util.ArrayList;
//import java.util.List;
//
////  /admin/competition
//
//@Controller
//@RequestMapping("/admin")
//public class AdminController {
//    @Autowired
//    private AdminService adminService;
//    @Autowired
//    private TeamService teamService;
//    @Autowired
//    private AthleteService athleteService;
//
//    @RequestMapping("/competition")
//    public String adminCompetition() {
//        return "adminCompetition";
//    }
//
//    @ResponseBody
//    @RequestMapping("/competition/list")
//    public Object adminCompList() {
//        List<Competition> competitions = adminService.getAllCompetitions();
//        List<CompetitionVO> cVO = new ArrayList<>();
//
//        for (Competition c : competitions) {
//            cVO.add(MyConvertor.convertComp(c));
//        }
//        return cVO;
//    }
//
//    @ResponseBody
//    @RequestMapping("/competition/add")
//    public Object adminCompetitionAdd(Competition competition) {
//        boolean s = false;
//        s = adminService.addCompetition(competition);
//        return s;
//    }
//
//    @ResponseBody
//    @RequestMapping("/competition/update")
//    public Object adminCompetitionUpdate(Competition competition) {
//        return adminService.updateCompetition(competition);
//    }
//
//    @ResponseBody
//    @RequestMapping("/competition/delete")
//    public Object competitionDelete(@RequestParam("data") int[] compIds) {
//        boolean success = false;
//        for (int i = 0; i < compIds.length; i++) {
//            success = adminService.deleteCompetition(compIds[i]);
//            if (success == false)
//                break;
//        }
//        return success;
//    }
//
//    @RequestMapping("/group/{compid}")
//    public String adminGroup(@PathVariable int compid, ModelMap map) {
//        Competition competition = teamService.getCompetition(compid);
//        CompetitionVO cVO = MyConvertor.convertComp(competition);
//
//        Integer groupNo = adminService.getMaxAthGroupOfComp(compid);
//        map.put("competition1", cVO);
//        map.put("maxGroupNo", groupNo);
//        return "adminGroup";
//    }
//
//    @ResponseBody
//    @RequestMapping("/group/{compid}/ath")
//    public Object athList(@PathVariable int compid) {
//        List<Participate> pars = teamService.getParticipateByComp(compid);
//        List<Participate> parVO = new ArrayList<>();
//        //找到已分组的
//        for (Participate p : pars) {
//            if (p.getGroupno() != 0)
//                parVO.add(p);
//        }
//
//        return parVO;
//    }
//
//    @ResponseBody
//    @RequestMapping("/group/{compid}/ref") //已分好组的裁判
//    public Object refList(@PathVariable int compid) {
//        List<Judge> judges = teamService.getJudgeByComp(compid);
//        List<Judge> judVO = new ArrayList<>();
//        //找到已分组的
//        for (Judge j : judges) {
//            if (j.getGroupno() != 0)
//                judVO.add(j);
//        }
//        return judVO;
//    }
//
//    @ResponseBody
//    @RequestMapping("/group/{compid}/ath/group") //比赛中未分组的运动员
//    public Object athGroup(@PathVariable int compid) {
//        List<Participate> pars = teamService.getParticipateByComp(compid);
//        List<Participate> parVO = new ArrayList<>();
//        //找到未分组的
//        for (Participate p : pars) {
//            if (p.getGroupno() == 0)
//                parVO.add(p);
//        }
//
//        return parVO;
//    }
//
//    //找到未分组的裁判，对于 major和normal都是相同的
//    @ResponseBody
//    @RequestMapping("/group/{compid}/ref/group")
//    public Object refGroup(@PathVariable int compid) {
//        List<Referee> referees = adminService.getUnGroupRef(compid);
//        return referees;
//    }
//
//
//    @Transactional
//    @ResponseBody
//    @RequestMapping("/group/{compid}/addAthGroup")
//    public Object addAthGroup(@PathVariable int compid, @RequestParam("data") int[] athIds) {
//        boolean s = false;
//        //最大组号加一
//        Integer groupNo = adminService.getMaxAthGroupOfComp(compid);
//        for (int i = 0; i < athIds.length; i++) {
//            s = adminService.setAthGroupNo(athIds[i], compid, groupNo);
//        }
//        return s;
//    }
//
//    @Transactional
//    @ResponseBody
//    @RequestMapping("/group/{compid}/addRefGroup")
//    public Object addRefGroup(@PathVariable int compid, @RequestParam("normal") int[] norIds, @RequestParam("major") int majorId) {
//        boolean s = false;
//        Judge judge = null;
//        int refType = 0;  //normal referee type symbol
//        //最大组号加一
//        Integer groupNo = adminService.getMaxRefGroupOfComp(compid);
//        for (int i = 0; i < norIds.length; i++) {
//            judge = new Judge(new Referee(norIds[i]), new Competition(compid), groupNo, refType);
//            s = adminService.setRefereeInComp(judge);
//        }
//        //设置主裁判
//        refType = 1;
//        judge = new Judge(new Referee(majorId), new Competition(compid), groupNo, refType);
//        s = adminService.setRefereeInComp(judge);
//
//        return s;
//    }
//
//    @RequestMapping("/team")
//    public String team(ModelMap map) {
//        return "adminTeam";
//    }
//
//    @ResponseBody
//    @RequestMapping("/team/list")
//    public Object teamList() {
//        List<Team> teams = adminService.getAllTeams();
//        return teams;
//    }
//
//    @ResponseBody
//    @RequestMapping("/team/add")
//    public Object addTeam(Team team) {
//        boolean success = false;
//        success = adminService.addTeamAccount(team);
//        return success;
//    }
//
//    @ResponseBody
//    @Transactional
//    @RequestMapping("/team/delete")
//    public Object deleteTeam(@RequestParam(value = "data") int[] data) {
//        boolean success = true;
//        for (int i = 0; i < data.length; i++) {
//            success = adminService.deleteTeamAccount(data[i]);
//            if (success == false)
//                break;
//        }
//        return success;
//    }
//
//    @ResponseBody
//    @RequestMapping("/team/update")
//    public Object updateTeam(Team team) {
//        boolean success = false;
//        success = adminService.updateTeamAccount(team);
//        return success;
//    }
//
//    @RequestMapping("/referee")
//    public String refereePage() {
//        return "adminReferee";
//    }
//
//    @ResponseBody
//    @RequestMapping("/referee/list")
//    public Object refereeList() { //TODO 不需要改变吗？
//        List<Referee> referees = adminService.getAllReferees();
////        List<RefereeVO> rVO = new ArrayList<>();
////        for(Referee r:referees){
////            rVO.add(MyConvertor.convertRef(r));
////        }
////        return  rVO;
//        return referees;
//    }
//
//    @ResponseBody
//    @RequestMapping("/referee/update")
//    public Object updateRefereeUser(@RequestParam("id") int id, @RequestParam("user") String user) {
//        return adminService.addRefereeAccount(id, user); //TODO 类似这样重新优化代码
//    }
//
//    @ResponseBody
//    @RequestMapping("/athlete/setNo")  //TODO setSignUpFinish()调用
//    public Object setAtheleteNo() {
//        return athleteService.setAthleteNo();
//    }
//
//
////    @ResponseBody
////    @RequestMapping("/team/setSignUp")
////    public Object setTeamSignUpFinish(){
////
////    }
//}
