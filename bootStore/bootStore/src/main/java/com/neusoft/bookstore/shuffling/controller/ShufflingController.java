package com.neusoft.bookstore.shuffling.controller;

import com.neusoft.bookstore.base.Response;
import com.neusoft.bookstore.shuffling.model.Shuffling;
import com.neusoft.bookstore.shuffling.service.ShufflingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(description = "轮播图")
@RequestMapping("/shuffling")
@Controller
public class ShufflingController {

    @Autowired
    private ShufflingService shufflingService;

    @ApiOperation("分页查询轮播列表")
    @PostMapping("/listShufflingByPage")
    @ResponseBody
    public Response listShufflingByPage(@RequestBody Shuffling shuffling){
        Response response = shufflingService.listShuffling(shuffling);
        return response;
    }
    @ApiOperation("新增轮播")
    @PostMapping("/saveShuffling")
    @ResponseBody
    public Response saveShuffling(@RequestBody Shuffling shuffling){
        Response response = shufflingService.saveShuffling(shuffling);
        return response;
    }
    @ApiOperation("删除轮播")
    @PostMapping("/deleteShufflingById")
    @ResponseBody
    public Response deleteShufflingById(@RequestBody Shuffling shuffling){
        Response response = shufflingService.deleteShufflingById(shuffling);
        return response;
    }
    @ApiOperation("轮播启用禁用")
    @PostMapping("/updShuffling")
    @ResponseBody
    public Response updShuffling(@RequestBody Shuffling shuffling){
        Response response = shufflingService.updShuffling(shuffling);
        return response;
    }
}
