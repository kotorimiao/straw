package cn.tedu.straw.portal.controller;


import cn.tedu.straw.portal.model.Comment;
import cn.tedu.straw.portal.service.ICommentService;
import cn.tedu.straw.portal.vo.CommentVo;
import cn.tedu.straw.portal.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tedu.cn
 * @since 2020-10-15
 */
@RestController
@RequestMapping("/v1/comments")
@Slf4j
public class CommentController {
    @Autowired
    private ICommentService  commentService;
    @PostMapping("")
    public R<Comment> postComment(@Validated CommentVo  commentVo,
                                  BindingResult  result,
                                  @AuthenticationPrincipal UserDetails userDetails){
        if (result.hasErrors()){
            String message=result.getFieldError().getDefaultMessage();
            return R.invalidRequest(message);
        }
        String  username=userDetails.getUsername();
        Comment comment=commentService.saveComment(commentVo,username);
        log.debug("{}",commentVo);
        return  R.created(comment);
    }
    @GetMapping("/{id}/delete")
    public  R  removeComment(@PathVariable Integer id,@AuthenticationPrincipal UserDetails userDetails){
        log.debug("CommentId{}",id);
        boolean del=commentService.removeComment(id,userDetails.getUsername());
        if (del){
            return R.gone("删除了！");
        }else{
            return R.notFound("删除失败！");
        }

    }
    @PostMapping("/{id}/update")
    public R  updateComment(@Validated  CommentVo commentVo,
                            BindingResult  result,
                            @PathVariable Integer id,
                            @AuthenticationPrincipal UserDetails  user){
        if (result.hasErrors()){
            String  message=result.getFieldError().getDefaultMessage();
            return R.unproecsableEntity(message);
        }
        log.debug("CommentVo{}",commentVo);
        Comment  comment=commentService.updateComment(id,commentVo,user.getUsername());
        return  R.ok(comment);

    }
}
