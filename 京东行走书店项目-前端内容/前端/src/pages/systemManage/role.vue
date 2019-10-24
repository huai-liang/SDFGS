<template>
  <div class="role">
    <!-- 搜索区域 -->
    <el-card class="searchModule">
      <el-form :model="query" label-width="100px" :inline="true" ref="queryFrom">
        <el-form-item label="名称" prop="roleName">
          <el-input v-model="query.roleName" clearable></el-input>
        </el-form-item>
        <div class="searchBtn">
          <el-button type="primary" @click="searchForm()">查询</el-button>
          <el-button @click="resetForm('queryFrom')">重置</el-button>
        </div>
      </el-form>
    </el-card>
    <!--内容区域-->
    <div class="contentModule">
      <!--按钮-->
      <div class="handle-box">
        <!-- <el-button type="primary" icon="el-icon-plus" @click="openAdd('form')">新增</el-button>
        <el-button type="primary" icon="el-icon-edit" @click="openEdit()">修改</el-button>
        <el-button type="primary" icon="el-icon-delete" @click="del()">删除</el-button> 
        <el-button type="primary" icon="el-icon-warning" @click="menuAuthorize()">菜单授权</el-button>
        <el-button type="primary" icon="el-icon-warning" @click="userAuthorize()">用户授权</el-button> -->
        <el-button
          v-for="(item,i) in menuButtonList"
          class="operateBtn"
          :key="i"
          type="primary"
          :icon="item.buttonStyle"
          @click="chooseClick(item.buttonUrl)"
        >{{item.buttonName}}</el-button>
      </div>
      <!--内容-->
      <sirui-table
        class="operateTable"
        :page="page"
        :loading="load"
        :data="tableData"
        @handleSelectionChange="handleSelectionChange"
        @handleSizeChange="handleSizeChange"
        @handleCurrentChange="handleCurrentChange"
      >
        <el-table-column align="center" type="selection" width="45"></el-table-column>
        <el-table-column
          v-for="(item,index) in columnList"
          :key="index"
          :prop="item.prop"
          :label="item.label"
          :width="item.width"
          :sortable="item.sort"
          :fixed="item.fixed"
          align="center"
          show-overflow-tooltip
        ></el-table-column>
      </sirui-table>
    </div>
    <!--新增-->
    <roleAdd ref="roleAdd" v-on:search="searchForm"></roleAdd>
    <!--修改-->
    <roleEdit ref="roleEdit" v-on:getData="getData"></roleEdit>
    <!--菜单授权-->
    <menuAuthorize ref="menuAuthorize" v-on:getData="getData"></menuAuthorize>
    <!--用户授权-->
    <userAuthorize ref="userAuthorize" v-on:getData="getData"></userAuthorize>
  </div>
</template>
<script>
import { mapState } from 'vuex'
import API from '@/api/systemManage/role'
import SiruiTable from '@/components/SiruiTable'
import RoleAdd from '@/pages/systemManage/roleComponent/roleAdd'
import RoleEdit from '@/pages/systemManage/roleComponent/roleEdit'
import MenuAuthorize from '@/pages/systemManage/roleComponent/menuAuthorize'
import UserAuthorize from '@/pages/systemManage/roleComponent/userAuthorize'

