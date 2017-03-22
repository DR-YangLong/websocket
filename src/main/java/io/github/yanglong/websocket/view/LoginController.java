package io.github.yanglong.websocket.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * package: io.github.yanglong.websocket.view <br/>
 * functional describe:
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2017/3/21
 */
@Controller
public class LoginController {

    @RequestMapping({"index", "/", "index.html"})
    public ModelAndView index() {
        return new ModelAndView("index-guest");
    }


    @RequestMapping({"auth/index"})
    public ModelAndView notifyMsg() {
        return new ModelAndView("index");
    }
}
