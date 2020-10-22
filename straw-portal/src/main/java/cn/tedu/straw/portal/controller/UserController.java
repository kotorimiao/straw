package cn.tedu.straw.portal.controller;


import cn.tedu.straw.portal.model.User;
import cn.tedu.straw.portal.service.IUserService;
import cn.tedu.straw.portal.vo.R;
import cn.tedu.straw.portal.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tedu.cn
 * @since 2020-10-15
 */
@RestController
@RequestMapping("/v1/users")
public class UserController {
    @Autowired
    IUserService  userService;

   @GetMapping("/masters")
    public R<List<User>> masters(){
       List<User>  masters=userService.getMasters();
       masters.forEach(master->master.setPassword(""));
       return  R.ok(masters);
   }
   //获取当前用户信息
    @GetMapping("/me")
    public  R<UserVo>  me(){
       UserVo userVo=userService.getCurrentUserVo();
       return R.ok(userVo);
    }

}
