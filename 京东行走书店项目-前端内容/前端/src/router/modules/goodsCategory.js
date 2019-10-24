//主页面
const index = r => require.ensure([], () => r(require('@/components/home.vue')), 'index');
//商品分类管理
const goodsCategory = r => require.ensure([], () => r(require('@/pages/goodsCategory/goodsCategory.vue')), 'goodsCategory');

const goodsCategoryRouter = {
  path: '/',
  component: index,
  children: [
    {
      path: '/goodsCategory',
      name: 'goodsCategory',
      component: goodsCategory,
      meta: {
        keepAlive: true,
        title: '商品分类管理'
      }
    }
  ]
}
export default goodsCategoryRouter
