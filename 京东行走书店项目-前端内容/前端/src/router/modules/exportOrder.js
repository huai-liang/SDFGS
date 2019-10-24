//主页面
const index = r => require.ensure([], () => r(require('@/components/home.vue')), 'index');
//代购订单管理
const exportOrder = r => require.ensure([], () => r(require('@/pages/exportOrder/exportOrder.vue')), 'exportOrder');

const exportOrderRouter = {
  path: '/',
  component: index,
  children: [
    {
      path: '/exportOrder',
      name: 'exportOrder',
      component: exportOrder,
      meta: {
        keepAlive: true,
        title: '代购订单管理'
      }
    }
  ]
}
export default exportOrderRouter
