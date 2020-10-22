package cn.tedu.straw.portal.controller;


import cn.tedu.straw.portal.model.Answer;
import cn.tedu.straw.portal.model.Comment;
import cn.tedu.straw.portal.service.IAnswerService;
import cn.tedu.straw.portal.vo.AnswerVo;
import cn.tedu.straw.portal.vo.CommentVo;
import cn.tedu.straw.portal.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/v1/answers")
@Slf4j
public class AnswerController {
    @Autowired
    private IAnswerService  answerService;
    @PostMapping("")
    public R postAnswer(@Validated  AnswerVo  answerVo, BindingResult bindingResult,
                        @AuthenticationPrincipal User user){
        if (bindingResult.hasErrors()){
            String  message=bindingResult.getFieldErrors().toString();
            return  R.unproecsableEntity(message);
        }
        log.debug("表单数据{}",answerVo);
        Answer answer=answerService.saveAnswer(answerVo,user.getUsername());
        return  R.created(answer);
    }
    //完整请求路径/v1/answers/question/{id}
    //根据问题的编号获取全部回答列表
    @GetMapping("/question/{id}")
    public R<List<Answer>> questionAnswers(@PathVariable Integer id){
        if (id==null){
            return R.invalidRequest("必须包含问题编号！");
        }
        List<Answer>  answers=answerService.getAnswersByQuestionId(id);
        return R.ok(answers);
    }
    @GetMapping("/{answerId}/solved")
    public R solved(@PathVariable Integer answerId){
        log.debug("收到answerId{}",answerId);
        boolean  accept=answerService.accept(answerId);
        if (accept){
            return  R.accepted("接受了");
        }else{
            return R.gone("失败");
        }
    }

}
