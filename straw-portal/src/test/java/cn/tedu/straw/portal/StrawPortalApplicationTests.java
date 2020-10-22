package cn.tedu.straw.portal;


import cn.tedu.straw.portal.mapper.UserMapper;
import cn.tedu.straw.portal.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StrawPortalApplicationTests {
    @Autowired
    private UserMapper  userMapper;
    @Test
    void contextLoads() {
    }
    @Test
    public  void  testUserMapper(){
//        User user=userMapper.selectById(1);
//        System.out.println(user);
        User user=userMapper.selectById(1);
        System.out.println(user);

    }

}
