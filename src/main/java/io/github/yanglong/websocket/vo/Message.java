package io.github.yanglong.websocket.vo;

import java.io.Serializable;

/**
 * package: io.github.yanglong.websocket.vo <br/>
 * functional describe:webSocket基础协议对象
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2017/3/21
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    private String token;
    private String content;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
