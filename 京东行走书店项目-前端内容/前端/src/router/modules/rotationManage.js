//主页面
const index = r => require.ensure([], () => r(require('@/components/home.vue')), 'index');
//主页轮播图管理
const rotation = r => require.ensure([], () => r(require('@/pages/rotationManage/rotationChart.vue')), 'rotation');

const rotationRouter = {
  path: '/',
  component: index,
  children: [
    {
      path: '/rotationChart',
      name: 'rotation',
      component: rotation,
      meta: {
        keepAlive: true,
        title: '主页轮播图管理'
      }
    }
  ]
}
export default rotationRouter
