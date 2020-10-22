package cn.tedu.straw.portal.service.impl;

import cn.tedu.straw.portal.mapper.ClassroomMapper;
import cn.tedu.straw.portal.mapper.UserRoleMapper;
import cn.tedu.straw.portal.model.*;
import cn.tedu.straw.portal.mapper.UserMapper;
import cn.tedu.straw.portal.service.IQuestionService;
import cn.tedu.straw.portal.service.IUserService;
import cn.tedu.straw.portal.service.ServiceException;
import cn.tedu.straw.portal.vo.RegisterVo;
import cn.tedu.straw.portal.vo.UserVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sun.plugin.liveconnect.SecurityContextHelper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tedu.cn
 * @since 2020-10-15
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ClassroomMapper classroomMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private IQuestionService questionService;


    //密码加密类
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final List<User> masters = new CopyOnWriteArrayList<>();
    private final Map<String, User> masterMap = new ConcurrentHashMap<>();
    private final Timer timer = new Timer();

    //代码块在创建对象的时候执行  清除缓存
    {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                synchronized (masters) {
                    masters.clear();
                    masterMap.clear();
                }
            }
        }, 1000 * 60 * 60, 1000 * 60 * 60);
    }

    @Override
    public UserDetails getUserDetails(String username) {
        User user = userMapper.findUserByUsername(username);
        if (user == null) {
            return null;
        }
        //添加全部的权限信息
        List<Permission> permissions = userMapper.findUserPermissionById(user.getId());
        //该数组目的是装下所有权限 因为后续.authorities(arr)设置权限需要传入一个数组
        String[] arr = new String[permissions.size()];
        int i = 0;
        for (Permission p : permissions) {
            arr[i++] = p.getName();
        }
        //添加全部的角色信息
        List<Role>  roles=userMapper.findUserRolesById(user.getId());
        arr= Arrays.copyOf(arr,arr.length+roles.size());
        for (Role role:roles) {
            arr[i++]=role.getName();
        }
        //创建用户详情对象
        UserDetails u = org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(arr)
                .disabled(user.getEnabled() == 0)//是否允许  允许为0  不允许为1
                .accountLocked(user.getLocked() == 1)//从数据库中获取当前用户是否被锁定  禁用为0  未禁用1
                .build();
        return u;
    }

    //为了让方法更加健壮，在方法开始都会检查方法的参数
    //如果不检查参数，可能会发生空指针异常等问题
    @Override
    public void registerStudent(RegisterVo registerVo) {
        //参数检查
        if (registerVo == null) {
            log.info("方法参数为null");
            throw ServiceException.unproccesableEntity("参数为空！");
        }
        log.debug("方法参数：{}", registerVo);
        //检查邀请码
        log.debug("验证邀请码： {}", registerVo.getInviteCode());
        QueryWrapper<Classroom> query = new QueryWrapper<>();
        query.eq("invite_code", registerVo.getInviteCode());//条件
        Classroom classroom = classroomMapper.selectOne(query);
        if (classroom == null) {
            log.info("验证邀请码失败！");
            throw ServiceException.notFound("无效邀请码！请联系任课老师获取！");
        }
        //验证手机号 手机号就是用户名
        log.debug("验证手机号码是否使用{}", registerVo.getPhone());
        User user = userMapper.findUserByUsername(registerVo.getPhone());
        if (user != null) {
            log.info("手机号已经注册！");
            throw ServiceException.unproccesableEntity("手机号码已经被使用过！");
        }
        //将用户存入系统中
        user = new User();
        user.setUsername(registerVo.getPhone());
        user.setNickname(registerVo.getNickname());
        String password = passwordEncoder.encode(registerVo.getPassword());
        user.setPassword("{bcrypt}" + password);
        user.setPhone(registerVo.getPhone());
        user.setLocked(0);
        user.setEnabled(1);
        user.setCreatetime(LocalDateTime.now());
        user.setBirthday(null);
        //返回值为受到影响的行数
        int row = userMapper.insert(user);
        if (row != 1) {
            log.info("保存用户信息失败！");
            throw new ServiceException("服务器繁忙，请稍后重试！");
        }
        log.debug("保存数据 user数据{}", user);
        log.debug("设置用户是一个学生角色");
        //Role的ID为2是学生角色  只需要在用户角色表中插入一行数据即可
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleId(2);
        row = userRoleMapper.insert(userRole);
        if (row != 1) {
            log.info("保存用户角色信息失败！");
            throw new ServiceException("数据库繁忙，请稍后重试！");
        }
        log.debug("设置用户的角色{}", userRole);
    }

    //获得用户名实现  使用spring  Security获取
    @Override
    public String currentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            return username;
        }
        throw ServiceException.notFound("还没有登录！");
    }

    @Override
    public List<User> getMasters() {
        if (masters.isEmpty()) {
            synchronized (masters) {
                if (masters.isEmpty()) {
                    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
                    //user表中属性为1的都是答疑老师
                    queryWrapper.eq("type", 1);//type0学生  type1老师
                    List<User> list = userMapper.selectList(queryWrapper);
                    //初始化masters缓存
                    masters.addAll(list);
                    //初始化masterMap
                    masters.forEach(master -> masterMap.put(master.getNickname(), master));
                    //清除敏感信息
                    masters.forEach(master -> master.setPassword(""));

                }
            }
        }
        return masters;
    }

    @Override
    public Map<String, User> getMasterMap() {
        if(masterMap.isEmpty()){
            getMasters();
        }
        return masterMap;
    }
//查询相关用户信息 提问回答收藏数量的方法
    @Override
    public UserVo getCurrentUserVo() {
        String  username=currentUserName();
        UserVo  userVo=userMapper.getUserVoByUsername(username);
        Integer  questions=questionService.countQuestionsByUserId(userVo.getId());
        userVo.setQuestions(questions).setCollections(0);//收藏以后做
        return userVo;
    }
}
