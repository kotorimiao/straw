package cn.tedu.straw.portal.service;

import cn.tedu.straw.portal.model.Comment;
import cn.tedu.straw.portal.vo.CommentVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tedu.cn
 * @since 2020-10-15
 */
public interface ICommentService extends IService<Comment> {
    /**
     * 将用户发起的评论讯息保存起来
     * @param 评论表单  以及发起评论的用户名
     *
     * */
    Comment saveComment(CommentVo  commentVo,String username);

    /**
     * 根据评论的Id删除掉评论
     * 老师可以删除掉学生的评论
     * 学生只能删除掉自己的评论
     * @param commentId
     * @param username
     * @return
     */
    boolean  removeComment(Integer  commentId,String username);

    /**
     * 更新一个评论
     * @param commentId  评论id
     * @param commentVo 评论表单内容
     * @param username  发起修改评论的用户
     * @return
     */
    Comment  updateComment(Integer commentId,CommentVo commentVo,String  username);

}
