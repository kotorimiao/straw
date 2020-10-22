package cn.tedu.straw.portal.service;

import cn.tedu.straw.portal.model.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tedu.cn
 * @since 2020-10-15
 */
public interface ITagService extends IService<Tag> {
    List<Tag>  getTags();
    Map<String,Tag> getName2TagMap();
}
