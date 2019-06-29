package controller;

import MyUtil.MyConvertor;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import po.*;
import service.AdminService;
import service.AthleteService;
import service.RefereeService;
import service.TeamService;
import vo.CompSign;
import vo.CompetitionVO;
import vo.Rank;

import javax.enterprise.inject.Model;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes(value = {"teamId"}, types = {int.class})
@RequestMapping("/team")
public class TeamController {  //TODO 404 page
    @Autowired
    private TeamService teamService;
    @Autowired
    private RefereeService refereeService;
    @Autowired
    private AthleteService athleteService;
    @Autowired
    private AdminService adminService;

    @RequestMapping("/setSession/{id}")  //为了方便调试
    public Object setSession(@PathVariable int id, HttpSession session) {
        session.setAttribute("teamId", id);
        return true;
    }

    @RequestMapping("/profile") //队伍信息
    public String profile(ModelMap map,@ModelAttribute("teamId")int teamid){
        Team team = adminService.getTeam(teamid);
        map.put("team",team);
        return "teamProfile";
    }


    @Transactional
    @RequestMapping("/coach")
    public String coachListJump(ModelMap map, @ModelAttribute("teamId") int teamId) {
        List<Coach> list = teamService.getTeamCoaches(teamId);
        map.put("role", "coach");
        if (list.toArray().length == 0) {
            return "jumbotron_coach";
        } else
            return "coach";
    }

    @ResponseBody
    @RequestMapping("/coach/list")
    public Object coachListBody(@ModelAttribute("teamId") int id, HttpSession session) {
        List<Coach> list = teamService.getTeamCoaches(id);
        Map<String, Object> map = new HashMap<>();

        map.put("coaches", list);
        return list;
    }

    @ResponseBody
    @RequestMapping("/coach/add")
    public Object addCoach(Coach coach, @ModelAttribute("teamId") int teamid) {
        coach.setTeam(new Team(teamid, null));
        return teamService.addCoach(coach);
    }

    @ResponseBody
    @Transactional
    @RequestMapping("/coach/delete")
    public Object deleteCoach(@RequestParam(value = "data") int[] data) {
        boolean success = true;
        for (int i = 0; i < data.length; i++) {
            System.out.println(data[i]);
            if (success == false)
                break;
            success = teamService.deleteCoach(data[i]);
        }
        return success;
    }

    @ResponseBody
    @RequestMapping("/coach/update")
    public Object updateCoach(Coach coach) {
        return teamService.updateCoach(coach);
    }

    @Transactional
    @RequestMapping("/doctor")
    public String doctorListJump(ModelMap map, @ModelAttribute("teamId") int teamId) {
        List<Doctor> list = teamService.getTeamDoctors(teamId);
        map.put("role", "doctor");
        if (list.toArray().length == 0) {
            return "jumbotron_doctor";
        } else
            return "doctor";
    }

    @ResponseBody
    @RequestMapping("/doctor/list")
    public Object doctorListBody(@ModelAttribute("teamId") int id, HttpSession session) {
        List<Doctor> list = teamService.getTeamDoctors(id);
        return list;
    }

    @ResponseBody
    @RequestMapping("/doctor/add")
    public Object addDoctor(Doctor doctor, @ModelAttribute("teamId") int teamid) {
        doctor.setTeam(new Team(teamid, null));
        return teamService.addDoctor(doctor);
    }

    @ResponseBody
    @Transactional
    @RequestMapping("/doctor/delete")
    public Object deleteDoctor(@RequestParam(value = "data") int[] data) {
        boolean success = true;
        for (int i = 0; i < data.length; i++) {
            System.out.println(data[i]);
            if (success == false)
                break;
            success = teamService.deleteDoctor(data[i]);
        }
        return success;
    }

    @ResponseBody
    @RequestMapping("/doctor/update")
    public Object updateDoctor(Doctor doctor) {
        boolean success = false;
        success = teamService.updateDoctor(doctor);
        return success;
    }

    @Transactional
    @RequestMapping("/captain")
    public String capListJump(ModelMap map, @ModelAttribute("teamId") int teamId) {
        Captain captain = teamService.getTeamCaptain(teamId);
        map.put("role", "captain");
        if (captain == null) {
            return "jumbotron_coach";
        } else {
            map.put("captain", captain);
            return "captain";
        }
    }

    @ResponseBody
    @RequestMapping("/captain/add")
    public Object addCaptain(Captain captain, @ModelAttribute("teamId") int teamid) {
        captain.setTeam(new Team(teamid, null));
        return teamService.addCaptain(captain);
    }

    @ResponseBody
    @RequestMapping("/captain/update")
    public Object updateCaptain(Captain captain) {
        return teamService.updateCaptain(captain);
    }

