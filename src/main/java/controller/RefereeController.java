package controller;

import MyUtil.MyConvertor;
import com.mchange.v1.util.Sublist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import po.*;
import service.AdminService;
import service.AthleteService;
import service.RefereeService;
import service.TeamService;
import vo.AthTempListNormal;
import vo.CompetitionVO;
import vo.Rank;
import vo.RefTempListMajor;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/referee")
@SessionAttributes(value = {"refId"}, types = {int.class})
public class RefereeController {//TODO 运动员报名完毕后 设置编号
    @Autowired
    private RefereeService refereeService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private AthleteService athleteService;
    @Autowired
    private AdminService adminService;

    @RequestMapping("/setSession/{ref_id}") //调试用：设置 ref_id
    public String setSession(@PathVariable int ref_id,ModelMap map, HttpSession session){
        session.setMaxInactiveInterval(18000);//TODO session有效期
//        session.setAttribute();
        map.put("refId",ref_id);
        return "teamSidebar";
    }

    @RequestMapping("/profile")
    public String profile(@ModelAttribute("refId")int refid,ModelMap map){
        Referee referee = refereeService.getReferee(refid);
        map.put("ref",referee);
        return "refProfile";
    }

    @RequestMapping("/competitionList")
    public String refCompetitionList(@ModelAttribute("refId")int refid, ModelMap map){
        List<Judge> majors = refereeService.getJudges(refid,1);
        List<Judge> normals = refereeService.getJudges(refid,0);

        List<CompetitionVO> majorVO = new ArrayList<>();
        List<CompetitionVO> normalVO = new ArrayList<>();
        for(Judge j : majors){
            majorVO.add(MyConvertor.convertComp(j.getCompetition()));
        }
        for(Judge j : normals){
            normalVO.add(MyConvertor.convertComp(j.getCompetition()));
        }

        map.put("majors",majorVO);
        map.put("normals",normalVO);

        return "refCompetition";
    }

    @RequestMapping("/normal/{compid}")
    public String normal(@PathVariable int compid,@ModelAttribute("refId")int refid,ModelMap map){
        Judge judge = refereeService.getJudgeByRefComp(compid, refid);
        judge.setCompetitionVO(MyConvertor.convertComp(judge.getCompetition()));
        map.put("jud",judge);
        return "normalRef";
    }

    @ResponseBody
    @RequestMapping("/tempScore/list") //得到一个裁判下每个运动员的最新给分，进行了相应的组装
    public Object normalScoreList(@RequestParam("jud_id")int jud_id){
        Judge j = refereeService.getJudge(jud_id);
        List<Integer> athIds = teamService.getAthByCompGroup(j.getCompetition().getId(),j.getGroupno());

        List<AthTempListNormal> listNormals = new ArrayList<>();
        AthTempListNormal normal = null;
        TempScore t = null;
        for(Integer i:athIds){
            t = refereeService.getAthScoreListNormal(jud_id,i);
            normal = new AthTempListNormal();
            if(t != null){
                normal.setAthlete(t.getAthlete());
                normal.setScore(t.getScore());
                normal.setIsValid(t.getIsValid());

            }else {
                Athlete a = teamService.getAthlete(i);
                normal.setAthlete(a);
            }
            listNormals.add(normal);
        }

        return listNormals;
    }

    @ResponseBody
    @RequestMapping("/tempScore/add") //添加裁判给运动员的给分
    public Object normalAddScore(@RequestParam("ath_id")int athid,@RequestParam("jud_id")int jud_id,@RequestParam("score")Double score){
        if(score == null)
            return false;
        return refereeService.addScore(new Judge(jud_id),athid,score);
    }

    @ResponseBody
    @RequestMapping("/tempScore/log") //一个裁判对于一个运动员的所有打分记录（一般都是 isValid = false）
    public Object normalScoreLog(@RequestParam("jud_id")int jud_id,@RequestParam("ath_id")int athid){
        List<TempScore> logs = refereeService.getSingleAthTemp(jud_id, athid);
        return logs;
    }


    /* 以下为主裁判的相关的 resolve method */

    @RequestMapping("/major/{compid}")  //主裁判下的运动员表
    public String major(@PathVariable int compid,@ModelAttribute("refId")int refid,ModelMap map){
        Judge j = refereeService.getJudgeByRefComp(compid, refid);
        j.setCompetitionVO(MyConvertor.convertComp(j.getCompetition()));
        List<Integer> athIds = teamService.getAthByCompGroup(j.getCompetition().getId(),j.getGroupno());

        List<Participate> pars = new ArrayList<>();
        for(Integer i:athIds){
            Participate par = refereeService.getSingleAthById(compid, i);
            pars.add(par);
        }

        map.put("jud",j);
        map.put("pars",pars);

        return "majorRef";
    }


    @RequestMapping("/major/{compid}/{athid}") //查看某运动员的 裁判 给分列表，进行与normal相似的 对应的组装
    public String majorConfirm(@PathVariable("compid")int compid,@PathVariable("athid")int athid,@ModelAttribute("refId")int refId,ModelMap map){
        //显示运动员信息 和最终成绩
        Participate par = refereeService.getSingleAthById(compid, athid);

        map.put("ath_id",athid);
        map.put("comp_id",compid);
        map.put("par",par);

        return "majorConfirm";
    }

