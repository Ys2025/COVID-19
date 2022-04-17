package cn.yanghuisen.covid19.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @author 啥也不会的程序员
 * @date 2022/4/15
 */
@RequestMapping("/")
@Controller()
public class IndexController {
    @RequestMapping({"/","/index"})
    public String index(){
        return "index";
    }

    @RequestMapping("/refresh")
    public Map<String,Object> refresh(){



        return null;
    }

}
