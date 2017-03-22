package io.github.yanglong.websocket.listener;

import java.security.Principal;
import java.util.concurrent.ConcurrentHashMap;

/**
 * package: io.github.yanglong.websocket.listener <br/>
 * functional describe:
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2017/3/21
 */
public class SessionMap {
    //已认证用户map
    public static final ConcurrentHashMap<String,Principal> AUTHENTICATED_USER=new ConcurrentHashMap<>();
    //未认证用户token-queueId map
    public static final ConcurrentHashMap<String,String> GUEST_USER=new ConcurrentHashMap<>();
}
