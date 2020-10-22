package cn.tedu.straw.portal.controller;


import cn.tedu.straw.portal.model.Question;
import cn.tedu.straw.portal.service.IQuestionService;
import cn.tedu.straw.portal.service.ServiceException;
import cn.tedu.straw.portal.vo.QuestionVo;
import cn.tedu.straw.portal.vo.R;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tedu.cn
 * @since 2020-10-15
 */
@RestController
@Slf4j
@RequestMapping("/v1/questions")
public class QuestionController {
    @Autowired
    private IQuestionService  questionService;
        /*
        获取当前用户的全部问题
        请求url  /v1/questions/my */
    @GetMapping("/my")
    public R<PageInfo<Question>> my(Integer  pageNum){
        if(pageNum==null){
            pageNum=1;
        }
        Integer   pageSize=8;
        try {
            log.debug("开始请求当前用户的所有问题");
            PageInfo<Question> pageInfo = questionService.getMyQuestions(pageNum,pageSize);
            return R.ok(pageInfo);
        }catch (ServiceException e){
            log.error("获取用户问答失败！{}",e);
            return  R.failed(e);
        }
    }
    //新增问题的方法
    @PostMapping("")
    public R<String>  createQuestion(@Validated QuestionVo  questionVo,
                                     BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            String   message=bindingResult.getFieldError().getDefaultMessage();
            return  R.unproecsableEntity(message);
        }
        if (questionVo.getTagNames().length==0){
            return  R.unproecsableEntity("标签名不能为空！");
        }
        if (questionVo.getTeacherNicknames().length==0){
            return  R.unproecsableEntity("没有选择答疑老师！");
        }
        log.debug("收到表单信息{}",questionVo);

            questionService.saveQuestion(questionVo);
            return  R.created("成功保存问题数据！");
    }
    //请求/v1/questions/teacher 分页返回当前老师有关的问题  参数是当前老师  页号  返回值是一页数据
    @GetMapping("/teacher")
    @PreAuthorize("hasRole('TEACHER')")
    public  R<PageInfo<Question>>  teachers(@AuthenticationPrincipal User user,Integer  pageNum){
        if(pageNum==null){
            pageNum=1;
        }
        Integer  pageSize=8;
        PageInfo<Question>  pageInfo=questionService.getQuestionsByTeacherName(
                user.getUsername(),pageNum,pageSize
        );
        return  R.ok(pageInfo);
    }
    /**
     * Rest风格
     * */
    @GetMapping("/{id}")
    public  R<Question>  question(@PathVariable  Integer  id){
        if(id==null){
            return R.invalidRequest("ID不能为空！");
        }
        log.debug("id:{}",id);
        Question question=questionService.getQuestionById(id);
        return  R.ok(question);
    }
}
