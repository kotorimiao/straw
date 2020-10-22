package cn.tedu.straw.portal.controller;


import cn.tedu.straw.portal.model.Tag;
import cn.tedu.straw.portal.service.ITagService;
import cn.tedu.straw.portal.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/v1/tags")
public class TagController {
    @Autowired
    private ITagService  tagService;
    /*
    * 请求地址：  /v1/tags
    * */
    @GetMapping("")
    public R<List<Tag>> tags(){
        List<Tag>  list=tagService.getTags();
        return  R.ok(list);
    }
}
