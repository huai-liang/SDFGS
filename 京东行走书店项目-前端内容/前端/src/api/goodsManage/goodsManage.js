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
  'GETPAGETGOODSSKU_LIST_API': '/pc/tGoodsSku/getPageTGoodsSku',
  /**新增 */
  'ADD_GOODS_API': '/pc/tGoodsSku/insertGoods',
  /**修改 */
  'UPDATE_GOODS_API': '/pc/tGoodsSku/updateSelective',
  /**删除 */
  'DELETE_GOODS_API': '/pc/tGoodsSku/deleteSelective',
   /**导出 */
  'EXPORTEXCEL_GOODS_API': '/pc/tGoodsSku/exportExcel',
  /**上架下架 */
  'UPDATEPUTWAY_GOODS_API': '/pc/tGoodsSku/updatePutWay',
   /**获取一级分类 */
  'GOODS_LIST_API': '/pc/goodsCategory/queryGoodsCategory',
  /**上传图片 */
  'UPLOAD_API': '/pc/fastDFS/upload',
  /**修改时查询图片 */
  'SELECTPICURL_API': '/pc/tGoodsSku/selectPicUrl',
  /**导入数据 */
  'IMPORTEXCEL_API': '/pc/tGoodsSku/importExcel',
  /**设置关联商品查询 */
  'GETGOODSRECOMMEND_API': '/pc/tGoodsSku/getGoodsRecommend',
  /**设置关联商品修改数据 */
  'UPDATEGOODSRECOMMEND_API': '/pc/tGoodsSku/updateGoodsRecommend',
  /**上传模板图片 */
  'BATCHUPLOAD_API': '/pc/tGoodsSku/batchUpload',
  /**导入失败时调用接口 */
  'DOWNLOADEXCEL_API': '/pc/tGoodsSku/downloadExcel',
   /**商品已关联时删除 */
  'DELETEGOODSRECOMMEND_API': '/pc/tGoodsSku/deleteGoodsRecommend',
  /**图片批量上传 */
  'GOODPICZIP_API': '/pc/goodPicZip/upload',
}
