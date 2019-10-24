package com.neusoft.bookstore.goods.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neusoft.bookstore.base.Response;
import com.neusoft.bookstore.file.mapper.FileMapper;
import com.neusoft.bookstore.file.modle.File;
import com.neusoft.bookstore.file.service.FileService;
import com.neusoft.bookstore.goods.mapper.GoodsMapper;
import com.neusoft.bookstore.goods.model.Goods;
import com.neusoft.bookstore.goods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private FileService fileService;

    private final Integer FLAG = 1;

    /**
     * 新增sku
     * @param goods
     * @return
     */
    @Override
    public Response saveGoods(Goods goods) {
        //验证是否为空
        //验证sku名称是否为空
        if (null == goods.getSkuName()){
            return Response.error("sku名称为空");
        }
        //定价是否为空
        if (null == goods.getCostPrice()){
            return Response.error("定价为空");
        }
        //销售价是否为空
        if (null == goods.getSalePrice()){
            return Response.error("销售价为空");
        }
        //一级分类是否为空
        if (null == goods.getOneCateId()){
            return Response.error("一级分类为空");
        }
        //二级分类是否为空
        if (null == goods.getTwoCateId()){
            return Response.error("二级分类为空");
        }
        //验证sku名称是否存在
        int count = goodsMapper.listGoodsNameCount(goods);
        if (count > 0){
            return Response.error("sku名称已存在");
        }
        int saveCount = goodsMapper.saveGoods(goods);
        if (saveCount<=0){
            return Response.error("新增失败");
        }
        //判断是否为空
        if (null != goods.getFiles() && goods.getFiles().size() > 0){
            //保存图片
            fileMapper.saveFile(goods);
        }
        return Response.ok("新增成功");
    }
    /**
     * 查询分类下是否有商品
     * @param id
     * @return
     */
    @Override
    public boolean getGoodsByCateId(Long id){
        int count = goodsMapper.getGoodsByCateId(id);
        if (count>0){
            return true;
        }
        return false;
    }
    /**
     * sku分页查询
     * @param goods
     * @return
     */
    @Override
    public Response listGoodsByPage(Goods goods) {
        PageHelper.startPage(goods.getPageNum(),goods.getPageSize());
        List<Goods> goodsList;
        //如果为1说明是管理端
        if (FLAG.intValue() == goods.getFlag().intValue()){
            goodsList = goodsMapper.listGoodsByPage(goods);
        }else {
            //否则是app端
            goodsList = goodsMapper.listAppGoodsByPage(goods);
        }
        PageInfo<Goods> goodsPageInfo = new PageInfo<>(goodsList);
        return Response.ok(goodsPageInfo,"查询成功");
    }
    /**
     * 详情查询
     * @param goods
     * @return
     */
    @Override
    public Response getGoodsById(Goods goods) {
        //查询商品信息
        Goods goods1 = goodsMapper.getGoodsById(goods);
        //查询图片
        File file = new File();
        file.setBusinessId(goods.getId());
        file.setBusinessType(1);
        List<File> files= fileMapper.listFile(file);
        goods1.setFiles(files);
        return Response.ok(goods1,"查询成功");
    }
    /**
     * 上架或下架
     * @param goods
     * @return
     */
    @Override
    public Response updateGoodsStatus(Goods goods) {
        goodsMapper.updateGoodsStatus(goods);
        return Response.ok("成功");
    }
    /**
     * 删除商品
     * @param goods
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Response deleteGoods(Goods goods) {
        //id不能为空
        if (null != goods.getId()){
            Response.error("id不能为空");
        }
        //删除商品
        goodsMapper.deleteGoods(goods);
        //查询图片
        File file = new File();
        //业务id
        file.setBusinessId(goods.getId());
        //业务类型(1：商品)
        file.setBusinessType(1);
        List<File> files = fileMapper.listFile(file);
        //删除图片
        for (File fl:files){
            fileService.deleteFile(fl);
        }
        return Response.ok("删除成功");
    }
    /**
     * 修改sku
     * @param goods
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Response updateGoods(Goods goods) {
        //验证是否为空
        //验证sku名称是否为空
        if (null == goods.getSkuName()){
            return Response.error("sku名称为空");
        }
        //定价是否为空
        if (null == goods.getCostPrice()){
            return Response.error("定价为空");
        }
        //销售价是否为空
        if (null == goods.getSalePrice()){
            return Response.error("销售价为空");
        }
        //一级分类是否为空
        if (null == goods.getOneCateId()){
            return Response.error("一级分类为空");
        }
        //二级分类是否为空
        if (null == goods.getTwoCateId()){
            return Response.error("二级分类为空");
        }
        //验证sku名称是否存在
        int count = goodsMapper.listGoodsNameCount(goods);
        if (count > 0){
            return Response.error("sku名称已存在");
        }
        //修改操作
        goodsMapper.updateGoods(goods);
        //图片先删除后新增也可以只保存新增的图片
        goods.setGoodsType(1);
        fileMapper.deleteFileByGoods(goods);
        //保存图片
        if (null != goods.getFiles()){
            fileMapper.saveFile(goods);
        }
        return Response.error("修改成功");
    }
    /**
     * 查询商品状态
     * @param skuId
     * @return
     */
    @Override
    public int getGoodsStatus(Long skuId) {
        int status = goodsMapper.getGoodsStatus(skuId);
        return status;
    }
    /**
     * 查询库存
     * @param skuId
     * @return
     */
    @Override
    public int getStock(Long skuId) {
        int stock = goodsMapper.getStock(skuId);
        return stock;
    }
}
