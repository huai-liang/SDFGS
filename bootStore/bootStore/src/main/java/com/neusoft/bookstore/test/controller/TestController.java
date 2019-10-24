package com.neusoft.bookstore.test.controller;

import com.neusoft.bookstore.test.service.TestService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api("test")
@RequestMapping("/test")
@Controller
public class TestController {

    @Autowired
    private TestService TestService;

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @ResponseBody
    @GetMapping("/listAccount/{id}")
    //@RequestBody  json格式对象传参
    public List<String> listAccount(@PathVariable("id") String id){
        List<String> longs = TestService.listAccount();
        //redisTemplate.opsForValue().set("userId","12345");
        //Object userId = redisTemplate.opsForValue().get("userId");
        //System.out.println(userId);
        return longs;
    }
}
