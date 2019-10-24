//主页面
const index = r => require.ensure([], () => r(require('@/components/home.vue')), 'index');
//门店信息管理
const shop = r => require.ensure([], () => r(require('@/pages/shopManage/shopManage.vue')), 'shop');
const shopRouter = {
  path: '/',
  component: index,
  children: [
    {
      path: '/shopManage',
      name: 'shop',
      component: shop,
      meta: {
        keepAlive: true,
        title: '门店信息管理'
      }
    }
  ]
}
export default shopRouter
