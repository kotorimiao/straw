package cn.tedu.straw.portal.service;

import cn.tedu.straw.portal.vo.R;

/*
* 业务层异常 包含异常好code
* */
public class ServiceException extends  RuntimeException{
    private int code=R.INTERNAL_SERVER_ERROR;

    public ServiceException(int code) {
        this.code = code;
    }

    public ServiceException(String message, int code) {
        super(message);
        this.code = code;
    }

    public ServiceException(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
    }

    public ServiceException(Throwable cause, int code) {
        super(cause);
        this.code = code;
    }

    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int code) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    //查询资源请求不存在进行的操作  get一个对象，但是没有这个对象的记录
    public static   ServiceException notFound(String message){
        return  new ServiceException(message, R.NOT_FOUND);
    }
    //请求的资源 服务端不存在 该资源已永久的被删除了
    public static ServiceException gone(String message){
        return new ServiceException(message,R.GONE);
    }
    //在收到的表单数据，无法进行处理的时候  抛出无法处理实体异常
    public  static ServiceException unproccesableEntity(String message){
        return new ServiceException(message,R.UNPROCESABLE_ENTITY);
    }

    public static ServiceException busy() {
        return  new ServiceException("数据库繁忙，请稍后重试！",R.INTERNAL_SERVER_ERROR);
    }

    public int getCode() {
        return code;
    }
}
