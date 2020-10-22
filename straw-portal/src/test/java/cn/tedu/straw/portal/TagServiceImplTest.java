package cn.tedu.straw.portal;

import cn.tedu.straw.portal.model.Tag;
import cn.tedu.straw.portal.service.ITagService;
import com.sun.xml.internal.rngom.digested.DValuePattern;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class TagServiceImplTest {
    @Autowired
    private ITagService  tagService;
    @Test
    public void getTags(){
        List<Tag> list=tagService.getTags();
        list.forEach(tag->System.out.println(tag));
    }
    //测试name  对应tag是否成功
    @Test
    public  void  testName2TagMap(){
        Map<String,Tag> map=tagService.getName2TagMap();
        map.forEach((name,tag)-> System.out.println(name+":"+tag));
    }
}
