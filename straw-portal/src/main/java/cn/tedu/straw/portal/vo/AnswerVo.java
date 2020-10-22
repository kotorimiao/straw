package cn.tedu.straw.portal.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
public class AnswerVo {
    @NotNull(message = "问题ID不能为空！")
    private Integer questionId;
    @NotBlank(message="回复正文不能为空！")
    private String content;

}
