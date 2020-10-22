package cn.tedu.straw.portal;

import cn.tedu.straw.portal.model.User;
import cn.tedu.straw.portal.service.IQuestionService;
import cn.tedu.straw.portal.service.IUserService;
import cn.tedu.straw.portal.vo.RegisterVo;
import cn.tedu.straw.portal.vo.UserVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class UserServiceImplTest {
    @Autowired
    private IUserService  userService;
    @Test
    public  void  testGetUserDetails(){
        UserDetails userDetails=userService.getUserDetails("tc2");
        System.out.println(userDetails);
    }
    @Test
    public  void  registerStudent(){
        RegisterVo  register=new RegisterVo();
        register.setInviteCode("JSD2003-005803");
        register.setPhone("13244624451");
        register.setNickname("刘东梅大傻子");
        register.setPassword("123456");
        register.setConfirm("123456");

        userService.registerStudent(register);
        System.out.println("注册成功！");
    }
    //离线测试当前用户名获取
    @Test
    @WithMockUser(username = "st1",password = "888888")
    public  void testCurrentUserName(){
        String username=userService.currentUserName();
        System.out.println(username);
    }
    //测试获取答疑老师
    @Test
    public  void  testGetMasters(){
        List<User> masters=userService.getMasters();
        masters.forEach(master-> System.out.println(master));
    }
    @Test
    public  void  getMasterMap(){
        Map<String,User> map=userService.getMasterMap();
        map.forEach((nickname,master)->System.out.println(nickname+":"+master));
    }
    @Test
    @WithMockUser(username = "st2",password = "888888")
    public  void   testgetCurrentVo(){
        UserVo  userVo=userService.getCurrentUserVo();
        System.out.println(userVo);
    }

}
