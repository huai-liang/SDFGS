<template>
  <div class="headCom">
      <div class="headStyle">
            <div class="title">行走书店后台管理系统</div>
            <div class="userNews">
              <div><img src="../../assets/timg.jpeg"></div>
              <div>
                <el-dropdown @command="handleCommand">
                  <span>
                    {{userName}}
                  </span>
                  <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item command="changePassword">修改密码</el-dropdown-item>
                    <el-dropdown-item command="backToLogin">退出</el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
              </div>
            </div>
      </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      userName:sessionStorage.getItem("userName")
    }
  },
  methods:{
    //点击修改密码/退出
    handleCommand(val) {
      if('changePassword'==val){
        alert("修改密码暂未开发")
      }else{
        this.$router.push({
          path:'/'
        })
      }
    },
  }
}
</script>
<style scoped>
  .headStyle{
    height:50px;
    width:100%;
    background:#544242
  }
  .title{
    width:20%;
    float: left;
    text-align: center;
    line-height: 50px;
    color: #fff
  }
  .userNews{
    float: right;
    line-height: 50px;
    text-align: right;
    padding-right:30px;
    overflow: hidden;
    color: #fff;
    font-size: 12px
  }
  .userNews div:first-child{
    float: left;
  }
  .userNews div:first-child img{
    width: 30px;
    margin-top: 10px;
    border-radius:100px; 
    margin-right:10px 
  }
  .userNews div:last-child{
    float: left;
  }
</style>
<style>
.headCom .el-dropdown{
    color:#fff
  }
</style>
