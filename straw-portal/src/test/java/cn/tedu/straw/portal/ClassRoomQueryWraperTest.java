package cn.tedu.straw.portal;

import cn.tedu.straw.portal.mapper.ClassroomMapper;
import cn.tedu.straw.portal.model.Classroom;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ClassRoomQueryWraperTest {
    @Autowired
    ClassroomMapper  classroomMapper;
    @Test
    public void queryWrapper(){
        QueryWrapper<Classroom> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("invite_code","JSD2002-525416");
        //执行mybatis查询生成器  提供的查询方法
        Classroom  classroom=classroomMapper.selectOne(queryWrapper);
        System.out.println(classroom);
    }
}
