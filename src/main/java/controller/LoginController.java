package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.AdminService;
import service.RefereeService;
import service.TeamService;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private TeamService teamService;
    @Autowired
    private RefereeService refereeService;
    @Autowired
    private AdminService adminService;

    @RequestMapping("/page")
    public String loginPage(){
        return "login";
    }

    @ResponseBody
    @RequestMapping(value = "/confirm",method = RequestMethod.POST)
    public Object login(@RequestParam("first")String user, @RequestParam("second")String password,
                        @RequestParam("tag")int tag, HttpSession session){
        Map<String,Object> map = new HashMap<>();
        boolean canLogin;
        if(tag == 0){ //Team login
            canLogin = teamService.canLogin(new Team(null,user,password));
            map.put("canLogin",canLogin);
            if(canLogin){
                int id = teamService.getTeamId(user, password);
                map.put("location","/sports/team/profile");
                session.setAttribute("teamId",id);
            }
        }else if(tag == 1){//Referee login
            canLogin = refereeService.canLogin(new Referee(user,password));
            map.put("canLogin",canLogin);
            if(canLogin){
                int id = refereeService.getRefIdByUser(user, password);
                map.put("location","/sports/referee/profile");
                session.setAttribute("refId",id);
            }
        }else {//Admin login
            canLogin = adminService.canLogin(user,password);
            map.put("canLogin",canLogin);
            if(canLogin){
                map.put("location","/sports/admin/team");
                session.setAttribute("admin","Sports");
            }
        }
        return map;
    }


}
