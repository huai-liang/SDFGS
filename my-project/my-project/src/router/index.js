import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/login'
import HomePage from '@/components/layout/homePage'
import User from '@/components/pages/systemPage/user'//用户管理
import Menu from '@/components/pages/systemPage/menu'//菜单管理

import BannerImg from '@/components/pages/worksPage/bannerImg'//轮播图管理
import Goods from '@/components/pages/worksPage/goods'//商品管理
import GoodsKind from '@/components/pages/worksPage/goodsKind'//商品分类管理
import Customer from '@/components/pages/worksPage/customer'//客户管理

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    },
    {
      path: '/homePage',
      name: 'HomePage',
      component: HomePage,
      children:[
        {
          path: '/user',
          name: 'User',
          component: User
        },
        {
          path: '/menu',
          name: 'Menu',
          component: Menu
        },
        {
          path: '/bannerImg',
          name: 'BannerImg',
          component: BannerImg
        },
        {
          path: '/goods',
          name: 'Goods',
          component: Goods
        },
        {
          path: '/goodsKind',
          name: 'GoodsKind',
          component: GoodsKind
        },
        {
          path: '/customer',
          name: 'Customer',
          component: Customer
        },
      ]
    }
  ]
})