    @RequestMapping("/referee")
    public String refereeList(@ModelAttribute("teamId") int id, ModelMap map) {
            return "referee";
    }

//    @RequestMapping("/referee/jumbotron_referee")
//    public String refereeJump(ModelMap map, Referee referee) {
//        try {
//            //for test
//            referee.setTeam(new Team(1, null));
//            boolean success = teamService.addReferee(referee);//判断是否还没有一个 referee
//            if (success) {
//                map.put("role", "referee");
//                //成功添加后进行重定向操作
//                return "redirect:1";
//            } else {
//                return "jumbotron_referee";
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "jumbotron_referee";
//        }
//
//    }

    @ResponseBody
    @RequestMapping("/referee/list")
    public Object refereeListBody(@ModelAttribute("teamId") int id, HttpSession session) {
        List<Referee> list = teamService.getTeamReferees(id);
        return list;
    }

    @ResponseBody
    @RequestMapping("/referee/add")
    public Object addReferee(Referee referee, @ModelAttribute("teamId") int teamid) {
        boolean success = false;
        referee.setTeam(new Team(teamid, null));
        success = teamService.addReferee(referee);
        return success;
    }

    @ResponseBody
    @Transactional
    @RequestMapping("/referee/delete")
    public Object deleteReferee(@RequestParam(value = "data") int[] data) {
        boolean success = true;
        for (int i = 0; i < data.length; i++) {
            System.out.println(data[i]);
            if (success == false)
                break;
            success = teamService.deleteReferee(data[i]);
        }
        return success;
    }

    @ResponseBody
    @RequestMapping("/referee/update")
    public Object updateReferee(Referee referee) {
        boolean success = false;
        success = teamService.updateReferee(referee);
        return success;
    }

    @RequestMapping("/referee/{id}")
    public String showRefereeInf(@PathVariable int id, ModelMap map) {
        Referee referee = refereeService.getReferee(id);
        List<Judge> normals = refereeService.getJudges(id, 0);
        List<Judge> majors = refereeService.getJudges(id, 1);

        for (Judge n : normals) {
            n.setCompetitionVO(MyConvertor.convertComp(n.getCompetition()));
        }
        for (Judge m : majors) {
            m.setCompetitionVO(MyConvertor.convertComp(m.getCompetition()));
        }
        map.put("referee", referee);
        map.put("normals", normals);
        map.put("majors", majors);

        return "refereeInf";
    }

    @RequestMapping("/competition")
    public String competitionList(ModelMap map) {
        List<Competition> initials = teamService.getCompetitionByType(0);
        List<Competition> finals = teamService.getCompetitionByType(1);
        List<CompetitionVO> iniVO = new ArrayList<>();
        List<CompetitionVO> fiVO = new ArrayList<>();

        for (Competition c : initials) {
            iniVO.add(MyConvertor.convertComp(c));
        }
        for (Competition c : finals) {
            fiVO.add(MyConvertor.convertComp(c));
        }
        map.put("initials", iniVO);
        map.put("finals", fiVO);
        return "competition";
    }

    @RequestMapping("/competition/{id}") ///显示某比赛的具体信息
    public String competitionInf(@PathVariable int id, ModelMap map) {
        Competition c = teamService.getCompetition(id);
        List<Participate> pars = teamService.getParticipateByComp(id);
        List<Judge> juds = teamService.getJudgeByComp(id);

        map.put("competition", MyConvertor.convertComp(c));
        map.put("participates", pars);
        map.put("judges", juds);

        //如果比赛已结束，就显示排名表
        if (c.getIsEnd() == 1) {
            List<Rank> athRank = refereeService.getAthleteRank(c.getId());
            List<Rank> teamRank = refereeService.getTeamRank(c.getId());
            map.put("athRank", athRank);
            map.put("teamRank", teamRank);
        }

        return "competitionInf";
    }

    @RequestMapping("/athlete")
    public String athleteList(@ModelAttribute("teamId") int id, ModelMap map) {
        //三个不同年龄组的运动员
        List<Athlete> athletes0 = teamService.getAthletesByAgeGroup(0, id);
        List<Athlete> athletes1 = teamService.getAthletesByAgeGroup(1, id);
        List<Athlete> athletes2 = teamService.getAthletesByAgeGroup(2, id);

        map.put("athletes0", athletes0);
        map.put("athletes1", athletes1);
        map.put("athletes2", athletes2);

        return "athlete";
    }

    @RequestMapping("/athlete/list/{ageGroup}")
    public String athleteListSection(@PathVariable int ageGroup, @ModelAttribute(value = "teamId") int id, ModelMap map) {
        List<Athlete> athletes = teamService.getAthletesByAgeGroup(ageGroup, id);
        map.put("athletes", athletes);

        return "athleteSection";
    }

