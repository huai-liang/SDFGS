<template>
  <div class="goodsManage">
    <!-- 搜索区域 -->
    <el-card class="searchModule">
      <el-form :model="query" label-width="100px" :inline="true" ref="queryFrom">
        <el-form-item label="商品编码" prop="skuNo">
          <el-input v-model="query.skuNo" clearable></el-input>
        </el-form-item>
        <el-form-item label="商品名称" prop="skuName">
            <el-input v-model="query.skuName" clearable></el-input>
        </el-form-item>
        <el-form-item label="门店编码" prop="storeNo">
          <el-input v-model="query.storeNo" clearable></el-input>
        </el-form-item>
        <el-form-item label="门店名称" prop="storeName">
          <el-input v-model="query.storeName" clearable></el-input>
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
            <span
              v-if="item.prop === 'gmtCreate' || item.prop === 'gmtModified'"
            >{{scope.row[item.prop] | dateTimeFormat}}</span>
            <span v-else>{{scope.row[item.prop]}}</span>
          </template>
        </el-table-column>
      </sirui-table>
      <!-- 评论详情 -->
      <el-dialog
        title="评论详情"
        :visible.sync="commentPopInfo"
        width="50%"
        :close-on-click-modal="false"
        :before-close="cancelDialog"
        >
        <el-form
            :model="commentInfo"
            ref="commentInfo"
            label-width="100px"
            label-position="right"
            class=""
        >
            <div class="clr rate-div">
                <img :src="commentInfo.skuPicUrl" width="60px" height="80px" class="fl">
                <div class="rate-box">
                    <el-rate
                        v-model="commentInfo.starLevel"
                        disabled>
                    </el-rate>
                </div>
            </div>
            <div class="nav-comment">评论内容</div>
            <div>
                <p class="lh2">{{commentInfo.appraiseInfo}}</p>
                <div class="demo-image clr">
                        <img v-for="key in commentInfo.appraisePicList"
                            style="width: 80px; height: 80px;margin:5px;float: left"
                            :src="key"
                            :key="key.index"
                            >
                </div>
            </div>
        </el-form>
    </el-dialog>
    </div>
  </div>
</template>
<script>
import { mapState } from 'vuex';
//测试环境
import { devUrl } from '@/common/config/dev.env'
//正式环境
import { prodUrl } from '@/common/config/prod.env'
import API from '@/api/commentManage/commentManage';
import SiruiTable from '@/components/SiruiTable';

export default {
  components: {
    SiruiTable,
  },
  name: 'goods',
  data() {
    return {
      query: {
        skuNo:'',//商品编码
        skuName:'',//商品名称
        storeNo:'',//门店编码
        storeName:'',//门店名称
      },
      fill:'fill',
      load: false,
      menuButtonList: [],
      columnList: [
        { prop: 'skuNo', label: '商品编号', width: 200 },
        { prop: 'skuName', label: '商品名称', },
        { prop: 'starLevel', label: '评价星级', width: 120 },
        { prop: 'storeName', label: '下单门店', width: 220 },
        { prop: 'orderTime', label: '评论时间', width: 200 },
      ],
      tableData: [],
      tableHeight: document.body.scrollHeight - 460,
      page: {
        pages: 0,//总共多少页
        pageNum: 1,//第几页
        pageSize: 10,//一页多少条数据
        total: 0//总共多少条
      },
      multipleSelection: [],//存放选中的值
      commentPopInfo:false,//详情弹窗
      commentInfo:{
        skuPicUrl:'', //图片路径
        starLevel:'',//星级
        appraiseInfo:'',//评价内容
        appraisePicList:[],//评论图片
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
    getData() {
      this.query.pageSize = this.page.pageSize;
      this.query.pageNum = this.page.pageNum;
      this.load = true;
      this.$axios.post(API.POSTGOODSAPPRAISE_LIST_API,this.query).then(res => {
        this.load = false;
        if (0 == res.data.code) {
          this.tableData = res.data.data.list;
          this.page.total = res.data.data.total;
        } else if(5 == res.data.code){
          this.tableData=[];
          this.page.total = 0;
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
      if ('del' == method) {
        this.del();
      } else if ('info' == method) {
        this.info();
      }
    },
    //取消
    cancelDialog(){
      this.commentPopInfo=false;//详情弹窗
      this.$refs["commentInfo"].resetFields();
    },
    //删除
    del() {
      if (0 != this.multipleSelection.length) {
        this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let params = {
            idList: []
          };
          for (var i = 0; i < this.multipleSelection.length; i++) {
            params.idList.push(this.multipleSelection[i].id);
          }
          this.loading.startLoading();
          this.$axios.post(API.DELETE_GOODS_API, params).then(res => {
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
    //详情
    info() {
        this.commentInfo={
            skuPicUrl:'', //图片路径
            starLevel:'',//星级
            appraiseInfo:'',//评价内容
            appraisePicList:[],//评论图片
        }
        if (1 == this.multipleSelection.length) {
            this.commentPopInfo=true;//详情弹窗
            let params={
                id:this.multipleSelection[0].id
            }
            this.loading.startLoading();
            this.$axios.post(API.INFO_GOODS_API,params).then(res=>{
                this.loading.endLoading();
                if (0 == res.data.code) {
                    this.commentInfo=res.data.data;
                } else {
                this.$message({
                    showClose: true,
                    message: res.data.msg,
                    type: 'error'
                });
                }
            })
        } else {
            this.$message({
            showClose: true,
            message: "请选择一条数据进行查看",
            type: 'error'
            });
        }
    },
  }
}
</script>
<style lang="scss" scoped>
    .el-dialog__header {
        background:#00D1B2 !important;
    }
    .el-dialog__title {
        color: #fff !important;
    }
    .clr::after {
      clear: both;
      content: ".";
      display: block;
      height: 0;
      overflow: hidden;
  }
  .clr {
      *zoom: 1;
  }
  .handle-box {
    margin-top:10px;
  }
  .rate-div {
      padding-bottom:15px;
  }
    .fl{
        float: left;
    }
    .rate-box {
        width:200px;
        padding: 30px 0 0 20px;
        float: left;
    }
    .nav-comment {
        height: 30px;
        line-height: 30px;
        padding-left:10px;
        background:#f5f5f5;
        color: #000;
    }
    .lh2 {
        padding-top:10px;
        line-height: 2;
        text-indent:.3rem;
    }
    .demo-image {
        padding-top: 15px;
    }
    
</style>


