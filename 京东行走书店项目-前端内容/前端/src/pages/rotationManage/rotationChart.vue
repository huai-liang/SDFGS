<template>
  <div class="rotation">
    <!-- 搜索区域 -->
    <el-card class="searchModule">
      <el-form :model="query" label-width="100px" :inline="true" ref="queryFrom">
        <el-form-item label="轮播图名" prop="title">
          <el-input v-model="query.title" clearable></el-input>
        </el-form-item>
        <el-form-item label="有效期起" prop="startTime">
          <el-date-picker
            v-model="query.startTime"
            type="date"
            format="yyyy-MM-dd"
            value-format="yyyy-MM-dd"
            placeholder="选择日期"
            @change="startTimeStatus"
            :picker-options="pickerOptionsStart">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="有效期止" prop="endTime">
          <el-date-picker
            v-model="query.endTime"
            type="date"
            format="yyyy-MM-dd"
            value-format="yyyy-MM-dd"
            placeholder="选择日期"
            @change="endTimeStatus"
            :picker-options="pickerOptionsEnd">
          </el-date-picker>
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
        >
          <template slot-scope="scope">
            <span v-if="item.prop === 'imageUrl'"><img :src="scope.row[item.prop]" alt="图片预览" style="width:90px;"></span>
            <span v-else>{{scope.row[item.prop]}}</span>
          </template>
        </el-table-column>
      </sirui-table>
    </div>
    <!--新增-->
    <rotationAdd ref="rotationAdd" v-on:search="searchForm"></rotationAdd>
    <!--修改-->
    <rotationEdit ref="rotationEdit" v-on:getData="getData"></rotationEdit>
  </div>
</template>
<script>
import { mapState } from 'vuex'
import API from '@/api/rotationManage/rotationChart';
import SiruiTable from '@/components/SiruiTable'
import RotationAdd from '@/pages/rotationManage/rotationComponent/rotationAdd'
import RotationEdit from '@/pages/rotationManage/rotationComponent/rotationEdit'

export default {
  components: {
    SiruiTable,
    RotationAdd,
    RotationEdit,
  },
  name: 'rotation',
  data() {
    return {
      query: {
        title: "",
        startTime: "",
        endTime:""
      },
      //表格loading
      load: false,
      menuButtonList: [],
      columnList: [
        { prop: 'sortNo', label: '排序',sort: true,  fixed: true, width: 120 },
        { prop: 'imageUrl', label: '预览' },
        { prop: 'title', label: '轮播图片名' },
        { prop: 'startTime', label: '有效期起' },
        { prop: 'endTime', label: '有效期止' },
        { prop: 'typeName', label: '新增类型' },
      ],
      tableData: [],
      page: {
        pages: 0,//总共多少页
        pageNum: 1,//第几页
        pageSize: 10,//一页多少条数据
        total: 0//总共多少条
      },
      multipleSelection: [],//存放选中的值
      //开始时间小于结束时间
      pickerOptionsStart: {
        disabledDate: time => {
          let endDateVal = this.query.endTime;
          if (endDateVal) {
            return time.getTime() > new Date(endDateVal).getTime();
          }
        }
      },
      //结束时间大于开始时间
      pickerOptionsEnd: {
        disabledDate: time => {
          let beginDateVal = this.query.startTime;
          if (beginDateVal) {
            return (
              time.getTime() <
              new Date(beginDateVal).getTime()
            );
          }
        },
      },
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
    // 时间开始选择器
    startTimeStatus:function(value){
      this.query.startTime = value
    },
    // 时间结束选择器
    endTimeStatus:function(value){
      this.query.endTime = value
    },
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
      this.$axios.post(API.ROTATION_LIST_API, this.query).then(res => {
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
    /**页面必要代码End */
    //判断点击哪个按钮
    chooseClick(method) {
      if ('addRotation' == method) {
        this.addRotation();
      }else if ('editRotation' == method) {
        this.editRotation();
      } else if ('delRotation' == method) {
        this.delRotation();
      }
    },
    //新增
    addRotation() {
      this.$refs.rotationAdd.openDialog(true);
    },
    //修改
    editRotation() {
      if (1 == this.multipleSelection.length) {
        //为了防止自组件的值  影响父组件
        this.$refs.rotationEdit.openDialog(true, JSON.parse(JSON.stringify(this.multipleSelection[0])));
      } else {
        this.$message({
          showClose: true,
          message: "请选择一条数据进行修改",
          type: 'error'
        });
      }
    },
    //删除
    delRotation() {
      if (0 != this.multipleSelection.length) {
        this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let params = {
            bannerCodeList: []
          };
          for (var i = 0; i < this.multipleSelection.length; i++) {
            params.bannerCodeList.push(this.multipleSelection[i].bannerCode);
          }
          this.loading.startLoading();
          this.$axios.post(API.DELETE_ROTATION_API, params).then(res => {
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
  }
}
</script>
<style lang="scss" scoped>
.rotation .handle-box{
  margin-top:10px; 
}
</style>