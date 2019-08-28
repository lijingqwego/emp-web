package com.kaisn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 通用跳转页面
 * @author lwx515559
 *
 */
@Controller
public class PageController {

    @RequestMapping("page/{pageName}")
    public String goHome(@PathVariable String pageName){
        return pageName;
    }
}