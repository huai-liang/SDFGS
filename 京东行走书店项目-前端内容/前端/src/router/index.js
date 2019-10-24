import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

/* 路由模块集合 */
//系统管理
import systemRouter from './modules/systemManage'
//商品管理
import goodsRouter from './modules/goodsManage'
//订单管理
import ordersRouter from './modules/orderManage'
//商品分类管理
import goodsCategoryRouter from './modules/goodsCategory'
//轮播图管理
import rotationRouter from './modules/rotationManage'
//代购订单管理
import exportOrderRouter from './modules/exportOrder'
//家长端信息管理
import patriarchRouter from './modules/patriarchManage'
// 门店信息管理
import shopRouter from './modules/shopManage'
// 司机信息管理
import motormanRouter from './modules/motormanManage'
// 热门位商品管理
import popCommodityRouter from './modules/popCommodityManage'
// 日志查询
import logQueryRouter from './modules/logQuery'


//登录页
const login = r => require.ensure([], () => r(require('@/pages/login.vue')), 'login');
//主页面
const index = r => require.ensure([], () => r(require('@/components/home.vue')), 'index');
//系统首页
const dashboard = r => require.ensure([], () => r(require('@/pages/dashboard.vue')), 'dashboard');
//系统首页
const commentManage = r => require.ensure([], () => r(require('@/pages/commentManage/commentManage.vue')), 'commentManage');
//403
const forbid = r => require.ensure([], () => r(require('@/pages/403.vue')), '403');
//404
const error = r => require.ensure([], () => r(require('@/pages/404.vue')), '404');

export default new Router({
  routes: [{
      path: '/',
      redirect: '/login',
    },
    {
      path: '/',
      component: index,
      children: [{
          path: '/dashboard',
          name: 'Dashboard',
          component: dashboard,
          meta: {
            keepAlive: true,
            title: '首页'
          }
        },
        {
          path: '/commentManage',
          name: 'commentManage',
          component: commentManage,
          meta: {
            keepAlive: true,
            title: '商品评论管理'
          }
        },
        {
          path: '/404',
          component: error,
          meta: {
            title: '404'
          }
        },
        {
          path: '/403',
          component: forbid,
          meta: {
            title: '403'
          }
        },
      ]
    },
    systemRouter,
    goodsRouter,
    ordersRouter,
    goodsCategoryRouter,
    rotationRouter,
    exportOrderRouter,
    patriarchRouter,
    shopRouter,
    motormanRouter,
    popCommodityRouter,
    logQueryRouter,
    {
      path: '/login',
      component: login
    },
    {
      path: '*',
      redirect: '/404'
    }
  ]
})
