package com.neusoft.bookstore.shop.service;

import com.neusoft.bookstore.base.Response;
import com.neusoft.bookstore.shop.model.Shop;

/**
 * 购物车接口
 */
public interface ShopService {
    /**
     * 加入购物车
     * @param shop
     * @return
     */
    Response saveShop(Shop shop);
}
