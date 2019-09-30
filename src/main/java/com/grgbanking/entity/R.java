package com.grgbanking.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 *
 * @author grg
 * @email sunlightcs@gmail.com
 * @date 2018年12月26日 下午9:59:27
 */
public class R extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public R() {
        put("code", 0);
        put("msg", "success");
        put("result", new HashMap<>());
    }

    public static R ok(String msg) {
        R r = new R();
        r.put("msg", msg);
        return r;
    }

//	public static R ok(Map<String, Object> map) {
//		R r = new R();
//		r.putAll(map);
//		return r;
//	}

    public static R ok(String msg, Object data) {
        R r = new R();
        r.put("msg", msg);
        r.put("result", data);
        return r;
    }

    public static R ok(Object data) {
        R r = new R();
        r.put("result", data);
        return r;
    }

    public static R ok() {
        return new R();
    }

    @Override
    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public R add(String key, Object value) {
        Map map = (Map) super.get("result");
        map.put(key, value);
        return this;
    }
}
