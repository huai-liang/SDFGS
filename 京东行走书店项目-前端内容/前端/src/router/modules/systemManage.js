//主页面
const index = r => require.ensure([], () => r(require('@/components/home.vue')), 'index');
//用户管理
const user = r => require.ensure([], () => r(require('@/pages/systemManage/user.vue')), 'user');
//菜单管理
const menu = r => require.ensure([], () => r(require('@/pages/systemManage/menu.vue')), 'menu');
//角色管理
const role = r => require.ensure([], () => r(require('@/pages/systemManage/role.vue')), 'role');

const systemRouter = {
  path: '/',
  component: index,
  children: [
    {
      path: '/user',
      name: 'user',
      component: user,
      meta: {
        keepAlive: true,
        title: '用户管理'
      }
    },
    {
      path: '/menu',
      name: 'menuList',
      component: menu,
      meta: {
        keepAlive: true,
        title: '菜单管理'
      }
    },
    {
      path: '/role',
      name: 'role',
      component: role,
      meta: {
        keepAlive: true,
        title: '角色管理'
      }
    }
  ]
}
export default systemRouter
