package com.neusoft.bookstore.menu.service;

import com.neusoft.bookstore.base.Response;
import com.neusoft.bookstore.menu.model.Menu;

public interface MenuService {
    /**
     * 新增菜单
     * @param menu
     * @return
     */
    Response saveMenu(Menu menu);
    /**
     * 修改菜单
     * @param menu
     * @return
     */
    Response updateMenu(Menu menu);
    /**
     * 删除菜单
     * @param menu
     * @return
     */
    Response deleteMenu(Menu menu);
    /**
     * 查询菜单树
     * @return
     */
    Response listMenuTree();
}
