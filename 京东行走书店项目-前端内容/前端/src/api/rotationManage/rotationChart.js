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
    'ROTATION_LIST_API': '/pc/banner/listBanner',
    /**新增轮播图 */
    'ADD_ROTATION_API': '/pc/banner/saveBanner',
    /**修改轮播图 */
    'UPDATE_ROTATION_API': '/pc/banner/updateBanner',
    /**删除轮播图 */
    'DELETE_ROTATION_API': '/pc/banner/deleteBanner',
    /**查看轮播图详情 */
    'DETAIL_ROTATION_API': '/pc/banner/getBanner',
    /**排序号重复判断 */
    'CHECKNO_ROTATION_API': '/pc/banner/checkBannerSortNo',
    /**导入 */
    'UPLOAD_ROTATION_API': '/pc/banner/uploadBannerGoodExcel',
    /**图片上传 */
    'UPLOADPIC_ROTATION_API': '/pc/fastDFS/upload',
    /**图片删除 */
    'DELTEPIC_ROTATION_API': '/pc/fastDFS/delete',
    /**获取商品列表 */
    'GETPAGETGOODSSKU_LIST_API': '/pc/tGoodsSku/getPageTGoodsSku',
    /**获取一级分类 */
    'FIRST_GOODS_API': '/pc/goodsCategory/listFirstCategory',
    /**获取二级分类 */
    'SECOND_GOODS_API': '/pc/goodsCategory/listSecondCategory',
    /**异常数据下载 */
    'DOWNLOADEXCEL_GOODS_API': '/pc/goodsCategory/downloadExcel',
  }
  