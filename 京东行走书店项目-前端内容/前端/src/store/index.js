import Vue from 'vue'
import Vuex from 'vuex'
import router from '../router'
import userInfo from './module/userInfo.js'

/**
 * 全局状态管理
 */
const state = {
	//菜单code
	menuCode: '',
	//tab集合
	tagsList: [],
	//需要缓存的路由name
	keepAlive: [],
	//获取菜单按钮
	getButtonUrl: '/webauth/role/getRoleMenuButton'
};
const mutations = {
	//keepalive里塞值
	SET_KEEP_ALIVE: (state, keepAlive) => {
		state.keepAlive = keepAlive
	},
	//列表页面新开tabs
	LIST_ADD_TABS: (state, data) => {
		state.keepAlive.forEach((item, index) => {
			if (item === data.name) {
				state.keepAlive.splice(index, 1);
			}
		});
		router.push(data.path);
	},
}

Vue.use(Vuex)


export default new Vuex.Store({
	state,
	mutations,
	modules: {
		userInfo
	}
})