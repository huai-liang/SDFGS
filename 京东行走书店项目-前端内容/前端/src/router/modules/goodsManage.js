//主页面
const index = r => require.ensure([], () => r(require('@/components/home.vue')), 'index');
//商品管理
const goods = r => require.ensure([], () => r(require('@/pages/goodsManage/goodsManage.vue')), 'goods');

const goodsRouter = {
  path: '/',
  component: index,
  children: [
    {
      path: '/goodsManage',
      name: 'goods',
      component: goods,
      meta: {
        keepAlive: true,
        title: '商品管理'
      }
    }
  ]
}
export default goodsRouter
