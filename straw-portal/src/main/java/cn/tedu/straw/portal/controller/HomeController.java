package cn.tedu.straw.portal.controller;

import cn.tedu.straw.portal.service.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@Slf4j
public class HomeController {
    static  final GrantedAuthority  STUDENT=new SimpleGrantedAuthority("ROLE_STUDENT");
    static  final GrantedAuthority  TEACHER=new SimpleGrantedAuthority("ROLE_TEACHER");
    //显示首页   User  user注入spring-security中已经登录的用户信息  类型是org.springframework.security.core.userdetails.User;
    @GetMapping("/index.html")
    public ModelAndView showIndex(@AuthenticationPrincipal User  user){
        log.debug("当前登录的用户信息{}",user);
        if (user.getAuthorities().contains(STUDENT)){
            //检查当前用户是否是学生身份
            return new ModelAndView("index");
        }else  if (user.getAuthorities().contains(TEACHER)){
            //如果是讲师转发到讲师页面
            return new ModelAndView("index_teacher");
        }
        throw new ServiceException("需要登录！");
    }
    //显示提问界面
    @GetMapping("/question/create.html")
    public  ModelAndView showCreate(){
        return  new ModelAndView("/question/create");
    }
    //教师登录显示的页面
//    @GetMapping("/index_teacher.html")
//    public ModelAndView  indexTeacher(){
//        return  new ModelAndView("index_teacher");
//    }
    //显示问题详情页面
    @GetMapping("/question/detail.html")
    public  ModelAndView   detail(@AuthenticationPrincipal UserDetails  user){
        if (user.getAuthorities().contains(STUDENT)) {
            return new ModelAndView("/question/detail");
        }else if (user.getAuthorities().contains(TEACHER)){
            return new ModelAndView("/question/detail_teacher");
        }
        throw new  ServiceException("需要登录！");
    }
}
