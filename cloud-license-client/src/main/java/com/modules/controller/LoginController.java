package com.modules.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录
 */
@Slf4j
@CrossOrigin
@RestController
public class LoginController {

    /**
     * 模拟登录验证
     * @param loginName 用户名
     * @param password 密码
     */
    @GetMapping(value = "login")
    public Map<String,Object> test(@RequestParam(required = true) String loginName, @RequestParam(required = true) String password){
        Map<String,Object> result = new HashMap<>(1);
        log.info(MessageFormat.format("登录名称：{0}，密码：{1}",loginName,password));
        //模拟登录
        log.info("模拟登录被拦截检查证书可用性");
        result.put("code",200);
        return result;
    }
}
