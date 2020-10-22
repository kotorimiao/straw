package cn.tedu.straw.portal;

import cn.tedu.straw.portal.mapper.QuestionMapper;
import cn.tedu.straw.portal.model.Question;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

@SpringBootTest
public class QuestionMapperTest {
    @Autowired
    QuestionMapper questionMapper;
    @Test
    public  void   findTeahcersQuestoons(){
        Integer  userId=4;
        List<Question> questions=questionMapper.findTeachersQuestions(userId);
        questions.forEach(question -> System.out.println(question));
    }
    @Test
    public  void updateStatus(){
        int rows=questionMapper.updateStatus(161,Question.SOLVING);
        System.out.println(rows);
    }
}
