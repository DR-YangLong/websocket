package io.github.yanglong.websocket.view.websocket;

import io.github.yanglong.websocket.vo.Message;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static io.github.yanglong.websocket.listener.SessionMap.AUTHENTICATED_USER;
import static io.github.yanglong.websocket.listener.SessionMap.GUEST_USER;

/**
 * package: io.github.yanglong.websocket.view.websocket <br/>
 * functional describe:
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2017/3/21
 */
@Controller
public class NotifyController {
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    /**
     * 公共topic，群发，简单转发接收内容
     *
     * @param receive
     * @return
     * @throws Exception
     */
    @MessageMapping("/all/notify")
    @SendTo("/topic/all/notify")
    public Message notify(Message receive) throws Exception {
        String msg = StringUtils.isEmpty(receive.getContent()) ? "空消息" : receive.getContent();
        receive = new Message();
        receive.setContent(msg);
        return receive;
    }


    /**
     * <code>@SendToUser</code> 自动回复单发。非验证用户用于注册身份和会话对应关系。
     * js：
     * stompClient.subscribe('/user/queue/guest', function(data){alert(data.body);});
     * java:
     * registry.enableSimpleBroker("/queue", "/topic");
     *
     * @param receive
     * @param headerAccessor
     * @return
     * @throws Exception
     */
    @MessageMapping("/guest/register")
    @SendToUser("/queue/guest")
    public Message returnMsg(Message receive, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        String token = receive.getToken();
        if (token != null) {
            if (!GUEST_USER.keySet().contains(token)) {
                GUEST_USER.put(token, headerAccessor.getSessionId());
            }
        }
        receive.setContent("Token收到！");
        return receive;
    }


    /**
     * 未登录服务端推送
     *
     * @param token
     * @return
     */
    @RequestMapping("/queue/guest/{token:\\w+}")
    public
    @ResponseBody
    String guestQueueMsg(@PathVariable("token") String token) {
        System.out.println(token);
        if (GUEST_USER.keySet().contains(token)) {
            String sessionId = GUEST_USER.get(token);
            SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.create();
            accessor.setSessionId(sessionId);
            accessor.setLeaveMutable(true);
            Message message = new Message();
            message.setContent("游客单发！");
            messagingTemplate.convertAndSendToUser(sessionId, "/queue/guest", message, accessor.getMessageHeaders());
        }
        return "success";
    }

    /**
     * 登陆后服务端推送。
     * 。
     *
     * @return
     */
    @RequestMapping("/queue/auth/{userId:\\w+}")
    public
    @ResponseBody
    String authQueueMsg(@PathVariable("userId") String userId) {
        System.out.println(userId);
        //是否登录
        if (AUTHENTICATED_USER.keySet().contains(userId)) {
            Message message = new Message();
            message.setContent("已登录");
            //此处订阅的地址不必有前缀，因为spring默认user自动会加上，但是js中订阅必须有user前缀
            messagingTemplate.convertAndSendToUser(userId, "/queue/auth", message);
        }
        return "success";
    }
}
