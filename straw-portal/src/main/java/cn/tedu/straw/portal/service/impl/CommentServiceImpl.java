package cn.tedu.straw.portal.service.impl;

import cn.tedu.straw.portal.mapper.UserMapper;
import cn.tedu.straw.portal.model.Comment;
import cn.tedu.straw.portal.mapper.CommentMapper;
import cn.tedu.straw.portal.model.User;
import cn.tedu.straw.portal.service.ICommentService;
import cn.tedu.straw.portal.service.ServiceException;
import cn.tedu.straw.portal.util.Keys;
import cn.tedu.straw.portal.vo.CommentVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tedu.cn
 * @since 2020-10-15
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {
    @Resource
    private UserMapper  userMapper;
    @Resource
    private   CommentMapper commentMapper;
    @Override
    @Transactional
    public Comment saveComment(CommentVo commentVo, String username) {
        User  user=userMapper.findUserByUsername(username);
        Comment  comment=new Comment()
                .setUserNickName(user.getNickname())
                .setUserId(user.getId())
                .setContent(commentVo.getContent())
                .setCreatetime(LocalDateTime.now())
                .setAnswerId(commentVo.getAnswerId());
        int rows=commentMapper.insert(comment);
        if (rows!=1){
            throw ServiceException.busy();
        }
        return comment;
    }

    @Override
    public boolean removeComment(Integer commentId, String username) {
        User  user=userMapper.findUserByUsername(username);
        //type的值是1  表示是老师
        if (user.getType().equals(Keys.MASTER)){
            int row=commentMapper.deleteById(commentId);
            return row==1;
        }
        Comment comment=commentMapper.selectById(commentId);
        if (comment.getUserId().equals(user.getId())){
            int row=commentMapper.deleteById(commentId);
            return row==1;
        }
        throw new ServiceException("权限不足！");
    }

    @Override
    @Transactional
    public Comment updateComment(Integer commentId, CommentVo commentVo, String username) {
        User  user=userMapper.findUserByUsername(username);
        Comment  comment=commentMapper.selectById(commentId);
        if (user.getType().equals(Keys.MASTER)||comment.getUserId().equals(user.getId())){
            comment.setContent(commentVo.getContent());
            int rows=commentMapper.updateById(comment);
            if (rows!=1){
                throw  ServiceException.busy();
            }
            return comment;
        }
        throw new  ServiceException("权限不足！");
    }
}
