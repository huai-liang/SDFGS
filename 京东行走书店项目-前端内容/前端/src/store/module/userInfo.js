export const SAVE_USERINFO_TO_LOCAL = 'saveUserInfo'

export const UPDATE_USERINFO_FROM_LOCAL = 'updateUserInfo'

export const DELETE_USERINFO_FROM_LOCAL = 'deleteUserInfo'

import { setLocalStorage, getLocalStorage, removeLocalStorage } from '../../common/js/util'

const state = {
  userInfo: {}
}

const mutations = {
  /**
   * 将权限信息数据存储在localstore里面
   */
  [SAVE_USERINFO_TO_LOCAL](state, data) {
    state.userInfo = data;
    setLocalStorage('userInfo', state);
  },
  /**
   * 从localstorage里取出数据
   */
  [UPDATE_USERINFO_FROM_LOCAL](state) {
    let userInfo = getLocalStorage('userInfo');
    if (userInfo) {
      for (let key in state) {
        state[key] = userInfo[key];
      }
    }
  },
  /**
   * 从localstorage里清空
   */
  [DELETE_USERINFO_FROM_LOCAL](state) {
    removeLocalStorage('userInfo');
  }
};

export default {
  state,
  mutations
}