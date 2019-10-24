//主页面
const index = r => require.ensure([], () => r(require('@/components/home.vue')), 'index');
//热门位商品管理
const popCommodity = r => require.ensure([], () => r(require('@/pages/popCommodityManage/popCommodityManage.vue')), 'countyTown');
const popCommodityRouter = {
  path: '/',
  component: index,
  children: [{
    path: '/popCommodityManage',
    name: 'popCommodity',
    component: popCommodity,
    meta: {
      keepAlive: true,
      title: '热门位商品管理'
    }
  }]
}
export default popCommodityRouter
