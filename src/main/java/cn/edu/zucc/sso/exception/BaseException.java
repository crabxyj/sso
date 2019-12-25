package cn.edu.zucc.sso.exception;

import lombok.Getter;

/**
 * @author crabxyj
 * @date 2019/12/23 17:43
 */
@Getter
public class BaseException extends Exception {
    private int code;
    private String msg;

    public BaseException(int code,String msg){
        super(msg);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
