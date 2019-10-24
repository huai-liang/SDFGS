package com.neusoft.bookstore.cate.service;

import com.neusoft.bookstore.base.Response;
import com.neusoft.bookstore.cate.model.Cate;

public interface CateService {
    /**
     * 新增商品分类
     * @return
     */
    Response saveCate(Cate cate);
    /**
     * 修改商品分类
     * @param cate
     * @return
     */
    Response updateCate(Cate cate);
    /**
     * 删除商品分类
     * @param cate
     * @return
     */
    Response deleteCate(Cate cate);
    /**
     * 查询商品分类树
     */
    Response listCateTree();
    /**
     * 查询一级分类
     * @return
     */
    Response listOneCate();
    /**
     * 查询二级分类
     * @return
     */
    Response listTwoCate(Cate cate);
    /**
     *查询分类详情
     * @return
     */
    Response getCate(Long id);
}
