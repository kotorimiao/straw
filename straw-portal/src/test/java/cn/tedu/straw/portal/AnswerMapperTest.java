package cn.tedu.straw.portal;

import cn.tedu.straw.portal.mapper.AnswerMapper;
import cn.tedu.straw.portal.model.Answer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class AnswerMapperTest {
    @Autowired
    private AnswerMapper answerMapper;
    @Test
    public  void  testFindAnswerByQuestionId(){
        List<Answer> answers=answerMapper.findAnswersByQuestionId(149);
        answers.forEach(answer -> System.out.println(answer));
    }
    @Test
    public void  updateAcceptStatus(){
        int rows=answerMapper.updateAcceptStatus(36,1);
        log.debug("{}",rows);
    }

}
