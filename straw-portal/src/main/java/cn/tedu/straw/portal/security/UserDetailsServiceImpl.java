package cn.tedu.straw.portal.security;

import cn.tedu.straw.portal.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private IUserService  userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        UserDetails user=null;
//        if("Jerry".equals(username)){
//            user= User.builder()
//                    .username("Jerry")
//                    .password("{bcrypt}$2a$10$6raiOCdTEnJLJ/4cqu1.z.h8z7Nv7UIfWTtnzlWy3zxluDyBz8KF2")
//                    .authorities("/user/get")
//                    .build();
//        }
        //根据用户名到数据库中查找用户信息
        return userService.getUserDetails(username);
    }
}
