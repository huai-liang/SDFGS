//主页面
const index = r => require.ensure([], () => r(require('@/components/home.vue')), 'index');
//日志查询
const logQuery = r => require.ensure([], () => r(require('@/pages/logQuery/logQuery.vue')), 'logQuery');
const logQueryRouter = {
  path: '/',
  component: index,
  children: [{
    path: '/logQuery',
    name: 'logQuery',
    component: logQuery,
    meta: {
      keepAlive: true,
      title: '日志查询'
    }
  }]
}
export default logQueryRouter
