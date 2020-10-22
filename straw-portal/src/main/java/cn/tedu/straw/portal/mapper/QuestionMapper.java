package cn.tedu.straw.portal.mapper;

import cn.tedu.straw.portal.model.Question;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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
public interface QuestionMapper extends BaseMapper<Question> {
    //查询老师的相关问题  包括老师自问自答  学生提问的问题  数组式写法
    @Select({"select q.* from question q  join  user_question uq  on q.id=uq.question_id " ,
            "where q.user_id=#{userId}  or  uq.user_id=#{userId}",
            " order by  q.modifytime  desc"})
    List<Question>  findTeachersQuestions(Integer userId);
    //更新问题状态
    @Update("update question set status=#{status} where id=#{questionId}")
    Integer  updateStatus(@Param("questionId") Integer questionId, @Param("status") Integer status);
}
