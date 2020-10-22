package cn.tedu.straw.portal.service.impl;

import cn.tedu.straw.portal.mapper.QuestionTagMapper;
import cn.tedu.straw.portal.mapper.UserMapper;
import cn.tedu.straw.portal.mapper.UserQuestionMapper;
import cn.tedu.straw.portal.model.*;
import cn.tedu.straw.portal.mapper.QuestionMapper;
import cn.tedu.straw.portal.service.IQuestionService;
import cn.tedu.straw.portal.service.ITagService;
import cn.tedu.straw.portal.service.IUserService;
import cn.tedu.straw.portal.service.ServiceException;
import cn.tedu.straw.portal.vo.QuestionVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tedu.cn
 * @since 2020-10-15
 */
@Service
@Slf4j
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements IQuestionService {
    @Autowired
    private IUserService  userService;
    @Autowired
    private UserMapper  userMapper;
    @Autowired
    private  QuestionMapper  questionMapper;
    @Autowired
    private ITagService  tagService;
    @Autowired
    private QuestionTagMapper questionTagMapper;
    @Autowired
    private UserQuestionMapper  userQuestionMapper;
    @Override
    public PageInfo<Question> getMyQuestions(Integer pageNum, Integer pageSize) {
        if(pageNum==null || pageSize==null){
            throw  new ServiceException("翻页参数错误");
        }
        String username=userService.currentUserName();
        log.debug("当前用户{}",username);
        User  user=userMapper.findUserByUsername(username);
        log.debug("当前用户登录{}",user);
        if(user==null){
            throw ServiceException.notFound("登录用户没有找到！");
        }
        QueryWrapper<Question>  queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",user.getId());
        queryWrapper.eq("delete_status",0);
        queryWrapper.orderByDesc("createtime");
        PageHelper.startPage(pageNum,pageSize);
        List<Question>  questions=questionMapper.selectList(queryWrapper);
        log.debug("查询得到{}个数据",questions.size());
        for (Question q:questions) {
            List<Tag>  tags=tagNameToTags(q.getTagNames());
            q.setTags(tags);

        }
        return new PageInfo<>(questions);
    }

    @Override
    @Transactional//开启数据库事务  声明式事务 当前方法sql作为一个整体执行，执行失败会回滚，回到开始状态
    public void saveQuestion(QuestionVo questionVo) {
        /*存储问题*/
        String  userName=userService.currentUserName();
        //获取用户的全部信息
        User  user=userMapper.findUserByUsername(userName);
        log.debug("当前用户{}",user);
        //根据标签名数组生成标签列表
        StringBuilder  buf=new StringBuilder();
        for (String tagName : questionVo.getTagNames()) {
            buf.append(tagName).append(",");
        }
        String  tagNames=buf.deleteCharAt(buf.length()-1).toString();
        log.debug("标签列表{}",tagNames);
        //
        Question question=new Question();
        question.setTitle(questionVo.getTitle())
                .setContent(questionVo.getContent())
                .setStatus(0)//表示刚刚创建的问题 还没有老师回复
                .setPublicStatus(0)//0表示未公开 只能自己查看
                .setDeleteStatus(0)//0表示未删除 1表示删除
                .setPageViews(0)//问题被查看的次数
                .setCreatetime(LocalDateTime.now())//问题创建的时间  默认是当前时间
                .setModifytime(LocalDate.now())//问题的修改时间
                .setUserNickName(user.getNickname())
                .setUserId(user.getId())
                .setTagNames(tagNames)//问题相关的标签名列表
        ;
        log.debug("问题信息{}",question);
        int  row=questionMapper.insert(question);
        if (row!=1){
            throw  new  ServiceException("数据库繁忙，请稍后再试！");
        }
        //插入标签表
        Map<String,Tag>  name2TagMap=tagService.getName2TagMap();
        //保存问题和标签的关系
        /*根据标签名找到标签ID，问题ID和标签ID存储到questionTag*/
        for (String tagName : questionVo.getTagNames()) {
            //根据标签名查找对应的标签信息
            Tag tag=name2TagMap.get(tagName);
            if(tag==null){
                throw  ServiceException.unproccesableEntity("标签名错误！");
            }
            QuestionTag questionTag=
                    new QuestionTag()
                    .setQuestionId(question.getId())
                    .setTagId(tag.getId());
            //将标签和问题对应关系插入到数据库
            row=questionTagMapper.insert(questionTag);
            if (row!=1){
                throw  new  ServiceException("数据库繁忙，请稍后再试！");
            }
        }
        //插入问题以及答疑老师
        Map<String,User>  masterMap=userService.getMasterMap();
        for (String nickname : questionVo.getTeacherNicknames()) {
            //根据名字找到老师所有信息
            User master=masterMap.get(nickname);
            if(master==null){
                throw  ServiceException.unproccesableEntity("讲师名字错误！");
            }
            UserQuestion  userQuestion=new UserQuestion()
                    .setQuestionId(question.getId())
                    .setUserId(master.getId())
                    .setCreatetime(LocalDateTime.now());
            row=userQuestionMapper.insert(userQuestion);
            if (row!=1){
                throw  new  ServiceException("数据库繁忙，请稍后再试！");
            }
        }
    }
//查询问题数量的实现类
    @Override
    public Integer countQuestionsByUserId(Integer userId) {
        QueryWrapper<Question>  queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        queryWrapper.eq("delete_status",0);
        //mybatis  plus提供的查询的方法 专门用于计算数量 或者统计计算数量
        Integer  count=questionMapper.selectCount(queryWrapper);
        return count;
    }

    @Override
    public PageInfo<Question> getQuestionsByTeacherName(String username, Integer pageNum, Integer pageSize) {
        if (username==null){
            throw  ServiceException.notFound("用户名不能为空！");
        }
        if (pageNum==null){
            pageNum=1;
        }
        if (pageSize==null){
            pageSize=8;
        }
        User user=userMapper.findUserByUsername(username);
        PageHelper.startPage(pageNum,pageSize);
        List<Question>  questions=questionMapper.findTeachersQuestions(user.getId());

        for (Question  q:questions) {
            List<Tag> tags=tagNameToTags(q.getTagNames());
            q.setTags(tags);
        }
        return new PageInfo<>(questions);
    }

    @Override
    public Question getQuestionById(Integer id) {
        Question  question=questionMapper.selectById(id);
        //填充tags属性
        List<Tag>  tags=tagNameToTags(question.getTagNames());
        question.setTags(tags);
        return question;
    }

    //将标签名列表转换为标签列表集合
    private   List<Tag>  tagNameToTags(String  tagNames){
        String[]  names=tagNames.split(",\\s?");
        Map<String,Tag>  name2TagMap=tagService.getName2TagMap();
        List<Tag>  tags=new ArrayList<>();
        for (String name:names) {
            Tag  tag=name2TagMap.get(name);
            tags.add(tag);
        }
        return tags;
    }
}
