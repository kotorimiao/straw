package cn.tedu.straw.portal.entity;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class User {
    private Integer id;
    private String username;
    private String nickname;
    private String password;
    private String sex;
    private LocalDate birthday;
    private String phone;
    private String classroomId;
    private LocalDateTime  createtime;//cretae_time---->createTime
    private Boolean  enabled;//是否被禁用
    private Boolean locked;//账号锁定
    private Boolean type;//账号类型
    private String selfIntroduction;//自述
}
