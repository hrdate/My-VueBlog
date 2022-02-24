package com.vueblog.common.lang;

import java.io.Serializable;

public class TResult<T> implements Serializable {
    private Integer code;
    private String message;
    private T data;
    public TResult(Integer code, Object data,String message) {
        this.code = code;
        this.data = (T) data;
        this.message = message;
    }

    public TResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public TResult(Object data) {
        this.code = 200;
        this.data = (T) data;
        this.message = "操作成功!";
    }
    public TResult(Object data,String message) {
        this.code = 200;
        this.data = (T) data;
        this.message = message;
    }

    public TResult() {
        this.code = 200;
        this.message = "操作成功!";
    }
}
