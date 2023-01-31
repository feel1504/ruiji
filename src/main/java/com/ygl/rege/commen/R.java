package com.ygl.rege.commen;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 通用返回结果类,服务端响应数据封装该对象
 */
@Data
public class R<T> implements Serializable {
    private Integer code;   //返回码
    private String msg;     //错误信息
    private T data;         //数据
    private Map map = new HashMap();    //动态数据

    //返回成功
    public static <T> R<T> success(T object){
        R<T> r = new R<>();
        r.code = 1;
        r.data = object;
        return r;
    }

    //返回失败
    public static <T> R<T> error(String object){
        R<T> r = new R<>();
        r.code = 0;
        r.msg = object;
        return r;
    }

    //动态数据添加
    public R<T> addMap(String key,Object val){
        this.map.put(key,val);
        return this;
    }
}
