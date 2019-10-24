package com.neusoft.bookstore.menu.controller;

import com.neusoft.bookstore.base.LoginResult;
import com.neusoft.bookstore.base.Response;
import com.neusoft.bookstore.menu.model.Menu;
import com.neusoft.bookstore.menu.service.MenuService;
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

@Api(description = "菜单相关controller")
@RequestMapping("/menu")
@Controller
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 新增菜单
     * @param menu
     * @return
     */
    @ApiOperation(value = "新增菜单")
    @PostMapping("saveMenu")
    @ResponseBody
    public Response saveMenu (HttpServletRequest request, @RequestBody Menu menu){
        LoginResult loginResult = SessionUtil.getSession(request,menu.getToken());
//        menu.setCreateBy(String.valueOf(loginResult.getUserId()));
        menu.setCreateBy("1");
        Response response = menuService.saveMenu(menu);
        return response;
    }
    /**
     * 修改菜单
     * @param menu
     * @return
     */
    @ApiOperation(value = "修改菜单")
    @PostMapping("updateMenu")
    @ResponseBody
    public Response updateMenu (@RequestBody Menu menu){
        Response response = menuService.updateMenu(menu);
        return response;
    }
    /**
     * 删除菜单
     * @param menu
     * @return
     */
    @ApiOperation(value = "删除菜单")
    @PostMapping("deleteMenu")
    @ResponseBody
    public Response deleteMenu (@RequestBody Menu menu){
        Response response = menuService.deleteMenu(menu);
        return response;
    }
    /**
     * 查询菜单树
     * @param
     * @return
     */
    @ApiOperation(value = "查询菜单树")
    @PostMapping("listMenuTree")
    @ResponseBody
    public Response listMenuTree(){
        Response response=menuService.listMenuTree();
        return response;
    }
}
