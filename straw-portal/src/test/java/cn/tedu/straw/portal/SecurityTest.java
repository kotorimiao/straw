package cn.tedu.straw.portal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootTest
public class SecurityTest {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Test
    public void testPasswordEncoder(){
        /*
        * 每次生成的密文都不一样，原因在于使用了一个抖动算法
        * 该加密还是个慢速算法  防止暴力破解
        * $2a$10$6raiOCdTEnJLJ/4cqu1.z.h8z7Nv7UIfWTtnzlWy3zxluDyBz8KF2
        *测试密码加密功能
        * */
        String pwd=passwordEncoder.encode("123456");
        System.out.println(pwd);
    }
    @Test
    public  void  testMatchPasswordEncoder(){
        /*
        * 测试密码密文比对功能
        * $2a$10$6raiOCdTEnJLJ/4cqu1.z.h8z7Nv7UIfWTtnzlWy3zxluDyBz8KF2
        * */
        boolean  b=passwordEncoder.matches("12345",
                "$2a$10$6raiOCdTEnJLJ/4cqu1.z.h8z7Nv7UIfWTtnzlWy3zxluDyBz8KF2");
        System.out.println(b);
    }
}
