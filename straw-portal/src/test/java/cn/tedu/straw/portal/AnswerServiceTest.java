package cn.tedu.straw.portal;

import cn.tedu.straw.portal.model.Answer;
import cn.tedu.straw.portal.service.IAnswerService;
import cn.tedu.straw.portal.vo.AnswerVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class AnswerServiceTest {
    @Autowired
    private IAnswerService  answerService;
    @Test
    public   void  testSaveAnswer(){
        String  username="wangkj";
        AnswerVo answerVo=new AnswerVo()
                .setQuestionId(157)
                .setContent("哈哈哈哈哈哈，这个太简单啦！");
        Answer answer =answerService.saveAnswer(answerVo,username);
        log.debug("answer{}",answer);

    }
    @Test
    public  void testGetAnswersByQuestionId(){
        List<Answer>  answers=answerService.getAnswersByQuestionId(157);
        answers.forEach(answer -> log.debug("答案为{}",answer));
    }
    @Test
    public  void  accept(){
        int answerId=94;
        boolean  b=answerService.accept(answerId);
        System.out.println(b);
    }
}
