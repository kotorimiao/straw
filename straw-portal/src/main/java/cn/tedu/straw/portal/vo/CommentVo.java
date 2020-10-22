package cn.tedu.straw.portal.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
@Data
@Accessors(chain = true)
public class CommentVo implements Serializable {
    @NotNull(message="答案ID不能为空！")
    private  Integer  answerId;
    @NotBlank(message = "问题内容不能为空！")
    private  String   content;
}
