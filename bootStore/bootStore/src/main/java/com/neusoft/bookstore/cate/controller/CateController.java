package com.neusoft.bookstore.cate.controller;

import com.neusoft.bookstore.base.LoginResult;
import com.neusoft.bookstore.base.Response;
import com.neusoft.bookstore.cate.model.Cate;
import com.neusoft.bookstore.cate.service.CateService;
import com.neusoft.bookstore.util.SessionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Api(description = "商品分类相关controller")
@RequestMapping("/cate")
@Controller
public class CateController {
    @Autowired
    private CateService cateService;

    @ApiOperation(value = "新增商品分类")
    @PostMapping("saveCate")
    @ResponseBody
    public Response saveCate(HttpServletRequest request, @RequestBody Cate cate){
        LoginResult loginResult = SessionUtil.getSession(request,cate.getToken());
//        customer.setCreateBy(String.valueOf(loginResult.getUserId()));
        cate.setCreateBy("1");
        cate.setLastModifiedBy("1");
        Response response=cateService.saveCate(cate);
        return response;
    }
    @ApiOperation(value = "修改商品分类")
    @PostMapping("updateCate")
    @ResponseBody
    public Response updateCate(@RequestBody Cate cate){
        Response response=cateService.updateCate(cate);
        return response;
    }
    @ApiOperation(value = "删除商品分类")
    @PostMapping("deleteCate")
    @ResponseBody
    public Response deleteCate(@RequestBody Cate cate){
        Response response=cateService.deleteCate(cate);
        return response;
    }
    @ApiOperation(value = "查询商品分类树")
    @PostMapping("listCateTree")
    @ResponseBody
    public Response listCateTree(){
        Response response=cateService.listCateTree();
        return response;
    }
    /**
     *查询一级分类
     * @return
     */
    @ApiOperation(value = "查询一级分类")
    @PostMapping("listOneCate")
    @ResponseBody
    public Response listOneCate(){
        Response response=cateService.listOneCate();
        return response;
    }
    /**
     *查询二级分类
     * @return
     */
    @ApiOperation(value = "查询二级分类")
    @PostMapping("listTwoCate")
    @ResponseBody
    public Response listTwoCate(@RequestBody Cate cate){
        Response response=cateService.listTwoCate(cate);
        return response;
    }
    /**
     *查询分类详情
     * @return
     */
    @ApiOperation(value = "查询分类详情")
    @PostMapping("getCate")
    @ResponseBody
    public Response getCate(Long id){
        Response response=cateService.getCate(id);
        return response;
    }
}
