//主页面
const index = r => require.ensure([], () => r(require('@/components/home.vue')), 'index');
//订单管理
const  orders= r => require.ensure([], () => r(require('@/pages/orderManage/orderManage.vue')), 'orders');

const ordersRouter = {
  path: '/',
  component: index,
  children: [
    {
      path: '/orderManage',
      name: 'orders',
      component: orders,
      meta: {
        keepAlive: true,
        title: '订单管理'
      }
    }
  ]
}
export default ordersRouter
