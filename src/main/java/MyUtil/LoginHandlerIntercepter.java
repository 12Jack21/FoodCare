package MyUtil;


import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginHandlerIntercepter implements HandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object arg2, Exception arg3)
            throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
                           Object arg2, ModelAndView arg3) throws Exception {

    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse arg1,
                             Object arg2) throws Exception {
        String requestURI = request.getRequestURI();
        HttpSession session = request.getSession();
        if(requestURI.contains(".action"))
            return true;
        if(requestURI.indexOf("admin/")>0){
            String  admin = (String) session.getAttribute("admin");
            if(admin != null && admin.equals("Sport")){
                //登陆成功的用户
                return true;
            }else{
                //没有登陆，转向登陆界面
                request.getRequestDispatcher("/login/page").forward(request,arg1);
                return false;
            }
        }else if (requestURI.indexOf("referee/") > 0){
            Integer refId = (Integer) session.getAttribute("refId");
            if(refId != null){
                //登陆成功的用户
                return true;
            }else{
                //没有登陆，转向登陆界面
                request.getRequestDispatcher("/login/page").forward(request,arg1);
                return false;
            }
        }else if (requestURI.indexOf("team/") > 0){
            //说明处在队伍管理界面
            Integer teamId = (Integer) session.getAttribute("teamId");
            if(teamId != null){
                //登陆成功的用户
                return true;
            }else{
                //没有登陆，转向登陆界面
                request.getRequestDispatcher("/login/page").forward(request,arg1);
                return false;
            }

        }else
            return true;
    }

}
