package cn.tedu.straw.portal.service;

import cn.tedu.straw.portal.model.Answer;
import cn.tedu.straw.portal.vo.AnswerVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tedu.cn
 * @since 2020-10-15
 */
public interface IAnswerService extends IService<Answer> {

    Answer  saveAnswer(AnswerVo answerVo,String username);
    //查询一个问题的全部回复
    List<Answer> getAnswersByQuestionId(Integer  questionId);

    /**
     * 接收答案，将答案状态更新，再将问题状态更新
     * @param answerId  答案Id
     * @return  更新成功返回true  否则返回false
     */
    boolean accept(Integer answerId);
}
