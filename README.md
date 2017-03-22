# Spring Boot with WebSocket 

用[spring官网的demo](https://spring.io/guides/gs/messaging-stomp-websocket/ "前往")改写。

## 需求功能
1. 群发
2. 单发

对实际使用来说，简单的发送到客户端是没有实际意义的，大部分的需求都是将业务处理后的数据发送到特定的客户端上，也许是多个，也许是一个。
所以每个功能都简单划分为了已认证和未认证收发，还有服务端主动单发。

> 认证使用spring security，如果不用，认证及安全需要自己实现

## 快速使用
启动后直接访问[127.0.0.1:8085/index](127.0.0.1:8085/index)打开未认证使用页面。打开另一个浏览器
访问[127.0.0.1:8085/auth/index](127.0.0.1:8085/auth/index)打开登录页面，输入登录账号*admin*，登录密码*123456*，登陆后打开认证使用页面。

### 群发
打开2个浏览器的页面后，在2个页面上点击*Connect*按钮打开链接，之后输入框中输入文字，点击发送，能看到效果。

### 单发

> 未认证用户单发

点击未认证使用页面，点击*SendToken*按钮，如成功会返回**Token收到！**提示，之后
访问[127.0.0.1:8085/queue/guest/123456](127.0.0.1:8085/queue/guest/123456)，后台会对发送token为*123456*的会话主动单推一个消息【游客单发！】。

> 已认证用户单发

直接访问[127.0.0.1:8085/queue/auth/admin](127.0.0.1:8085/queue/auth/admin),后台会对登录时登录用户名为*admin*的用户主动单推一个消息【已登录】。

## 代码导读
> WebSocketConfig

配置服务端broker。

> SessionMapListener

主要用于获取并存储登陆用户名，以便定向单发。

> NotifyController 

WebSocket控制器。

> LoginController

跳转到使用页面。

> Message

消息协议