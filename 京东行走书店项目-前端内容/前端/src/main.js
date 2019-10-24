import Vue from 'vue';
import './icons'; // icon
import App from './App';
import store from './store';
import router from './router';
import ElementUI from 'element-ui';
import * as filters from './filters';// 过滤器
import loading from './common/js/load.js';//loading
//import 'element-ui/lib/theme-chalk/index.css';    // 默认主题
import '../static/css/theme-green/index.css';       // 浅绿色主题
import httpService from './service/http-service';

import VueQuillEditor from 'vue-quill-editor'
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
import 'quill/dist/quill.bubble.css'
import 'babel-polyfill';//设置兼容ie10+
  
Vue.use(VueQuillEditor);
Vue.use(ElementUI, { size: 'small' });

Vue.prototype.loading = loading;

Vue.prototype.$axios = httpService;
//过滤器
Object.keys(filters).forEach(key => {
  Vue.filter(key, filters[key])
});

new Vue({
  store,
  router,
  render: h => h(App)
}).$mount('#app');