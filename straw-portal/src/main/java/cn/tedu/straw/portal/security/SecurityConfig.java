package cn.tedu.straw.portal.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
//开启全局安全方法访问权限
@EnableGlobalMethodSecurity(prePostEnabled = true)
//加密配置    集成web安全配置适配器
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Bean
//    public PasswordEncoder  passwordEncoder(){
//        return  new BCryptPasswordEncoder();
//    }
     @Autowired
     UserDetailsServiceImpl  userDetailsService;
    //重写权限配置方法
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //添加了测试用户  Tom  123456  权限是/user/get
//        auth.inMemoryAuthentication()
//                .withUser("Tom")
//                .password("{bcrypt}$2a$10$6raiOCdTEnJLJ/4cqu1.z.h8z7Nv7UIfWTtnzlWy3zxluDyBz8KF2")
//                .authorities("/user/get","/user/list");

        auth.userDetailsService(userDetailsService);
    }
    //重写控制授权范围的方法
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()//在任何请求之前关闭 跨站攻击
                .authorizeRequests()//对请求request进行授权athorize
                //匹配"/index.html"并且全部允许（permit  all）
                .antMatchers(
                        "/index.html",
                        "/img/*",
                        "/js/*",
                        "/css/*",
                        "/browser_components/**",
                        "/login.html",//登录页不登录也可以看
                        "/register.html",
                        "/register"
                )
                .permitAll()
                //其他的请求需要认证authenticated
                .anyRequest()
                .authenticated()
                //采用表单进行认证
                //登录页面设置
                .and().formLogin()
                .loginPage("/login.html")  //登录页面设置 请求的是/login.html
                .loginProcessingUrl("/login")//设置请求路径 需要和表单中的action属性一致
                .failureUrl("/login.html?error")//设置失败请求页面
                .defaultSuccessUrl("/index.html")//设置成功访问页面
                .and() //并且设置登出
                .logout()
                .logoutUrl("/login.html?logout");
    }
}
