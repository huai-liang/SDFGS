<template>
  <div class="header">
    <!-- 折叠按钮 -->
    <hamburger
      id="hamburger-container"
      :is-active="!collapse"
      class="hamburger-container"
      @toggleClick="toggleSideBar"
    />
    <!--面包屑导航-->
    <breadcrumb id="breadcrumb-container" class="breadcrumb-container"/>

    <div class="header-right">
      <div class="header-user-con">
        <!-- <search id="header-search" class="right-menu-item"/> -->
        <!-- 消息中心 -->
        <!-- <div class="btn-bell">
          <el-tooltip effect="dark" :content="message?`有${message}条未读消息`:`消息中心`" placement="bottom">
            <router-link to="/tabs">
              <i class="el-icon-bell"></i>
            </router-link>
          </el-tooltip>
          <span class="btn-bell-badge" v-if="message"></span>
        </div> -->
        <!-- 用户头像 -->
        <div class="user-avator">
          <img src="static/img/img.jpg">
        </div>
        <!-- 用户名下拉菜单 -->
        <el-dropdown class="user-name" trigger="click" @command="handleCommand">
          <span class="el-dropdown-link">
            {{userInfo.userInfo.userName}}
            <i class="el-icon-caret-bottom"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item divided command="changePwd">修改密码</el-dropdown-item>
            <el-dropdown-item divided command="loginout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>
     <!-- 修改密码 -->
    <el-dialog
      title="修改密码"
      :visible.sync="dialogPwdEdit"
      width="30%"
      :close-on-click-modal="false"
      :before-close="cancelDialog"
    >
      <el-form
        :model="editPwd"
        :rules="rules"
        ref="editPwd"
        label-width="100px"
        label-position="right"
        class="changePassword"
      >
        <el-form-item label="新密码" prop="passWord">
          <el-input ref="password" :type="passwordType" v-model="editPwd.passWord"></el-input>
          <span class="show-pwd" @click="showPwd">
            <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'"/>
          </span>
        </el-form-item>
        <el-form-item label="确认密码" prop="passWordCheck">
          <el-input ref="passWordCheck" :type="passWordCheck" v-model="editPwd.passWordCheck"></el-input>
          <span class="show-pwd" @click="showPwdCheck">
            <svg-icon :icon-class="passWordCheck === 'password' ? 'eye' : 'eye-open'"/>
          </span>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelDialog()">取 消</el-button>
        <el-button type="primary" @click="editPwdSure()">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { mapState } from 'vuex'
import bus from '../common/js/bus'
import Hamburger from '@/components/Hamburger'
import Breadcrumb from '@/components/Breadcrumb'
import Search from '@/components/HeaderSearch'
import API from '@/api/systemManage/user';

