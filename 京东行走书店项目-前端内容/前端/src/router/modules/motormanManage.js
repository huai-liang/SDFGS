//主页面
const index = r => require.ensure([], () => r(require('@/components/home.vue')), 'index');
//门店信息管理
const motorman = r => require.ensure([], () => r(require('@/pages/motormanManage/motormanManage.vue')), 'motorman');
const motormanRouter = {
  path: '/',
  component: index,
  children: [{
    path: '/motormanManage',
    name: 'motorman',
    component: motorman,
    meta: {
      keepAlive: true,
      title: '司机信息管理'
    }
  }]
}
export default motormanRouter
