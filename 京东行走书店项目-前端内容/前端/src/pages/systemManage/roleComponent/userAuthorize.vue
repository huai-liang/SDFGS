<template>
  <div class="userAuthorize">
    <el-dialog
      :title="'用户授权-'+role.roleName"
      :visible.sync="dialogUserAuthorize"
      :close-on-click-modal="false"
      width="50%"
      :before-close="cancelDialog"
    >
      <!-- 搜索区域 -->
      <el-card class="searchModule">
        <el-form :model="query" label-width="100px" :inline="true" ref="queryFrom">
          <el-form-item label="用户名称" prop="userName">
            <el-input v-model="query.userName" clearable></el-input>
          </el-form-item>
          <div class="searchBtn">
            <el-button type="primary" @click="searchForm()">查询</el-button>
            <el-button @click="resetForm('queryFrom')">重置</el-button>
          </div>
        </el-form>
      </el-card>
      <!--内容-->
      <div class="contentModule">
        <!--内容-->
        <sirui-table
          class="operateTable"
          ref="mainTable"
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
          >
            <template slot-scope="scope">
              <span v-if="item.prop === 'sex'">{{({'0':"女",'1':"男"})[scope.row[item.prop]]}}</span>
              <span v-else>{{scope.row[item.prop]}}</span>
            </template>
          </el-table-column>
        </sirui-table>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelDialog()">取 消</el-button>
        <el-button type="primary" @click="userAuthorizeSure()">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import API from '@/api/systemManage/role';
import SiruiTable from '@/components/SiruiTable'

export default {
  name: 'userAuthorize',
  components: {
    SiruiTable
  },
  data() {
    return {
      load: false,
      dialogUserAuthorize: false,
      query: {
        userName: ""
      },
      role: {},
      columnList: [
        { prop: 'userName', label: '用户名称', sort: true },
        { prop: 'sex', label: '性别' },
        { prop: 'phone', label: '手机号码' }
      ],
      tableData: [],
      page: {
        pages: 1,//总共多少页
        pageNum: 1,//第几页
        pageSize: 10,//一页多少条数据
        total: 10//总共多少条
      },
      multipleSelection: [],//存放选中的值
      pageRoleCodes: []
    }
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
  methods: {
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
      this.$axios.post(API.LIST_USR_API, this.query).then(res => {
        this.load = false;
        if (0 == res.data.code) {
          this.tableData = res.data.data.list;
          this.page.total = res.data.data.total;
          this.getUseHasChoose();
        } else {
          this.$message({
            showClose: true,
            message: res.data.msg,
            type: 'error'
          });
        }
      })
    },
    //获取已经授权的用户
    getUseHasChoose() {
      this.pageRoleCodes = [];
      this.tableData.forEach((item, index) => {
        this.pageRoleCodes.push(item.userCode);
      });
      let params = {
        roleCode: this.role.roleCode,
        userCodesPage: this.pageRoleCodes
      };
      this.$axios.post(API.LIST_USER_ROLE_API, params).then(res => {
        if (0 == res.data.code) {
          //当前页面的中已勾选的遍历出来
          for (var i = 0; i < res.data.data.length; i++) {
            for (var j = 0; j < this.tableData.length; j++) {
              if (res.data.data[i] == this.tableData[j].userCode) {
                this.$refs.mainTable.clickRow(this.tableData[j]);
              }
            }
          }
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
    openDialog(flag, role) {
      this.role = role;
      this.searchForm();
      this.dialogUserAuthorize = flag;
    },
    cancelDialog() {
      this.$refs["queryFrom"].resetFields();
      this.dialogUserAuthorize = false;
    },
    //确认 菜单授权
    userAuthorizeSure() {
      let selectCode = [];
      this.multipleSelection.forEach((item, index) => {
        selectCode.push(item.userCode);
      });
      let params = {
        roleCode: this.role.roleCode,
        userCodesPage: this.pageRoleCodes,
        userCodesChecked: selectCode
      };
      this.loading.startLoading();
      this.$axios.post(API.SAVE_ROLE_USER_API, params).then(res => {
        this.loading.endLoading();
        if (0 == res.data.code) {
          this.$message({
            showClose: true,
            message: '用户授权成功',
            type: 'success'
          });
          this.$emit('getData');
          this.cancelDialog();
        } else {
          this.$message({
            showClose: true,
            message: res.data.msg,
            type: 'error'
          });
        }
      })
    },
  }
}
</script>
<style lang="scss" scoped>
</style>


