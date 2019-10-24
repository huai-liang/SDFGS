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
    'GOODS_LIST_API': '/pc/goodsCategory/queryGoodsCategory',
    /**新增商品分类 */
    'ADD_GOODS_API': '/pc/goodsCategory/addGoodsCategory',
    /**修改商品分类 */
    'UPDATE_GOODS_API': '/pc/goodsCategory/updateGoodsCategory',
    /**查看详情 */
    'DETAIL_GOODS_API': '/pc/goodsCategory/queryGoodsCategoryDetail',
    /**删除商品分类 */
    'DELETE_GOODS_API': '/pc/goodsCategory/deleteGoodsCategory',
    /**导入 */
    'UPLOAD_GOODS_API': '/pc/goodsCategory/importGoodsCategory',
    /**下载模板 */
    'DOWNLOAD_GOODS_API': '/pc/goodsCategory/downloadCategoryExcel',
    /**异常数据下载 */
    'DOWNLOADEXCEL_GOODS_API': '/pc/goodsCategory/downloadExcel',
  }
  