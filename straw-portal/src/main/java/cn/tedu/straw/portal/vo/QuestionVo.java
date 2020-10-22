package cn.tedu.straw.portal.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
@Data
@Accessors(chain = true)
public class QuestionVo implements Serializable {
    @NotBlank(message = "问题标题不能为空")
    @Pattern(regexp = "^.{3,50}$",message = "标题3到50个字符")
    private  String  title;
    private String[] tagNames={};
    private String []  teacherNicknames={};
    @NotBlank(message = "问题正文不能为空")
    private  String  content;
}
