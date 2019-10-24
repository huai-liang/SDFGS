<template>
  <div class="login">
      <div class="loginBg">
        <div class="loginBox">
          <div class="loginForm">
              <div class="pad10">用户名</div>
              <el-input v-model="loginList.userAcct"></el-input>
              <div class="pad10">密码</div>
              <el-input v-model="loginList.userPwd" type="password"></el-input>
          </div>
          <div class="buttomArea">
             <el-button type="primary"  @click="toLogin">登陆</el-button>
          </div>
        </div>
      </div>
  </div>
</template>
<script>
export default {
  name: 'HelloWorld',
  data () {
    return {
      loginList:{},
      toLoginUrl:this.$store.state.url+'customer/loginCustomer'
    }
  },
  methods:{
    toLogin(){
       this.$axios.post(this.toLoginUrl,this.loginList).then(res => {
        if (0 == res.data.code) {
            sessionStorage.setItem("userName", res.data.data.userName)
            sessionStorage.setItem("userId", res.data.data.userId)
            sessionStorage.setItem("token", res.data.data.token)
           this.$router.push({
              path:'/homePage'
            })
        } else {
            this.$message({
              showClose: true,
              message: res.data.msg,
              type: "error"
            });
          }
      });
    }
  }
}
</script>
<style scoped>
.login{
  height: 100%;
  background: url('../assets/bg.jpg')
}
.loginBg{
  height:100%;display: flex;justify-content:center;align-items:center
}
.loginBox{
  height:300px;
  width:400px;
  background:#fff;
  box-shadow: 5px 5px 5px  #888888;
}
.loginForm{
  padding:20px
}
.pad10{
  padding:10px 0;
  color: #888888
}
.buttomArea{
  text-align: center;
  margin-top: 20px;
}
</style>
<style>
.buttomArea .el-button{
  padding:10px 30px
} 
</style>