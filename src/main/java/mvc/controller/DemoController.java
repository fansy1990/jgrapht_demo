package mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: fansy
 * @Time: 2019/4/25 11:38
 * @Email: fansy1990@foxmail.com
 */
@Controller
public class DemoController {
    /*@RequestMapping("/")
    public String demo( ){
        return "demo";
    }*/

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "demo";
    }
}
