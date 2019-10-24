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
    'EXPORTORDER_LIST_API': '/pc/exportOrder/listExportOrder',
    /**修改代购订单 */
    'UPDATE_EXPORTORDER_API': '/pc/exportOrder/updateExportOrder',
    /**查看详情 */
    'DETAIL_EXPORTORDER_API': '/pc/exportOrder/queryExportOrder',
    /**删除代购订单 */
    'DELETE_EXPORTORDER_API': '/pc/exportOrder/deleteExportOrder',
    /**确认到货/取消确认到货*/
    'UPLOAD_EXPORTORDER_API': '/pc/exportOrder/confirmExportOrder',
  }
  