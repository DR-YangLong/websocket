package io.github.yanglong.websocket.config.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * package: io.github.yanglong.websocket.config.websocket <br/>
 * functional describe: websocket配置类
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2017/3/21
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    /**
     * 配置消息代理
     * setApplicationDestinationPrefixes：如果set值为<code>hello</code>,MessageMapping注解的url为<code>world</code>，则js请求url为 /hello/world。
     *
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic","/queue");//设置消息服务者前缀，sendTo匹配前缀
        registry.setApplicationDestinationPrefixes("/ws");//设置webSocket链接前缀，浏览器对应的请求连接要加上此前缀才能请求到MessageMapping注解的url
    }

    /**
     * 配置websocket链接地址
     *
     * @param stompEndpointRegistry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        //经过认证的用户
        stompEndpointRegistry.addEndpoint("/auth-notify").withSockJS();
        //未经过认证的用户
        stompEndpointRegistry.addEndpoint("/guest-notify").withSockJS();
    }

}
