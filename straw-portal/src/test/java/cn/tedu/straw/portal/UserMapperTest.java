package cn.tedu.straw.portal;

import cn.tedu.straw.portal.mapper.UserMapper;
import cn.tedu.straw.portal.model.Permission;
import cn.tedu.straw.portal.model.Role;
import cn.tedu.straw.portal.model.User;
import cn.tedu.straw.portal.vo.UserVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;
    @Test
    public  void  testFindUserByUsername(){
        User  user=userMapper.findUserByUsername("tc2");
        System.out.println(user);
    }
    @Test
    public  void  testFindUserPermissionById(){
        List<Permission> list=userMapper.findUserPermissionById(3);
        list.forEach(p-> System.out.println(p));
    }
    @Test
    public  void  testGetUserVoByUsername(){
       UserVo userVo= userMapper.getUserVoByUsername("st2");
        System.out.println(userVo);
    }
    @Test
    public  void  findUserRoleById(){
        List<Role>  roles=userMapper.findUserRolesById(11);
        roles.forEach(role -> System.out.println(role));
    }

}
