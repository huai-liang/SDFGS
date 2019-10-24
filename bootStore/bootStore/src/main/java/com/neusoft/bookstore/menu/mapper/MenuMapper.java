package com.neusoft.bookstore.menu.mapper;

import com.neusoft.bookstore.menu.model.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 菜单
 */
@Mapper
public interface MenuMapper {
    /**
     * 新增菜单
     * @param menu
     * @return
     */
    int saveMenu(Menu menu);
    /**
     * 验证菜单名是否存在
     * @param menu
     * @return
     */
    int getMenuNameCount(Menu menu);
    /**
     * 修改菜单
     * @param menu
     * @return
     */
    int updateMenu(Menu menu);
    /**
     * 查询子菜单
     * @param menu
     * @return
     */
    int listChildrenMenu(Menu menu);
    /**
     * 删除菜单
     * @param menu
     * @return
     */
    int deleteMenu(Menu menu);
    /**
     * 查询菜单树
     * @param
     * @return
     */
    List<Menu> listMenu();
}
