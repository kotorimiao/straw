package cn.tedu.straw.portal.controller;

import cn.tedu.straw.portal.service.IUserService;
import cn.tedu.straw.portal.service.ServiceException;
import cn.tedu.straw.portal.vo.R;
import cn.tedu.straw.portal.vo.RegisterVo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.sql.rowset.serial.SerialException;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@RestController
@Slf4j
public class SystemController {
    @Autowired
    private IUserService  userService;
    //读取自定义配置信息
    @Value("${straw.resource.path}")
    private  File   resourcePath;
    @Value("${straw.resource.host}")
    private  String   resourceHost;
    //登录页面
    @GetMapping("/login.html")
    public ModelAndView  login(){
        return new ModelAndView("login");
    }
    //请求注册页面
    @GetMapping("/register.html")
    public ModelAndView  register(){
        return  new ModelAndView("register");
    }
    //接受注册请求
    @PostMapping("/register")
    //加上@Validated后就开启了对RegisterVo对象进行数据检验 验证结果会自动存储到BindingResult
    public R registerStudent(@Validated RegisterVo registerVo, BindingResult validaResult){
        log.debug("收到表单数据:{}",registerVo);
        //检查验证结果中是否有错误
        if(validaResult.hasErrors()){//hasErrors用于检查是否有验证错误
            //拿到验证结果中的错误
            String error=validaResult.getFieldError().getDefaultMessage();
            log.info("表单验证错误：{}",error);
            return  R.unproecsableEntity(error);
        }
        //检验表单中的密码是否一致
        if(!registerVo.getPassword().equals(registerVo.getConfirm())){
            log.info("确认密码不一致！");
            return  R.unproecsableEntity("确认密码不一致！");
        }
            userService.registerStudent(registerVo);
            return R.created("创建成功！");

    }
    //处理文件上载请求
    @PostMapping("/upload/file")
    public  R  uploadFile(MultipartFile  imageFile) throws IOException {
        //展示一下收到的文件名  获得文件名
        String  fileName=imageFile.getOriginalFilename();
        //存储位置
        String path="/Users/lindefu/test/";
        File  folder=new File(path);
        folder.mkdir();//创建文件夹
        //依据文件名在指定位置创建文件
        File file=new File(folder,fileName);
        imageFile.transferTo(file);
        return  R.ok("成功保存文件");
    }
    @PostMapping("/upload/image")
    public  R  uploadImage(MultipartFile  imageFile) throws IOException {
        //创建目标存储目录
        String path= DateTimeFormatter.ofPattern("yyyy/MM/dd").format(LocalDate.now());
        File  folder=new File(resourcePath,path);
        folder.mkdirs();
        log.debug("存储文件位置{}",folder);
        //获取文件拓展名
        String  filename=imageFile.getOriginalFilename();
        String  ext=filename.substring(filename.lastIndexOf("."));
        log.debug("文件拓展名{}",ext);
        //生成随机的文件名
        String name= UUID.randomUUID().toString()+ext;
        //保存生成的文件名
        File  file=new File(folder,name);
        log.debug("保存到：{}",file.getAbsolutePath());
        //保存文件
        imageFile.transferTo(file);
        //文件的显示URL
        String url=resourceHost+"/"+path+"/"+name;
        log.debug("Image  URL{}",url);
        return R.ok(url);
    }

}
