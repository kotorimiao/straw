package cn.tedu.straw.portal.service;

import cn.tedu.straw.portal.model.Question;
import cn.tedu.straw.portal.vo.QuestionVo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tedu.cn
 * @since 2020-10-15
 */
public interface IQuestionService extends IService<Question> {
    //显示当前学生的自己问题  获取当前用户的全部问题列表  返回值是当前用户的全部问题
    //pageNum当前页号   pageSize每页翻页时显示的数据条数
    PageInfo<Question> getMyQuestions(Integer  pageNum,Integer pageSize);
    //将用户提交的问题保存到数据库中 参数是用户提交到表单的数据
    void  saveQuestion(QuestionVo questionVo);
    //查询问题数量  根据用户ID统计其发布的问题数量  删除掉的不统计
    Integer countQuestionsByUserId(Integer userId);
    /**
     * 查询老师相关的问题
     *
     * */
    PageInfo<Question>  getQuestionsByTeacherName(String username,Integer  pageNum,Integer pageSize);
    //根据问题的Id  查询一个问题数据
    Question  getQuestionById(Integer id);
}
