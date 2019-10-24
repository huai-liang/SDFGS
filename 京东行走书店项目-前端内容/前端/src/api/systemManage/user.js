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
  'USER_LIST_API': '/webauth/user/listUsers',
  /**新增用户 */
  'ADD_USER_API': '/webauth/user/saveUser',
  /**修改用户 */
  'UPDATE_USER_API': '/webauth/user/updateUser',
  /**修改密码 */
  'UPDATE_USER_PWD_API': '/webauth/user/updatePwd',
  /**删除用户 */
  'DELETE_USER_API': '/webauth/user/deleteUser',
  /**查询角色 */
  'ROLE_LIST_CHECKED_API': '/webauth/user/listRolesAndChecked',
  /**保存角色 */
  'SAVE_ROLE_API': '/webauth/user/saveUserRole',
  /**查询部门 */
  'DEPT_LIST_API': '/webauth/dept/listDepts',
  /**保存部门 */
  'SAVE_DEPT_API': '/webauth/user/saveUserDepts',
  /**获取授权部门 */
  'DEPT_USER_LIST_API': '/webauth/dept/listUserDepts',
  /**获取岗位 */
  'POST_LIST_API': '/webauth/user/listPostsByUser'

}
