package cn.tedu.straw.portal.controller;

import cn.tedu.straw.portal.service.ServiceException;
import cn.tedu.straw.portal.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
/*负责统一的异常处理*/
public class ExceptionController {
    //处理业务异常
    @ExceptionHandler
    public R handleServiceException(ServiceException  e){
        log.debug("处理业务异常");
        log.error("业务异常",e);
        return  R.failed(e);
    }
    //处理其他异常
    @ExceptionHandler
    public  R handleException(Exception  e){
        log.debug("处理其他异常");
        log.error("其他异常",e);
        return  R.failed(e);
    }
}
