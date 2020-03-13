package org.sss.gradletest.controller;

import org.springframework.web.bind.annotation.*;

/**
 * 对外提供接口的注解
 */
@RestController
public class HelloController {
    /**
     * 把方法对应成对外提供的接口
     */
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @PostMapping("/hi")
    public String hi(@RequestParam String name, @RequestParam String cellphone) {
        return "hi," + name + "您的手机号是" + cellphone;
    }

    @PostMapping("/hibody")
    public String hibody(@RequestBody String reqbody) {
        return "hi," + "请求消息体是" + reqbody;
    }

    @PostMapping("/hitoken")
    public String hitoken(@RequestHeader String token, @RequestParam String name, @RequestParam String cellphone) {
        return "hi," + "带token的post请求,token是" + token + ",名字是" + name + "电话是" + cellphone;
    }

    @PostMapping("/hiformdata")
    public String hiformdata(@ModelAttribute String name, @ModelAttribute String cellphone) {
        return "hi," + "formdata请求消息体是参数name是" + name + "参数cellphone是" + cellphone;
    }

}
