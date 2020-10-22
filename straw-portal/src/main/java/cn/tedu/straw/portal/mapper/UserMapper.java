package cn.tedu.straw.portal.mapper;

import cn.tedu.straw.portal.model.Permission;
import cn.tedu.straw.portal.model.Role;
import cn.tedu.straw.portal.model.User;
import cn.tedu.straw.portal.vo.UserVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
<p>
    *  Mapper 接口 *
</p>
*
* @author tedu.cn
* @since 2020-10-15
*/
@Repository
public interface UserMapper extends BaseMapper<User> {
    @Select("select * from  user where username=#{username}")
    public User  findUserByUsername(String  username);
    /*
    select  p.id,p.name
    from  user u
    left join user_role ur on u.id=ur.user_id
    left join role r on ur.role_id=r.id
    left join role_permission rp on r.id=rp.role_id
    left join permission p on p.id=rp.permission_id
    where  u.id=1
    * */
    @Select("select  p.id,p.name\n" +
            "    from  user u\n" +
            "    left join user_role ur on u.id=ur.user_id\n" +
            "    left join role r on ur.role_id=r.id\n" +
            "    left join role_permission rp on r.id=rp.role_id\n" +
            "    left join permission p on p.id=rp.permission_id\n" +
            "    where  u.id=#{userId}")
    List<Permission>  findUserPermissionById(Integer userId);
    @Select("select  id,username,nickname from user  where  username=#{username}")
    UserVo  getUserVoByUsername(String  username);
    //根据用户的ID查询当前用户所有的角色
    @Select("select r.id,r.name from " +
            "user u left join user_role ur on u.id=ur.user_id " +
            "left join role r on ur.role_id=r.id " +
            "where u.id=#{userId}")
    List<Role>  findUserRolesById(Integer  userId);
}
