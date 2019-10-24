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
  /**获取列表 */
  'ROLE_LIST_API': '/webauth/role/listRoles',
  /**新增角色 */
  'ADD_ROLE_API': '/webauth/role/saveRole',
  /**修改角色 */
  'UPDATE_ROLE_API': '/webauth/role/updateRole',
  /**删除角色 */
  'DELETE_ROLE_API': '/webauth/role/deleteRole',
  /**获取菜单树 */
  'MENU_LIST_API': '/webauth/menu/listMenus',
  /**获取菜单按钮 */
  'MENU_LIST_BTN_API': '/webauth/menuBtn/listMenuBtns',
  /**获取角色默认权限 */
  'MENU_BTN_ROLE_API': '/webauth/role/listRoleMenuBtns',
  /**菜单授权 */
  'SAVE_ROLE_MENU_BTN_API': '/webauth/role/saveRoleMenuBtn',
  /**获取用户列表 */
  'LIST_USR_API': '/webauth/user/listUsers',
  /**用户授权 */
  'SAVE_ROLE_USER_API': '/webauth/role/saveRoleUsers',
  /**获取已经授权的用户 */
  'LIST_USER_ROLE_API': '/webauth/role/listRoleUsersCurrentPage'

}
