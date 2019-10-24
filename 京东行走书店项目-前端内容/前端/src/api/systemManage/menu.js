/*
    此文件为数据接口存放命名，为防止接口太多，造成接口混乱
    命名为大写斜杠
    按页面划分
    例如： 'ORDER_API': '/api/order',
    每个接口注释用途，为方面其他开发人员查找，了解。
    export default {

    } 
*/
export default {
  /**获取菜单树 */
  'MENU_TREE_API': '/webauth/menu/listMenus',
  /**获取菜单按钮 */
  'MENU_BTN_API': '/webauth/menuBtn/listMenuBtns',
  /**新增菜单 */
  'ADD_MENU_API': '/webauth/menu/insertMenu',
  /**修改菜单 */
  'UPDATE_MENU_API': '/webauth/menu/updateMenu',
  /**删除菜单 */
  'DELETE_MENU_API': '/webauth/menu/deleteMenu',
  /**新增按钮 */
  'ADD_MENU_BTN_API': 'webauth/menuBtn/insertMenuBtn',
  /**修改按钮 */
  'UPDATE_MENU_BTN_API': '/webauth/menuBtn/updateMenuBtn',
  /**删除按钮 */
  'DELETE_MENU_BTN_API': '/webauth/menuBtn/deleteMenuBtn'

}