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
  /**获取订单列表 */
  'ORDER_LIST_API': '/pc/order/listOrder',
  /**导出 */
  'EXPORT_ORDER_API': '/pc/order/listExport',
  /**修改订单等接口 */
  'EDIT_ORDER_API': 'pc/order/updateOrder',
  /**查看详情 */
  'VIEW_ORDER_API': '/pc/order/getOrder',
  /**查看日志 */
  'LISTORDERLOG_ORDER_API': '/pc/order/listOrderLog',
  /**订单判断是否部分到货 */
  'LISTCHANNEL_ORDER_API': '/pc/order/listChannel',
}
