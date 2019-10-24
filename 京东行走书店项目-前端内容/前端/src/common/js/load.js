import { Loading } from 'element-ui'

let loading;
export default {
  startLoading: function () {
    loading = Loading.service({
      lock: true,
      text: '加载中……',
      spinner: 'el-icon-loading',
      background: 'rgba(0, 0, 0, 0.7)'
    })
  },
  endLoading: function () {
    loading.close();
  }
}