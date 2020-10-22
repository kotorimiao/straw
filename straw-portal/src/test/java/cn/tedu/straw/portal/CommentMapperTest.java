package cn.tedu.straw.portal;

import cn.tedu.straw.portal.mapper.CommentMapper;
import cn.tedu.straw.portal.model.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CommentMapperTest {
    @Autowired
    private CommentMapper  commentMapper;
    @Test
    public void  testComment(){
        Comment comment=commentMapper.selectById(10);
        System.out.println(comment);
    }
}
