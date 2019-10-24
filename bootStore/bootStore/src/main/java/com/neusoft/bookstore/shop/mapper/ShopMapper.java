package com.neusoft.bookstore.shop.mapper;

import com.neusoft.bookstore.shop.model.Shop;
import org.apache.ibatis.annotations.Mapper;

/**
 * 购物车
 */
@Mapper
public interface ShopMapper {
    /**
     * 添加购物车
     * @param shop
     */
    void saveShop(Shop shop);
}
