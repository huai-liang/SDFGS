<template>
  <div>
    <!--头部-->
    <el-container>
      <el-header>
        <div class="header_lf">
          <div class="tuxiang"></div>
          <span>行走书店管理系统</span>
        </div>
        <div class="header_rf">
          <div class="touxiang">
            <i class="el-icon-s-custom"></i>
          </div>
          <span>欢迎，系统管理员</span>
          <div class="tuichu">退出</div>
        </div>
      </el-header>
    </el-container>
    <!--内容-->
    <el-container>
      <el-aside width="200px" height="500px;">Aside</el-aside>
      <el-container>
        <el-header>查询条件</el-header>
        <el-main>Main</el-main>
        <el-header>查询结果</el-header>
        <el-main>Main</el-main>
        <el-footer>Footer</el-footer>
      </el-container>
    </el-container>
  </div>
</template>

<script>
export default {
  data() {
    return {};
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
/*头部*/
.el-header{
  background-color: #b3c0d1;
  color: #333;
  line-height: 60px;
  border-bottom: 1px solid#999;
}
.header_lf{
  float: left;
}
.tuxiang{
  width: 30px;
  height: 30px;
  background: #ffffff;
  float: left;
  margin: 14px 0 0 0;
  border-radius: 30px;
}
.el-header span{
  float: left;
  margin: 0 0 0 10px;
  font-size: 14px;
  color: #ffffff;
}
.header_rf{
  width: 280px;
  margin: 0 120px 0 0;
  float: right;
}
.touxiang{
  width: 25px;
  height:25px;
  float: left;
  line-height: 30px;
  margin: 17px 0 0 10px;
  border-radius: 30px;
  background: #ffffff;
}
.touxiang i{
  margin: 0 0 0 5px;
}
.tuichu{
  color: #ffffff;
  float: right;
}

.el-aside {
  background-color: #d3dce6;
  color: #333;
  text-align: center;
  line-height: 695px;
}

.el-main {
  background-color: #ffffff;
  color: #333;
  text-align: center;
  line-height: 160px;
}

.el-footer {
  background-color: #b3c0d1;
  color: #333;
  text-align: center;
  line-height: 60px;
}

body > .el-container {
  margin-bottom: 40px;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
  line-height: 320px;
}
</style>
