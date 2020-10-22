package cn.tedu.straw.portal.service;

import cn.tedu.straw.portal.mapper.UserMapper;
import cn.tedu.straw.portal.model.User;
import cn.tedu.straw.portal.vo.RegisterVo;
import cn.tedu.straw.portal.vo.UserVo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tedu.cn
 * @since 2020-10-15
 */
public interface IUserService extends IService<User> {
    //根据用户名获取用户的详细信息  用于spring  Security的登录操作  登录时SpringSecurity会将登录
    //用户名传递到getUserDetails方法，此方法就会去数据库查找用户信息  返回UserDetails
    UserDetails  getUserDetails(String username);
    //注册学生的方法
    void  registerStudent(RegisterVo registerVo);
    //从Spring  Security中获取当前用户名
    String  currentUserName();
    //获取所有答疑老师的方法
    List<User> getMasters();

    //返回缓存的全部老师昵称和老师信息
    Map<String,User> getMasterMap();
    /**获取当前用户信息  返回值是当前用户信息
   用于在页面上显示当前用户信息  信息包括用户发布过的问题数量  用户收藏的问题的数量*/
    UserVo  getCurrentUserVo();

}
