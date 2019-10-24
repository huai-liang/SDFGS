package com.neusoft.bookstore.cate.service.impl;

import com.neusoft.bookstore.base.Response;
import com.neusoft.bookstore.cate.mapper.CateMapper;
import com.neusoft.bookstore.cate.model.Cate;
import com.neusoft.bookstore.cate.model.CateTree;
import com.neusoft.bookstore.cate.service.CateService;
import com.neusoft.bookstore.goods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class CateServiceImpl implements CateService {

    private final Long CATE_PARENT_ID=0L;

    @Autowired
    private CateMapper cateMapper;

    @Autowired
    private GoodsService goodsService;
    /**
     * 新增商品分类
     * @param cate
     * @return
     */
    @Override
    public Response saveCate(Cate cate) {
        //判断商品分类名称不能为空
        if (null==cate.getCateName()){
            return Response.error("商品分类名称不能为空");
        }
        //判断商品分类层次不能为空
        if (null==cate.getCateLevel()){
            return Response.error("商品分类层次不能为空");
        }
        //验证商品分类名称是否重复
        int nameCount=cateMapper.getCateNameCount(cate);
        if (nameCount>0){
            return Response.error("商品分类名称已存在");
        }
        //如果是顶级分类默认父级分类id为0
        if (null==cate.getParentCateId()){
            cate.setParentCateId(CATE_PARENT_ID);
        }
        int count=cateMapper.saveCate(cate);
        if (count<=0){
            return Response.error("新增失败");
        }
        return Response.ok("新增成功");
    }
    /**
     * 修改商品分类
     * @param cate
     * @return
     */
    @Override
    public Response updateCate(Cate cate) {
        //判断商品分类名称不能为空
        if (null==cate.getCateName()){
            return Response.error("商品分类名称不能为空");
        }
        //验证商品分类名称是否重复
        int nameCount=cateMapper.getCateNameCount(cate);
        if (nameCount>0){
            return Response.error("商品分类名称已存在");
        }
        //判断分类下是否有商品
        boolean b = goodsService.getGoodsByCateId(cate.getId());
        if (b){
            return Response.error("该分类下存在sku,不能修改");
        }
        int count=cateMapper.updateCate(cate);
        if(count<=0){
            return Response.error("修改失败");
        }
        return Response.ok("修改成功");
    }
    /**
     * 删除商品分类
     * @param cate
     * @return
     */
    @Override
    public Response deleteCate(Cate cate) {
        //查询是否有子分类
        int count=cateMapper.listChildrenCate(cate);
        if (count>0){
            return Response.error("当前分类存在子分类，不能删除");
        }
        //判断分类下是否有商品
        boolean b = goodsService.getGoodsByCateId(cate.getId());
        if (b){
            return Response.error("该分类下存在sku,不能修改");
        }
        cateMapper.deleteCate(cate);
        return Response.ok("删除成功");
    }
    /**
     * 查询商品分类树
     */
    @Override
    public Response listCateTree() {
        //查询所有商品分类树
        List<Cate> cateList = cateMapper.listCate();
        //定义商品分类树
        CateTree cateTree = new CateTree();
        this.recursionCateTree(cateList,cateTree,0L);
        return Response.ok(cateTree.getChildren(),"查询成功");
    }
    /**
     * 查询一级分类
     * @return
     */
    @Override
    public Response listOneCate() {
        List<Cate> cateList = cateMapper.listOneCate();
        return Response.ok(cateList,"查询成功");
    }
    /**
     * 查询二级分类
     * @return
     */
    @Override
    public Response listTwoCate(Cate cate) {
        if (null == cate.getId()){
            return Response.error("参数传递错误");
        }
        List<Cate> cateList = cateMapper.listTwoCate(cate);
        return Response.ok(cateList,"查询成功");
    }
    /**
     *查询分类详情
     * @return
     */
    @Override
    public Response getCate(Long id) {
        Cate cate = cateMapper.getCate(id);
        return Response.ok(cate,"查询成功");
    }
    /**
     *递归所有商品分类
     * @param cateList
     * @param cateTree
     * @param selfId
     */
    private void recursionCateTree(List<Cate> cateList,CateTree cateTree,Long selfId){
        Iterator<Cate> iterator = cateList.iterator();
        while (iterator.hasNext()){
            Cate cate = iterator.next();
            //初始化自身节点
            if (cate.getId().equals(selfId)){
                this.setCateTree(cateTree, cate);
            }
            //初始化子节点
            if (cate.getParentCateId().equals(selfId)){
                CateTree newCateTree = new CateTree();
                this.setCateTree(newCateTree,cate);
                cateTree.getChildren().add(newCateTree);
                this.recursionCateTree(cateList,newCateTree,cate.getId());
            }
        }
    }
    /**
     * 赋值
     * @param cateTree
     * @param cate
     */
    private void setCateTree(CateTree cateTree, Cate cate) {
        cateTree.setId(cate.getId());
        cateTree.setName(cate.getCateName());
        cateTree.setPid(cate.getParentCateId());
        cateTree.setLevel(cate.getCateLevel());
        cateTree.setRemark(cate.getRemark());
    }
}
