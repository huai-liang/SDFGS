package com.neusoft.bookstore.goods.controller;

import com.neusoft.bookstore.base.Response;
import com.neusoft.bookstore.goods.model.Goods;
import com.neusoft.bookstore.goods.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(description = "sku相关")
@RequestMapping("/goods")
@Controller
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    /**
     * 新增sku
     * @param goods
     * @return
     */
    @ApiOperation(value = "新增sku")
    @PostMapping("saveGoods")
    @ResponseBody
    public Response saveGoods(@RequestBody Goods goods){
        Response response = goodsService.saveGoods(goods);
        return response;
    }
    /**
     * sku分页查询
     * @param goods
     * @return
     */
    @ApiOperation(value = "sku分页查询")
    @PostMapping("listGoodsByPage")
    @ResponseBody
    public Response listGoodsByPage(@RequestBody Goods goods){
        Response response = goodsService.listGoodsByPage(goods);
        return response;
    }
    /**
     * 详情查询
     * @param goods
     * @return
     */
    @ApiOperation(value = "详情查询")
    @PostMapping("getGoodsById")
    @ResponseBody
    public Response getGoodsById(@RequestBody Goods goods){
        Response response = goodsService.getGoodsById(goods);
        return response;
    }
    /**
     * 上架或下架
     */
    @ApiOperation(value = "上架或下架")
    @PostMapping("updateGoodsStatus")
    @ResponseBody
    public Response updateGoodsStatus(@RequestBody Goods goods){
        Response response = goodsService.updateGoodsStatus(goods);
        return response;
    }
    /**
     * 删除sku
     */
    @ApiOperation(value = "删除sku")
    @PostMapping("deleteGoods")
    @ResponseBody
    public Response deleteGoods(@RequestBody Goods goods){
        Response response = goodsService.deleteGoods(goods);
        return response;
    }
    /**
     * 修改sku
     */
    @ApiOperation(value = "修改sku")
    @PostMapping("updateGoods")
    @ResponseBody
    public Response updateGoods(@RequestBody Goods goods){
        Response response = goodsService.updateGoods(goods);
        return response;
    }
}
