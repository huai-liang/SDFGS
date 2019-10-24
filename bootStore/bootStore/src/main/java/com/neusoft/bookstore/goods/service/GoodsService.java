package com.neusoft.bookstore.goods.service;

import com.neusoft.bookstore.base.Response;
import com.neusoft.bookstore.goods.model.Goods;

public interface GoodsService {
    /**
     * 新增sku
     * @param goods
     * @return
     */
    Response saveGoods(Goods goods);
    /**
     * 查询分类下是否有商品
     * @param id
     * @return
     */
    boolean getGoodsByCateId(Long id);
    /**
     * sku分页查询
     * @param goods
     * @return
     */
    Response listGoodsByPage(Goods goods);
    /**
     * 详情查询
     * @param goods
     * @return
     */
    Response getGoodsById(Goods goods);
    /**
     * 上架或下架
     * @param goods
     * @return
     */
    Response updateGoodsStatus(Goods goods);
    /**
     * 删除sku
     * @param goods
     * @return
     */
    Response deleteGoods(Goods goods);
    /**
     * 修改sku
     * @param goods
     * @return
     */
    Response updateGoods(Goods goods);
    /**
     * 查询商品状态
     * @param skuId
     * @return
     */
    int getGoodsStatus(Long skuId);
    /**
     * 查询商品库存
     * @param skuId
     * @return
     */
    int getStock(Long skuId);
}
