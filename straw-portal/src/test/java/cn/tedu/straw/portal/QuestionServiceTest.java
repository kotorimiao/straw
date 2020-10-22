package cn.tedu.straw.portal;

import cn.tedu.straw.portal.model.Question;
import cn.tedu.straw.portal.service.IQuestionService;
import cn.tedu.straw.portal.vo.QuestionVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

@SpringBootTest
@Slf4j
public class QuestionServiceTest {
    @Autowired
    private IQuestionService questionService;
    @Test
    @WithMockUser(username = "st2",password = "888888")
    public  void getMyQuestions(){
        PageInfo<Question> pageInfo=questionService.getMyQuestions(1,8);
       // questions.forEach(question -> System.out.println(question));
        //System.out.println(pageInfo);
        pageInfo.getList().forEach(question -> System.out.println(question));
    }
    //测试保存问题表单到数据库
    @Test
    @WithMockUser(username = "st2",password = "888888")
    public  void  testSaveQuestion(){
        QuestionVo  questionVo=new QuestionVo();
        questionVo.setTitle("这是一个测试问题")
                .setContent("今天有点困")
                .setTagNames(new  String[]{"Java基础","面试题"})
                .setTeacherNicknames(new  String[]{"范传奇","王克晶"});
        questionService.saveQuestion(questionVo);
        System.out.println("OK！问题信息已保存");
    }
    @Test
    public  void  testCountQuestionsByUserId(){
        Integer count=questionService.countQuestionsByUserId(11);
        System.out.println(count);
    }
    @Test
    public  void  getQuestionsByTeacherName(){
        PageInfo<Question>  pageInfo=questionService.getQuestionsByTeacherName("wangkj",1,8);
        log.debug("pageInfo{}",pageInfo);
        pageInfo.getList().forEach(question -> log.debug("{}",question));
    }
    @Test
    public  void  getQuestionById(){
        Integer  id=149;
        Question question=questionService.getQuestionById(id);
        log.debug("question:{}",question);
    }
}
