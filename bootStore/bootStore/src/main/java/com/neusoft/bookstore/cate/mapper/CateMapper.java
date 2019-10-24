package com.neusoft.bookstore.cate.mapper;

import com.neusoft.bookstore.cate.model.Cate;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CateMapper {
    /**
     * 新增商品分类
     * @param cate
     * @return
     */
    int saveCate(Cate cate);
    /**
     * 验证商品分类名称是否重复
     * @param cate
     * @return
     */
    int getCateNameCount(Cate cate);
    /**
     * 修改商品分类
     * @param cate
     * @return
     */
    int updateCate(Cate cate);
    /**
     * 查询商品分类树
     * @return
     */
    List<Cate> listCate();
    /**
     * 查询一级分类
     * @return
     */
    List<Cate> listOneCate();
    /**
     * 查询二级分类
     * @return
     */
    List<Cate> listTwoCate(Cate cate);
    /**
     *查询分类详情
     * @return
     */
    Cate getCate(Long id);
    /**
     * 删除商品分类
     * @param cate
     * @return
     */
    int deleteCate(Cate cate);
    /**
     * 查询是否有子分类
     * @param cate
     * @return
     */
    int listChildrenCate(Cate cate);
}