export default {
  components: {
    Hamburger,
    Breadcrumb,
    Search
  },
  data() {
    //密码
    var validatePassWord = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.editPwd.passWordCheck !== '') {
          this.$refs.editPwd.validateField('passWordCheck');
        }
        callback();
      }
    };
    //密码确认
    var validatePassWordCheck = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.editPwd.passWord) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      collapse: true,
      message: 2,
      editPwd: {
        passWord: '',
        passWordCheck: ''
      },
      dialogPwdEdit: false,
      passwordType: 'password',
      passWordCheck: 'password',
      //修改密码
      rules: {
        passWord: [
          { required: true, validator: validatePassWord, trigger: 'blur' }
        ],
        passWordCheck: [
          { required: true, validator: validatePassWordCheck, trigger: 'blur' }
        ]
      }
    }
  },
  computed: {
    ...mapState({
      userInfo: function (state) {
        if (JSON.stringify(state.userInfo.userInfo) == '{}') {
          this.$store.commit('updateUserInfo');
        }
        return state.userInfo;
      }
    })
  },
  mounted() {
    this.toggleSideBar();
  },
  methods: {
    // 用户名下拉菜单选择事件
    handleCommand(command) {
      if (command == 'loginout') {
        this.$store.commit('deleteUserInfo');
        this.$router.push('/login');
      }
      if(command == 'changePwd'){
        this.dialogPwdEdit = true;
        // console.log(this.$store.state.userInfo.userInfo.userCode)
      }
    },
    //取消
    cancelDialog() {
      this.passwordType = 'password';
      this.passWordCheck = 'password';
      this.dialogPwdEdit = false;
      this.$refs["editPwd"].resetFields();
    },
    //修改密码
    editPwdSure() {
      this.$refs["editPwd"].validate((valid) => {
        if (valid) {
          let params = {
            userCode: this.$store.state.userInfo.userInfo.userCode,
            newPwd: this.editPwd.passWord
          };
          this.loading.startLoading();
          this.$axios.post(API.UPDATE_USER_PWD_API, params).then(res => {
            this.loading.endLoading();
            if (0 == res.data.code) {
              this.$message({
                showClose: true,
                message: '修改密码成功',
                type: 'success'
              });
              this.cancelDialog();
            } else {
              this.$message({
                showClose: true,
                message: res.data.msg,
                type: 'error'
              });
            }
          })
        }
      })
    },
    showPwd() {
      if (this.passwordType === 'password') {
        this.passwordType = '';
      } else {
        this.passwordType = 'password';
      }
      this.$nextTick(() => {
        this.$refs.password.focus();
      })
    },
    showPwdCheck() {
      if (this.passWordCheck === 'password') {
        this.passWordCheck = '';
      } else {
        this.passWordCheck = 'password';
      }
      this.$nextTick(() => {
        this.$refs.passWordCheck.focus();
      })
    },
    toggleSideBar() {
      this.collapse = !this.collapse;
      bus.$emit('collapse', this.collapse);
    },
  }
}
</script>
<style lang="scss" scoped>
.header {
  position: relative;
  box-sizing: border-box;
  width: 100%;
  height: 60px;
  font-size: 22px;
  border-bottom: 1px solid #f5f5f5;
  .changePassword {
    .el-form-item {
      position: relative;
    }
    .el-input {
      width: 90%;
    }
    .show-pwd {
      position: absolute;
      right: 13%;
      top: 2px;
      font-size: 16px;
      cursor: pointer;
      user-select: none;
    }
  }
  .handle-box{
    margin-top:10px; 
  }

  .hamburger-container {
    line-height: 56px;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: background 0.3s;
    -webkit-tap-highlight-color: transparent;

    &:hover {
      background: rgba(0, 0, 0, 0.025);
    }
  }

  .breadcrumb-container {
    float: left;
  }

  .right-menu-item {
    display: inline-block;
    padding: 0 8px;
    height: 100%;
    line-height: 60px;
    font-size: 18px;
    color: #aaa;
    vertical-align: text-bottom;

    &.hover-effect {
      cursor: pointer;
      transition: background 0.3s;

      &:hover {
        background: rgba(0, 0, 0, 0.025);
      }
    }
  }
}
.collapse-btn {
  float: left;
  padding: 0 21px;
  cursor: pointer;
  line-height: 60px;
}
.header-right {
  float: right;
  padding-right: 50px;
}
.header-user-con {
  display: flex;
  height: 60px;
  align-items: center;
}
.btn-fullscreen {
  transform: rotate(45deg);
  margin-right: 5px;
}
.btn-bell,
.btn-fullscreen {
  position: relative;
  width: 30px;
  height: 30px;
  text-align: center;
  border-radius: 15px;
  cursor: pointer;
}
.btn-bell-badge {
  position: absolute;
  right: 0;
  top: -2px;
  width: 8px;
  height: 8px;
  border-radius: 4px;
  background: #f56c6c;
}
.btn-bell .el-icon-bell {
  color: #aaa;
}
.user-name {
  margin-left: 10px;
}
.user-avator {
  margin-left: 20px;
}
.user-avator img {
  display: block;
  width: 40px;
  height: 40px;
  border-radius: 50%;
}
.el-dropdown-link {
  color: #aaa;
  cursor: pointer;
}
.el-dropdown-menu__item {
  text-align: center;
}
</style>
