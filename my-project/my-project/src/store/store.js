import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);
/**
 * Vuex全局状态管理
 * @param options {Array} 用于渲染tabs的数组
 */
const store = new Vuex.Store({
  state: {
      // userInfo: {},//当前登陆人信息
      url:'http://127.0.0.1:8082/',//服务请求路径
  },
  mutations: {
    // toLogin(){//公共方法 每个组件可直接调用
    //    window.location.href='http://localhost:8080/#/login'
    // },
  }
});

export default store;
