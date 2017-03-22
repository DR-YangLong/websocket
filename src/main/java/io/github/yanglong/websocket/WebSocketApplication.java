package io.github.yanglong.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * package: io.github.yanglong.websocket <br/>
 * functional describe:程序入口
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2017/3/21k
 */
@SpringBootApplication(scanBasePackages = {"io.github.yanglong"})
@PropertySource("classpath:application.yml")
public class WebSocketApplication{

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
//    }

    public static void main(String[] args) {
        System.out.println("*********************正在启动*********************************");
        SpringApplication application = new SpringApplication(WebSocketApplication.class);
        application.run(args);
        System.out.println("*********************启动成功*********************************");
    }
}
