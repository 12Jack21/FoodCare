//package controller;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.Date;
//import java.util.Map;
//
//@Controller
////@SessionAttributes({"user"})
//public class TestPOJO {
//
//    @RequestMapping(value = "/testPOJO",method = RequestMethod.POST)
//    public String testPOJOk(User abc){
//        System.out.println("User info: " + abc);
//        System.out.println("TestingPOJO...");
//        return "index";
//    }
//    @RequestMapping("/testMap")
//    public String testMap(Map<String,Object> map){
//        map.put("time",new Date());
//        return "index";
//    }
//    @RequestMapping(value = "/testSessionAttribute",method = RequestMethod.POST)
//    public String testSessionAttribute(Map<String,Object> map, User user){
////        User u = new User("1","Tom");
////        User u = new User(user.getId(),user.getUsername());
////        u.setAddress(new Address("Sanya","Hainan"));
//        User u = new User(user.getId(),user.getUsername(),user.getAddress());
//        map.put("user",u);
//        System.out.println("u info: " + u );
//        return "index";
//    }
//    @RequestMapping(value = "/testSessionAttribute",method = RequestMethod.GET)
//    public String testSessionAttributeget(Map<String,Object> map){
//        User u = new User("1123","TomSON",new Address("Sanya","Hainan"));
//        u.setUsername("Tendy");
//        u.setAddress(new Address("Hubei","Wuhan"));
//        map.put("user",u);
//        System.out.println(u);
//        return "index";
//    }
////    @ModelAttribute
//    public void getUser(@RequestParam(value = "id",required = false)String id, Map<String,Object> map){
//        if(id != null){
//            User user = new User(id,"Micheal11111wqw",new Address("Guangzhou","Shanghai"));
//            map.put("user",user);
//            System.out.println("ModelAttribute Executing...");
//        }
//
//    }
//}
