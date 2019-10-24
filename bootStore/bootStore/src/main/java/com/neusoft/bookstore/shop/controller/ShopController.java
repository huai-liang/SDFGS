package com.neusoft.bookstore.shop.controller;

import com.neusoft.bookstore.base.LoginResult;
import com.neusoft.bookstore.base.Response;
import com.neusoft.bookstore.shop.model.Shop;
import com.neusoft.bookstore.shop.service.ShopService;
import com.neusoft.bookstore.util.SessionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Api(description = "购物车")
@Controller
@RequestMapping("shop")
public class ShopController {

    @Autowired
    private ShopService shopService;

    /**
     * 加入购物车
     * @param shop
     * @return
     */
    @ApiOperation(value = "加入购物车")
    @PostMapping("saveShop")
    @ResponseBody
    private Response saveShop(HttpServletRequest request, @RequestBody Shop shop){
        LoginResult loginResult = SessionUtil.getSession(request,shop.getToken());
        shop.setCreateBy(String.valueOf(loginResult.getUserId()));
        Response response = shopService.saveShop(shop);
        return response;
    }
}
