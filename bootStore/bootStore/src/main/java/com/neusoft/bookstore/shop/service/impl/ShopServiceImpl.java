package com.neusoft.bookstore.shop.service.impl;

import com.neusoft.bookstore.base.Response;
import com.neusoft.bookstore.goods.service.GoodsService;
import com.neusoft.bookstore.shop.mapper.ShopMapper;
import com.neusoft.bookstore.shop.model.Shop;
import com.neusoft.bookstore.shop.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopMapper shopMapper;

    @Autowired
    private GoodsService goodsService;

    /**
     * 加入购物车
     * @param shop
     * @return
     */
    @Override
    public Response saveShop(Shop shop) {
        //商品是否在售
        int status = goodsService.getGoodsStatus(shop.getSkuId());
        if (status != 2){
            Response.error("商品非在售状态");
        }
        //判断库存，加入购物车的数量要小于等于库存
        int stock = goodsService.getStock(shop.getSkuId());
        if (shop.getCount().intValue() > stock){
            return Response.error("添加数量大于当前库存");
        }
        //添加购物车
        shopMapper.saveShop(shop);
        return Response.ok("添加成功");
    }
}
