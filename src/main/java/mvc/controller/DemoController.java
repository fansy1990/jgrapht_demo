package mvc.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: fansy
 * @Time: 2019/4/25 11:38
 * @Email: fansy1990@foxmail.com
 */
@RestController
public class DemoController {
    @RequestMapping("/")
    public String demo( ){
        return "html/demo";
    }
}
