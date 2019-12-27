package cn.edu.zucc.sso.exception;

/**
 * @author crabxyj
 * @date 2019/12/23 17:43
 */
public class BaseException extends Exception {

    public BaseException(String msg){
        super(msg);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
