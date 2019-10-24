<template>
<div class="login-page">
  <div class="head">
    <div>
      <span class="logo-img"></span>
      <span class="rectangle"></span>
      <span class="title">后台管理系统</span>
    </div>
  </div>
  <div class="login-wrap">
    <div class="ms-login">
      <!-- <div class="ms-title">行走书店管理平台</div> -->
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="0px" class="ms-content">
        <el-form-item prop="username">
          <el-input v-model="ruleForm.username" class="login-input" placeholder="请输入账号" size="large">
            <template slot="prepend"><img src="../assets/user.png" alt=""></template>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            ref="password"
            :type="passwordType"
            placeholder="请输入密码"
            v-model="ruleForm.password"
            class="login-input"
            size="large"
          >
          <template slot="prepend"><img src="../assets/pwd.png" alt=""></template>
          </el-input>
        </el-form-item>
        <el-form-item prop="imageCode" class="valiateCode">
          <el-input
            class="code-width"
            placeholder="验证码"
            v-model="ruleForm.imageCode"
            @keyup.enter.native="submitForm('ruleForm')"
            size="large"
          >
          <template slot="prepend"><img src="../assets/imageCode.png" alt=""></template>
          </el-input>
          <img v-bind:src="imgUrl" @click="getValidateCode" class="code-img-width">
        </el-form-item>
        <div class="login-btn">
          <el-button @click="submitForm('ruleForm')">登录</el-button>
        </div>
        <!-- <div class="forgetPwd" @click="forgetPwd">忘记密码？</div> -->
      </el-form>
    </div>
  </div>
  <div class="foot">
    <div class="foot-row">
      <span>Copyright © 上海思芮版权所有</span>
      <i class="divide">|</i>
      <span>鲁ICP备15042957号-1</span>
      <span class="email">运维邮箱：admin@neusoft</span>
      <span>运维电话：025-83665743</span>
    </div>
  </div>
</div>
</template>
<script>
import API from '../api/login';
export default {
  data: function () {
    return {
      passwordType: 'password',
      ruleForm: {
        username: '',
        password: '',
        imageCode: '',
        deviceId: '',
        appType:'pc'
      },
      imgUrl: "",
      deviceId: "",
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ],
        imageCode: [
          { required: true, message: '请输入验证码', trigger: 'blur' }
        ]
      }
    }
  },
  created: function () {
    this.getValidateCode();
  },
  methods: {
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
    //忘记密码
    forgetPwd() {
      this.$message({
        showClose: true,
        message: "该功能暂未开发，敬请期待。",
        type: 'warning'
      });
    },
    //登录
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.loading.startLoading();
          this.$axios.post(API.LOGIN_API, this.ruleForm).then(res => {
            this.loading.endLoading();
            if (0 == res.data.code) {
              const user = {
                access_token: res.data.data.token.access_token,
                userName: this.ruleForm.username,
                userCode: res.data.data.token['X-AUTH-UserId']
              };
              this.$store.commit('saveUserInfo', user);
              this.$router.push('/dashboard');
            } else {
              this.getValidateCode();
              this.ruleForm.imageCode = "";
              this.$message({
                showClose: true,
                message: res.data.msg,
                type: 'error'
              });
            }
          })
        }
      });
    },
    // 获取验证码
    getValidateCode() {
      this.$store.commit('deleteUserInfo');
      this.$axios.post(API.VALIDATE_CODE_API).then(res => {
        if (0 == res.data.code) {
          this.ruleForm.deviceId = res.data.data.deviceId;
          this.imgUrl = "data:image/jpg;base64," + res.data.data.image;
        } else {
          this.$message({
            showClose: true,
            message: res.data.msg,
            type: 'error'
          });
        }
      });
    }
  }
}
</script>

<style lang="scss" scoped>
.login-page{
  position: relative;
  width: 100%;
  height: 100%;
  .head{
    position: relative;
    height: 80px;
    line-height: 80px;
    padding-left: 80px;
    color: #666;
    font-weight: bolder;
    .logo-img{
      position: absolute;
      top: 25px;
      width: 118px;
      height: 30px;
      display: inline-block;
      background-image: url(../assets/logo.png);
      background-size: 100%;
    }
    .rectangle{
      position: absolute;
      top:24px;
      left:213px;
      width: 2px;
      height: 32px;
      display: inline-block;
      background-image: url(../assets/rectangle.png);
      background-size: 100%;
    }
    .title{
      margin-left: 150px;
      font-size: 20px;
      letter-spacing: 2px;
    }
  }
  .login-wrap {
    position: relative;
    width: 100%;
    height: 80%;
    background-image: url(../assets/background.png);
    background-size: 100%;
    background-position: center center;
    .ms-title {
      width: 100%;
      line-height: 50px;
      text-align: center;
      font-size: 20px;
      border-bottom: 1px solid #ddd;
    }
    .ms-login {
      position: absolute;
      right: 5%;
      top: 60%;
      width: 420px;
      height: 310px;
      margin: -190px 0 0 -175px;
      border-radius: 5px;
      background: #fff;
      overflow: hidden;
    }
    .login-input{
      width: 340px;
    }
    .user-name{
      width:14px;
      height:16px;
      display:inline-block;
      background-image: url(../assets/user.png);
    }
    .show-pwd {
      position: absolute;
      right: 10px;
      top: 2px;
      font-size: 16px;
      cursor: pointer;
      user-select: none;
    }
    .ms-content {
      padding: 40px 40px;
    }
    .code-width {
      width: 60%;
    }
    .code-img-width {
      width: 30%;
      height: 32px;
      margin-left: 5%;
      vertical-align: bottom;
    }
    .login-btn {
      text-align: center;
      margin-top: 25px;
    }
    .login-btn button {
      width: 100%;
      height: 45px;
      margin-bottom: 10px;
      font-size: 14px;
      color: #fff;
      background-color: #DE5A37;
    }
    .forgetPwd {
      text-align: right;
      color: #3F86D8;
      font-size: 14px;
      cursor: pointer;
      text-decoration: underline;
    }
  }
  .foot{
    position: relative;
    height: 50px;
    line-height: 50px;
    font-size: 12px;
    color: #666;
    .foot-row{
      width: 700px;
      margin: 0 auto;
    }
    .divide{
      margin: 0 10px;
    }
    .email{
      margin: 0 10px 0 20px;
    }
  }
}
</style>