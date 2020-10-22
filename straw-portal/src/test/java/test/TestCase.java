package test;

import org.junit.jupiter.api.Test;

public class TestCase {
    @Test
    public  void testLombok(){
        Msg msg=new Msg();
        msg.setId(1);
        msg.setName("Tom");
        msg.setMessage("Hello");
        Msg msg1=new Msg();
        msg1.setId(1);
        msg1.setName("Tom");
        msg1.setMessage("Hello");
        Msg msg2=new Msg();
        msg2.setId(2);
        msg2.setName("Jack");
        msg2.setMessage("你好");
        System.out.println(msg);
        System.out.println(msg1);
        System.out.println(msg2);
        //目前相当于根据所有属性生成的equals方法
        System.out.println(msg.equals(msg1));
        System.out.println(msg.hashCode());
        System.out.println(msg1.hashCode());
        System.out.println(msg.equals(msg2));

    }
    @Test
    public void testLoggerDemo(){
        LoggerDemo  lg=new LoggerDemo();
    }
}
