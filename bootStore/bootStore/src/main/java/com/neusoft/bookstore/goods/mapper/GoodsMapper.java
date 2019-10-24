package com.neusoft.bookstore.goods.mapper;

import com.neusoft.bookstore.goods.model.Goods;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GoodsMapper {
    /**
     * 验证sku名称是否存在
     * @param goods
     * @return
     */
    int listGoodsNameCount(Goods goods);
    /**
     * 新增sku
     * @param goods
     * @return
     */
    int saveGoods(Goods goods);
    /**
     *分类下是否有商品
     * @param id
     * @return
     */
    int getGoodsByCateId(Long id);
    /**
     * sku分页查询
     * @param goods
     * @return
     */
    List<Goods> listGoodsByPage(Goods goods);
    /**
     * 详情查询
     * @param goods
     * @return
     */
    Goods getGoodsById(Goods goods);
    /**
     * 上架或下架
     * @param goods
     */
    void updateGoodsStatus(Goods goods);
    /**
     * 删除sku
     * @param goods
     * @return
     */
    void deleteGoods(Goods goods);
    /**
     * 修改sku
     * @param goods
     * @return
     */
    void updateGoods(Goods goods);
    /**
     * 查询App商品列表
     * @param goods
     * @return
     */
    List<Goods> listAppGoodsByPage(Goods goods);
    /**
     * 查询商品状态
     * @param skuId
     * @return
     */
    int getGoodsStatus(Long skuId);
    /**
     * 查询库存
     * @param skuId
     * @return
     */
    int getStock(Long skuId);
}
