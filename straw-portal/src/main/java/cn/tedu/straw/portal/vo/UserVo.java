package cn.tedu.straw.portal.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class UserVo implements Serializable {
    private  Integer id;
    private String username;
    private  String nickname;
    //用户提交的问题数量
    private Integer questions;
    //用户收藏的问题数量
    private Integer  collections;
}
