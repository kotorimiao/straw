package cn.tedu.straw.portal.mapper;

import cn.tedu.straw.portal.model.Answer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
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
public interface AnswerMapper extends BaseMapper<Answer> {
    //根据问题的id查询出全部的答案  同时利用关联查询查询出每个答案的所有评论
    List<Answer>  findAnswersByQuestionId(Integer questionId);
    //
    @Update("UPDATE  answer set accept_status=#{acceptStatus}  where id=#{answerId} ")
    Integer updateAcceptStatus(@Param("answerId") Integer answerId, @Param("acceptStatus") Integer acceptStatus);
}
