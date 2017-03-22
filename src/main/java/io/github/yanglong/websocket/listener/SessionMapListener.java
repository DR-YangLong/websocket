package io.github.yanglong.websocket.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

import java.security.Principal;

import static io.github.yanglong.websocket.listener.SessionMap.AUTHENTICATED_USER;

/**
 * package: io.github.yanglong.websocket.listener <br/>
 * functional describe:系统通知推送需要知道所有用户的tag，以便精确推送，已认证用户，订阅"/user/queue/something"可以使用{@link org.springframework.messaging.simp.SimpMessageSendingOperations#convertAndSendToUser(String, String, Object)}方法推送。<br/>
 * 对于未登录用户，前端发起webSocket之前先向服务器发起普通的http请求申请token，此token为全局唯一，然后向特定的订阅发起一次请求，token作为参数，服务器端服务器端将此token作为map的key，sessionId（其他有意义值也行）作为value保存，前端使用token订阅唯一的topic，这样可以使用将群发变单发；
 * 或者订阅一个内置的user下的某个queue，"/user/queue/something",然后使用convertAndSendToUser发送：
 * 在发送的服务端代码如下：
 * SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
 * headerAccessor.setSessionId(sessionId);
 * headerAccessor.setLeaveMutable(true);
 * <p>
 * messagingTemplate.convertAndSendToUser(sessionId,"/queue/something", payload,headerAccessor.getMessageHeaders());
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2017/3/21
 */
@Configuration
public class SessionMapListener implements ApplicationListener<SessionConnectedEvent> {

    /**
     * websocket链接建立成功后触发，注意，只有发起websocket链接时链接经过security的filter，
     * 此处的user才不会为NULL，不然永远是null，所以认证和未认证的broker最好分为2个
     */
    @Override
    public void onApplicationEvent(SessionConnectedEvent sessionConnectedEvent) {
        StompHeaderAccessor headers = StompHeaderAccessor.wrap(sessionConnectedEvent.getMessage());
        //逻辑
        Principal user = headers.getUser();
        if (user != null && user.getName() != null) {
            //用户已验证过
            String name = headers.getUser().getName();
            if (!AUTHENTICATED_USER.keySet().contains(name)) {
                //没有，新加进去
                AUTHENTICATED_USER.put(name, user);
            }
        }
    }

    @Bean
    public SessionMapListener sessionListener() {
        return new SessionMapListener();
    }
}