export default {
  components: {
    SiruiTable,
    RoleAdd,
    RoleEdit,
    MenuAuthorize,
    UserAuthorize
  },
  name: 'role',
  data() {
    return {
      query: {
        roleName: ""
      },
      //表格loading
      load: false,
      menuButtonList: [],
      columnList: [
        { prop: 'roleName', label: '名称', sort: true },
        { prop: 'remark', label: '备注' }
      ],
      tableData: [],
      page: {
        pages: 0,//总共多少页
        pageNum: 1,//第几页
        pageSize: 10,//一页多少条数据
        total: 0//总共多少条
      },
      multipleSelection: []//存放选中的值
    }
  },
  computed: {
    ...mapState({
      userInfo: state => state.userInfo
    })
  },
  /**页面必要代码Start */
  watch: {
    pageNum: function () {
      this.getData();
    },
    pageSize: function () {
      this.getData();
    }
  },
  mounted() {
    this.getButton();
    this.getData();
  },
  methods: {
    //获取页面按钮
    getButton() {
      let params = {
        USER_NAME: this.userInfo.userInfo.userName,
        MENU_CODE: this.$store.state.menuCode,
        BUTTON_CODE: ''
      };
      this.$axios.post(this.$store.state.getButtonUrl, params).then(res => {
        if (0 == res.data.code) {
          this.menuButtonList = res.data.data;
        } else {
          this.$message({
            showClose: true,
            message: res.data.msg,
            type: 'error'
          });
        }
      })
    },
    //搜索
    searchForm() {
      this.page.pageNum = 1;
      this.getData()
    },
    //获取数据
    getData() {
      this.query.pageSize = this.page.pageSize;
      this.query.pageNum = this.page.pageNum;
      this.load = true;
      this.$axios.post(API.ROLE_LIST_API, this.query).then(res => {
        this.load = false;
        if (0 == res.data.code) {
          this.tableData = res.data.data.list;
          this.page.total = res.data.data.total;
        } else {
          this.$message({
            showClose: true,
            message: res.data.msg,
            type: 'error'
          });
        }
      })
    },
    //重置
    resetForm(from) {
      this.$refs[from].resetFields();
    },
    //每页条数
    handleSizeChange(val) {
      this.page.pageSize = val;
      this.getData();
    },
    //第几页
    handleCurrentChange(val) {
      this.page.pageNum = val;
      this.getData();
    },
    //选中触发事件
    handleSelectionChange(row) {
      this.multipleSelection = row;
    },
    //判断点击哪个按钮
    chooseClick(method) {
      if ('openAdd' == method) {
        this.openAdd();
      } else if ('openEdit' == method) {
        this.openEdit();
      } else if ('del' == method) {
        this.del();
      } else if ('menuAuthorize' == method) {
        this.menuAuthorize();
      } else if ('userAuthorize' == method) {
        this.userAuthorize();
      }
    },
    //新增
    openAdd() {
      this.$refs.roleAdd.openDialog(true);
    },
    //修改
    openEdit() {
      if (1 == this.multipleSelection.length) {
        this.$refs.roleEdit.openDialog(true, JSON.parse(JSON.stringify(this.multipleSelection[0])));
      } else {
        this.$message({
          showClose: true,
          message: "请选择一条数据进行修改",
          type: 'error'
        });
      }
    },
    //删除
    del() {
      if (0 != this.multipleSelection.length.length) {
        this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let params = {
            roles: []
          };
          for (var i = 0; i < this.multipleSelection.length; i++) {
            params.roles.push(this.multipleSelection[i].roleCode);
          }
          params.roles = params.roles.join(",");
          this.loading.startLoading();
          this.$axios.post(API.DELETE_ROLE_API, params).then(res => {
            this.loading.endLoading();
            if (0 == res.data.code) {
              this.searchForm();
              this.$message({
                type: 'success',
                message: '删除成功!'
              });
            } else {
              this.$message({
                showClose: true,
                message: res.data.msg,
                type: 'error'
              });
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      } else {
        this.$message({
          showClose: true,
          message: "请选择数据进行删除",
          type: 'error'
        });
      }
    },
    //菜单授权
    menuAuthorize() {
      if (1 == this.multipleSelection.length) {
        this.$refs.menuAuthorize.openDialog(true, this.multipleSelection[0]);
      } else {
        this.$message({
          showClose: true,
          message: "请选择一条数据进行授权",
          type: 'error'
        });
      }
    },
    //用户授权
    userAuthorize() {
      if (1 == this.multipleSelection.length) {
        this.$refs.userAuthorize.openDialog(true, this.multipleSelection[0]);
      } else {
        this.$message({
          showClose: true,
          message: "请选择一条数据进行授权",
          type: 'error'
        });
      }
    }
  }
}
</script>
<style lang="scss" scoped>
.role .handle-box{
  margin-top:10px; 
}
</style>