    @ResponseBody
    @RequestMapping("/majorConfirm/refList")  //主裁判对于某个运动员 下的裁判表
    public Object majorConfirmList(@RequestParam("compid")int compid,@RequestParam("athid")int athid,@ModelAttribute("refId")int refId){
        Judge judge = refereeService.getJudgeByRefComp(compid,refId);

        List<Referee> referees = refereeService.getJudgeByCompGroup(compid,judge.getGroupno());
        List<RefTempListMajor> listMajors = new ArrayList<>();
        RefTempListMajor major = null;
        TempScore t = null;

        for(Referee r:referees){
            //如果是主裁判自己就跳过
            if(refId == r.getId())
                continue;
            major = new RefTempListMajor();
            //最新给分
            t = refereeService.getNewestScoreOfRef(compid,r.getId(),athid);
            if(t != null){
                major.setReferee(r);
                major.setTempScore_id(t.getId());
                major.setScore(t.getScore());
                major.setIsValid(t.getIsValid());

            }else {
                major.setReferee(r);
            }

            listMajors.add(major);
        }
        return listMajors;
    }

    @ResponseBody
    @RequestMapping("/majorConfirm/showLog/{refid}") //显示某个裁判对 一个运动员的所有给分记录
    public Object showLogOfRef(@PathVariable int refid,@RequestParam("compid")int compid,@RequestParam("athid")int athid){
        List<TempScore> tempScores = refereeService.getRefLogOfSingleAth(compid,refid,athid);
        return tempScores;
    }

    @ResponseBody
    @RequestMapping("/majorConfirm/accept/{tempScore_id}")
    public Object acceptScore(@PathVariable int tempScore_id){
        return refereeService.updateTempScore(tempScore_id,1);
    }

    @ResponseBody
    @RequestMapping("/majorConfirm/reject/{tempScore_id}")
    public Object rejectScore(@PathVariable int tempScore_id){
        return refereeService.updateTempScore(tempScore_id,0);
    }

    @ResponseBody
    @RequestMapping("/majorConfirm/setScore") //TODO 计算最终成绩，其中 otherScore 为 D - P;如果所有参与的运动员都有成绩，则比赛结束
    public Object setScore(@RequestParam("compid")int compid,@RequestParam("athid")int athid,@RequestParam("finalScore")Double finalScore){
        boolean succeed = refereeService.figureResultScore(athid,compid,finalScore);
        if(succeed){
            //判断是否此比赛的所有参与人员都有分数（假设全部都参加比赛了）
            List<Participate> pars = teamService.getParticipateByComp(compid);
            boolean isEnd = true;
            for(Participate p:pars){
                if(p.getScore() == null){
                    isEnd = false;
                    break;
                }
            }
            //设置比赛结束
            if(isEnd){
                refereeService.setCompetitionEnd(compid);

                //某项比赛结束后设置该类比赛的决赛，并自动将初赛的前十名运动员加入参加表中
                Competition competition = teamService.getCompetition(compid);
                //如果是初赛
                if(competition.getType() == 0){
                    //设置为决赛
                    competition.setType(1);

                    adminService.addCompetition(competition);
                    //添加前十名运动员参加决赛，不足十名则取前五名
                    List<Rank> ranks = refereeService.getAthleteRank(compid);
                    List<Rank> r = null;
                    if(ranks.toArray().length > 10){
                        r = ranks.subList(0,10);
                    }else if (ranks.toArray().length > 5)
                        r = ranks.subList(0,5);

                    for(Rank rrr: r){
                        teamService.signUpAthlete(rrr.getId(),competition.getId());
                    }
                }

            }
        }

        return succeed;
    }

    @ResponseBody
    @RequestMapping("/majorConfirm/getFinalScore") //计算最终成绩后，刷新该运动员的最终成绩并显示
    public Object getFinalScore(@RequestParam("compid")int compid,@RequestParam("athid")int athid){
        Double finalScore = athleteService.getFinalScore(compid, athid);
        return finalScore;
    }

//
//    @ResponseBody
//    @RequestMapping("/majorConfirm/setFinalCompetition")
//    public Object setFinalCompetition(@RequestParam("compid")int compid){
//        //某项比赛结束后设置该类比赛的决赛，并自动将初赛的前十名运动员加入参加表中
//        Competition competition = teamService.getCompetition(compid);
//        //设置为决赛
//        competition.setType(1);
//
//        adminService.addCompetition(competition);
//        //添加前十名运动员参加决赛，不足十名则取前五名
//        List<Rank> ranks = refereeService.getAthleteRank(compid);
//        List<Rank> r = null;
//        if(ranks.toArray().length > 10){
//            r = ranks.subList(0,10);
//        }else if (ranks.toArray().length > 5)
//            r = ranks.subList(0,5);
//
//        for(Rank rrr: r){
//            teamService.signUpAthlete(rrr.getId(),competition.getId());
//        }
//
//    }

}
