//主页面
const index = r => require.ensure([], () => r(require('@/components/home.vue')), 'index');
//家长
const patriarch = r => require.ensure([], () => r(require('@/pages/patriarchManage/patriarchManage.vue')), 'patriarch');

const patriarchRouter = {
  path: '/',
  component: index,
  children: [
    {
      path: '/patriarchManage',
      name: 'patriarch',
      component: patriarch,
      meta: {
        keepAlive: true,
        title: '家长端信息管理'
      }
    }
  ]
}
export default patriarchRouter
