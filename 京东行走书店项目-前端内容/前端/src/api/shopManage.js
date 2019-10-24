export default {
  //门店信息管理 查询 by zheng_r 20190516
  //'LOGIN_API': '/uaa/auth/form',
  'STOTE_INFO_QUERY': '/pc/tStoreInfo/listtStoreInfo',
  // 门店信息管理 删除 by zheng_r
  'STORE_INFO_REMOVE': '/pc/tStoreInfo/removeTStoreInfo',
  // 新增
  'STORE_INFO_ADD': '/pc/tStoreInfo/saveTStoreInfo',
  // 修改
  'STORE_INFO_UPDATE': '/pc/tStoreInfo/updateTStoreInfo',
  // 省份查询
  'PROVEINCE_QUERY': '/pc/address/listProvince',
  //查询区
  'COUNTY_QUERY': '/pc/address/listCounty',
  //模糊查询区
  'COUNTY_NAME_QUERY': '/pc/address/listCountyByName',
  //门店导入
  'STORE_INFO_IMPORT': '/pc/tStoreInfo/importTStoreInfo',
  //门店模板下载
  'STORE_INFO_DOWNLOAD': '/pc/fastDFS/download',
  //下载异常文件
  STORE_DOWENLOAD_ERRO: '/pc/tStoreInfo/downloadExcel',
  /**修改密码 */
  'STORE_UPDATE_PWD_API': '/webauth/user/updatePwd',
}
