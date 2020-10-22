package test;

import lombok.Data;

@Data //利用lombok@Data注解生成Bean
public class Msg {
    private int id;
    private String name;
    private String message;
    
}
