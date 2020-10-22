package cn.tedu.straw.portal;

import cn.tedu.straw.portal.model.Comment;
import cn.tedu.straw.portal.service.ICommentService;
import cn.tedu.straw.portal.vo.CommentVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class CommentServiceTest {
    @Autowired
    private ICommentService  commentService;
    @Test
    public  void   testSaveComment(){
        CommentVo  commentVo=new CommentVo()
                .setContent("这是一个测试又一个测试")
                .setAnswerId(89);
        String  username="st2";
        Comment  comment=commentService.saveComment(commentVo,username);
        System.out.println("OK");
    }
    @Test
    public  void   testRemoveComment(){
        Integer  commentId=27;
        String  username="wangkj";
        boolean  b=commentService.removeComment(commentId,username);
        System.out.println(b);
    }
}
