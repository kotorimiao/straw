package cn.tedu.straw.portal.service.impl;

import cn.tedu.straw.portal.model.Tag;
import cn.tedu.straw.portal.mapper.TagMapper;
import cn.tedu.straw.portal.service.ITagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

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
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {
    //是线程安全的list  适合并发访问场合   final避免修改
    private final  List<Tag>  tags=new CopyOnWriteArrayList<>();
    private final Map<String,Tag>  name2TagMap=new ConcurrentHashMap<>();
    @Override
    public List<Tag> getTags() {
        //此处目的提高性能
        if(tags.isEmpty()){
            synchronized (tags) {
                if (tags.isEmpty()) {
                //list（）是mabatis plus提供的方法  继承自serviceImpl的方法
                    //方法作用就是返回全部的tags对象
                    tags.addAll(list());
                    tags.forEach(tag -> name2TagMap.put(tag.getName(),tag));
                    log.debug("加载tag列表{}",tags);
                    log.debug("加载了Map{}",name2TagMap);
                }
            }
        }
        return tags;
    }

    @Override
    public Map<String, Tag> getName2TagMap() {
        if(tags.isEmpty()){
            getTags();
        }
        return name2TagMap;
    }
}
