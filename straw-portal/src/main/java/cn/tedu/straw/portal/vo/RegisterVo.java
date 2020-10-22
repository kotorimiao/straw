package cn.tedu.straw.portal.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
@Data
public class RegisterVo implements Serializable {
    @NotBlank(message = "邀请码不能为空")
    private String inviteCode;
    @NotBlank(message = "电话号码不能为空")
    @Pattern(regexp = "^1\\d{10}$",message = "电话号码不符合规则")
    private String phone;
    @NotBlank(message = "昵称不能为空")
    @Pattern(regexp = "^.{2,20}$",message = "昵称2-20个字符")
    private String nickname;
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^\\w{6,20}$",message ="密码6-20个字母，数字，字母，_组成" )
    private String password;
    @NotBlank(message = "确认密码不能为空")
    private String confirm;

}
