import axios from 'axios'
import store from '../store'
import router from '../router';
import { Message } from 'element-ui'
//正式环境
import { devUrl } from '../common/config/dev.env'
//测试环境
import { prodUrl } from '../common/config/prod.env'
//josn数据序列化
import qs from 'qs'

// 线上环境配置axios.defaults.baseURL，生产环境则用proxy代理
if (process.env.NODE_ENV === 'production') {
  axios.defaults.baseURL = prodUrl;
} else {
  axios.defaults.baseURL = devUrl;
}

//超时时间 10s
//axios.defaults.timeout = 10000;

//拦截请求
axios.interceptors.request.use(config => {
  //每次请求加上token
  if (store.state.userInfo.userInfo.access_token) {
    if ('post' == config.method || 'put' == config.method) {
      config.data = config.data + '&access_token=' + store.state.userInfo.userInfo.access_token;
    } else if ('get' == config.method) {
      config.params = {
        access_token: store.state.userInfo.userInfo.access_token,
        ...config.params
      }
    }
  }
  return config;
}, err => {
  // todo 待完善接口拦截
  return Promise.reject(err);
});

//统一处理返回结果
axios.interceptors.response.use(response => {
  //token过期
  if (2003 == response.data.code) {
    store.commit("saveUserInfo", {});
    router.replace({
      path: '/login'
    });
  }
  return Promise.resolve(response);
}, err => {
  // todo 待完善接口返回
  if (err.code == 'ECONNABORTED' && err.message.indexOf('timeout') != -1) {
    Message.error("请求超时，请联系管理员。");
  }
  return Promise.reject(err);
});

//重新封装axios方法
export default {
  post(url, params) {
    return axios({
      method: 'post',
      url: `${url}`,
      data: qs.stringify(params) || {}
    });
  },
  get(url, params) {
    let tempParams = {
      ...(params || {}),
      // 解决IE缓存添加一个随机时间戳
      _timer: +(new Date())
    }
    return axios({
      method: 'get',
      params: tempParams,
      url: `${url}`
    });
  },
  put(url, params) {
    return axios({
      method: 'put',
      url: `${url}`,
      data: qs.stringify(params) || {}
    });
  },
  delete(url, params) {
    return axios({
      method: 'delete',
      params: params || {},
      url: `${url}`
    });
  }
}
