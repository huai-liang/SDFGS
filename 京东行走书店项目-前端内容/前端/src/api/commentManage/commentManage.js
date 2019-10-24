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
    /**获取商品列表 */
    'POSTGOODSAPPRAISE_LIST_API': '/pc/goodsAppraise/listGoodsAppraise',
    /** 详情 */
    'INFO_GOODS_API': '/pc/goodsAppraise/queryGoodsAppraise',
    /**删除 */
    'DELETE_GOODS_API': '/pc/goodsAppraise/deleteGoodsAppraise',
  }
  