    @ResponseBody
    @RequestMapping("/athlete/add")
    public Object addAthlete(Athlete athlete, @ModelAttribute("teamId") int teamid) {
        int age = athlete.getAge();
        int ageGroup = 0;
        if (age >= 9 && age <= 10)
            ageGroup = 1;
        else if (age >= 11 && age <= 12)
            ageGroup = 2;

        List<Athlete> athletes = teamService.getAthletesByGroup(ageGroup, athlete.getSex(), teamid);
        //判断是否有六个人 在一个性别的一个年龄组里
        if (athletes.toArray().length == 6)
            return false;
        else {
            athlete.setTeam(new Team(teamid, null));
            return teamService.addAthlete(athlete);
        }
    }

    @ResponseBody
    @Transactional
    @RequestMapping("/athlete/delete")
    public Object deleteAthlete(@RequestParam(value = "data") int athId) {
        return teamService.deleteAthlete(athId);
    }

    @ResponseBody
    @RequestMapping("/athlete/update")
    public Object updateAthlete(Athlete athlete) {
        boolean success = false;
        success = teamService.updateAthlete(athlete);
        return success;
    }

    @RequestMapping("/athleteInf/{athid}")
    public String athleteInf(@PathVariable int athid, ModelMap map) {
        Athlete athlete = teamService.getAthlete(athid);
        List<Participate> initials = athleteService.getAthScores(athid, 0);
        List<Participate> finals = athleteService.getAthScores(athid, 1);

        for (Participate n : initials) {
            n.setCompetitionVO(MyConvertor.convertComp(n.getCompetition()));
        }
        for (Participate m : finals) {
            m.setCompetitionVO(MyConvertor.convertComp(m.getCompetition()));
        }
        map.put("athlete", athlete);
        map.put("initials", initials);
        map.put("finals", finals);

        return "athleteInf";
    }

    @RequestMapping("/sign/list")
    public String signUpPage(ModelMap map) {
        return "signUp";
    }

    @ResponseBody
    @RequestMapping("/sign/competition") //展示所有比赛供队伍报名
    public Object signCompetition(@ModelAttribute("teamId") int teamid) {
        List<Competition> competitions = teamService.getAllCompetition();
        List<CompSign> compSigns = new ArrayList<>();

        CompSign compSign = null;
        Integer signId = null;
        for (Competition c : competitions) {
            compSign = new CompSign();
            compSign.setCompetitionVO(MyConvertor.convertComp(c));

            //判断是否报名了此比赛
            signId = teamService.getSignByTeamComp(teamid, c.getId());
            if (signId != null)
                compSign.setIsSign("已报名");
            else
                compSign.setIsSign("未报名");

            compSigns.add(compSign);
        }

        return compSigns;
    }

    @ResponseBody
    @RequestMapping("/sign/{compid}")  //展示某场比赛能报名的 运动员列表
    public Object signList(@PathVariable int compid, @ModelAttribute("teamId") int teamid) {
        Competition c = teamService.getCompetition(compid);
        int sexGroup = c.getSexgroup();
        int ageGroup = c.getAgegroup();

        return teamService.getAthletesByGroup(ageGroup, sexGroup, teamid);

    }

    @Transactional
    @ResponseBody
    @RequestMapping("/signUp/{compid}") //给运动员报名，同时给队伍报名
    public Object signUp(@PathVariable int compid, @RequestParam("data") int[] athIds, @ModelAttribute("teamId") int teamId) {
        boolean succeed = true;
        //给运动员报名
        for (int i = 0; i < athIds.length; i++) {
            System.out.println(athIds[i]);
            succeed = teamService.signUpAthlete(athIds[i], compid);
            if (succeed == false)
                return succeed;
        }

        //给队伍报名
        succeed = teamService.addCompSign(teamId, compid);

        return succeed;
    }

    //
    @ResponseBody //上传附件
    @RequestMapping(value = "/signUp/upload", method = RequestMethod.POST)
    public Object upLoadFile(@RequestParam("attachment") MultipartFile file, @ModelAttribute("teamId") int teamId,HttpServletRequest request) throws IOException {
        String type = request.getContentType();
        return teamService.uploadFile(file,teamId);
    }

    @ResponseBody
    @RequestMapping("/signUp/finish") //队伍结束所有报名，并且监测是否所有队伍报名完成，若完成，则自动设置运动员编号
    public Object finishSign(@ModelAttribute("teamId")int teamid){
        //设置队伍 报名结束
        boolean succeed1 = teamService.setSignUpFinish(teamid);

        //判断是否所有队伍都报名结束了，如果是，则自动给运动员设置编号
        List<Team> teams = adminService.getAllTeams();
        boolean succeed2 = true;
        for(Team t: teams){
            if(t.getIsSign() == 0){
                succeed2 = false;
                break;
            }
        }
        if(succeed2)
            athleteService.setAthleteNo();
        return succeed1;
    }

}
