package cn.tedu.straw.portal.service.impl;

import cn.tedu.straw.portal.mapper.QuestionMapper;
import cn.tedu.straw.portal.mapper.UserMapper;
import cn.tedu.straw.portal.model.Answer;
import cn.tedu.straw.portal.mapper.AnswerMapper;
import cn.tedu.straw.portal.model.Question;
import cn.tedu.straw.portal.model.User;
import cn.tedu.straw.portal.service.IAnswerService;
import cn.tedu.straw.portal.service.ServiceException;
import cn.tedu.straw.portal.vo.AnswerVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

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
public class AnswerServiceImpl extends ServiceImpl<AnswerMapper, Answer> implements IAnswerService {
    @Resource
    private UserMapper  userMapper;
    @Resource
    private AnswerMapper answerMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Override
    public Answer saveAnswer(AnswerVo answerVo, String username) {
        User user=userMapper.findUserByUsername(username);
        Answer  answer=new Answer()
                .setUserNickName(user.getNickname())
                .setUserId(user.getId())
                .setQuestId(answerVo.getQuestionId())
                .setContent(answerVo.getContent())
                .setLikeCount(0)
                .setCreatetime(LocalDateTime.now())
                .setAcceptStatus(0);//是否采纳  采纳为1  未采纳为0
        Integer rows=answerMapper.insert(answer);
        if (rows!=1){
            throw new ServiceException("数据库繁忙，请稍后再试！");
        }
        return answer;
    }

    @Override
    public List<Answer> getAnswersByQuestionId(Integer questionId) {
        if (questionId==null){
            throw ServiceException.notFound("questionId为空！");
        }
        List<Answer> answers=answerMapper.findAnswersByQuestionId(questionId);
        return answers;
    }

    @Override
    @Transactional//将事务进行添加
    public boolean accept(Integer answerId) {
        //查询出当前id对应的答案
        Answer  answer=answerMapper.selectById(answerId);
        if (answer==null){
            throw  ServiceException.notFound("没有找到数据");
        }
        //进入该判断表示已经被接收采纳了 则不处理
        if (answer.getAcceptStatus()==1){
            return false;
        }
        int  rows=answerMapper.updateAcceptStatus(answerId,1);
        if (rows!=1){
            throw ServiceException.busy();
        }
        rows=questionMapper.updateStatus(answer.getQuestId(), Question.SOLVED);
        if (rows!=1){
            throw ServiceException.busy();
        }
        log.debug("将问题和答案更新为已经解决的状态{}",answerId);
        return true;
    }
}
