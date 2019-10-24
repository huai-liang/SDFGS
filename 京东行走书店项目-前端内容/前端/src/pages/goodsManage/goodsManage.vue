<template>
  <div class="goodsManage">
    <!-- 搜索区域 -->
    <el-card class="searchModule">
      <el-form :model="query" label-width="100px" :inline="true" ref="queryFrom">
        <el-form-item label="商品名称" prop="skuName">
          <el-input v-model="query.skuName" clearable></el-input>
        </el-form-item>
        <el-form-item label="商品状态" prop="status">
          <el-select v-model="query.status" placeholder="请选择">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="广告词" prop="advertising">
          <el-input v-model="query.advertising" clearable></el-input>
        </el-form-item>
        <el-form-item label="出版社" prop="press">
          <el-input v-model="query.press" clearable></el-input>
        </el-form-item>
        <el-form-item label="作者" prop="author">
          <el-input v-model="query.author" clearable></el-input>
        </el-form-item>
        <el-form-item label="一级分类" prop="oneLevel">
          <el-select v-model="query.oneLevel" filterable clearable @change='getSecondLevel'>
            <el-option
              v-for="item in onelevelData"
              :key="item.cateName"
              :label="item.cateName"
              :value="item.cateCode">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="二级分类" prop="twoLevel">
          <el-select v-model="query.twoLevel" clearable filterable>
            <el-option
              v-for="item in twoLevelData"
              :key="item.cateName"
              :label="item.cateName"
              :value="item.cateCode">
            </el-option>
          </el-select>
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
       <!--  <el-button type="primary" class="operateBtn" icon="el-icon-plus"    @click="add('addInfo')">新增</el-button>
        <el-button type="primary" class="operateBtn" icon="el-icon-edit"    @click="edit()">修改</el-button>
        <el-button type="primary" class="operateBtn" icon="el-icon-delete"    @click="del()">删除</el-button>
        <el-button type="primary" class="operateBtn" icon="el-icon-sort-up"  @click="grounding('1')">上架</el-button>
        <el-button type="primary" class="operateBtn" icon="el-icon-sort-down"   @click="grounding('0')">下架</el-button>
        <el-button type="primary" class="operateBtn" icon="el-icon-download"   @click="modelDown()">模板下载</el-button>
        <el-button type="primary" class="operateBtn" icon="el-icon-download"   @click="downData()">导出</el-button>
        <el-button type="primary" class="operateBtn" icon="el-icon-upload2"   @click="importData()">导入</el-button>
        <el-button type="primary" class="operateBtn" icon="el-icon-s-goods"   @click="settingGoods()">设置关联商品</el-button>
        <el-button type="primary" class="operateBtn" icon="el-icon-upload2"   @click="importDataPic()">模板图片上传</el-button> -->
        <!-- <el-button type="primary" class="operateBtn" icon="el-icon-upload2"   @click="importLotSizePic()">图片批量上传</el-button> -->
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
            <span v-if="item.prop === 'detail'">
              <a href="javascript:void(0);" @click="lookDetail(scope.row)">详情</a>
            </span>
            <span
              v-else-if="item.prop === 'putawayTime' || item.prop === 'gmtCreate'"
            >{{scope.row[item.prop] | dateTimeFormat}}</span>
            <span v-else>{{scope.row[item.prop]}}</span>
          </template>
        </el-table-column>
      </sirui-table>
    </div>
    <!--新增-->
    <goodsAdd ref="GoodsAdd" v-on:search="searchForm"></goodsAdd>
    <!--修改-->
    <goodsEdit ref="goodsEdit" v-on:getData="getData"></goodsEdit>
    <!--详情-->
    <goodsView ref="goodsView"></goodsView>
     <!--导入数据-->
    <goodsUpload ref="goodsUpload" v-on:getData="getData"></goodsUpload>
     <!--设置关联商品查询-->
    <goodsRelated ref="goodsRelated" v-on:getData="getData"></goodsRelated>
    <!--上传图片-->
    <goodsUploadPic ref="goodsUploadPic" v-on:getData="getData"></goodsUploadPic>
    <!--批量上传图片-->
    <goodsUploadLotSizePic ref="goodsUploadLotSizePic" v-on:getData="getData"></goodsUploadLotSizePic>
  </div>
</template>
<script>
import { mapState } from 'vuex';
//测试环境
import { devUrl } from '@/common/config/dev.env'
//正式环境
import { prodUrl } from '@/common/config/prod.env'
import API from '@/api/goodsManage/goodsManage';
import SiruiTable from '@/components/SiruiTable';
import GoodsAdd from '@/pages/goodsManage/goodsComponent/goodsAdd';
import GoodsEdit from '@/pages/goodsManage/goodsComponent/goodsEdit';
import GoodsView from '@/pages/goodsManage/goodsComponent/goodsView';
import GoodsUpload from '@/pages/goodsManage/goodsComponent/goodsUpload';
import GoodsRelated from '@/pages/goodsManage/goodsComponent/goodsRelated';
import GoodsUploadPic from '@/pages/goodsManage/goodsComponent/goodsUploadPic';
import goodsUploadLotSizePic from '@/pages/goodsManage/goodsComponent/goodsUploadLotSizePic';
export default {
  components: {
    SiruiTable,
    GoodsAdd,
    GoodsEdit,
    GoodsView,
    GoodsUpload,
    GoodsRelated,
    GoodsUploadPic,
    goodsUploadLotSizePic
  },
  name: 'goods',
  data() {
    return {
      options: [{
        value: '1',
        label: '上架'
      }, {
        value: '0',
        label: '下架'
      }, {
        value: '2',
        label: '未发布'
      }],
      query: {
        skuName:'',//商品名称
        status:'',//商品状态
        advertising:'',//广告词
        press:'',//出版社
        author:'',//作者
        oneLevel:'',//一级分类
        twoLevel:'',//二级分类
      },
      load: false,
      menuButtonList: [],
      columnList: [
        { prop: 'skuNo', label: 'SKU商品编号', sort: true, fixed: true, width: 150 },
        { prop: 'skuName', label: 'SKU商品名称', width: 150 },
        { prop: 'isbn', label: 'ISBN', width: 80 },
        { prop: 'fixPrice', label: '定价', width: 120 },
        { prop: 'sellingPrice', label: '销售价', width: 120 },
        { prop: 'saleCnt', label: '已售数量', width: 80 },
        { prop: 'status', label: '商品状态', width: 180 },
        { prop: 'oneLevel', label: '一级分类', width: 120 },
        { prop: 'twoLevel', label: '二级分类', width: 120 },
        { prop: 'advertising', label: '广告词', width: 120 },
         { prop: 'channelType', label: '渠道', width: 120 },
        { prop: 'press', label: '出版社', width: 120 },
        { prop: 'author', label: '作者', width: 120 },
        { prop: 'putawayTime', label: '上架时间', width: 180 },
        { prop: 'gmtCreate', label: '创建时间', width: 180 },
        { prop: 'browseCount', label: '浏览量', width: 120 },
        { prop: 'detail', label: '操作', fixed: 'right', width: 80 }
      ],
      tableData: [],
      tableHeight: document.body.scrollHeight - 460,
      page: {
        pages: 0,//总共多少页
        pageNum: 1,//第几页
        pageSize: 10,//一页多少条数据
        total: 0//总共多少条
      },
      onelevelData:[],//一级分类
      twoLevelData:[],//二级分类
      multipleSelection: [],//存放选中的值
      downDataUrl:process.env.NODE_ENV === 'production'?prodUrl:devUrl,//下载地址
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
    this.getOneLevel();//获取一级分类
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
      this.$axios.post(API.GETPAGETGOODSSKU_LIST_API,this.query).then(res => {
        this.load = false;
        if (0 == res.data.code) {
          this.tableData = res.data.data.list;
          this.page.total = res.data.data.total;
          for(var i=0;i<this.tableData.length;i++){
            if(this.tableData[i].status=='0'){
              this.tableData[i].status='下架';
            }else if(this.tableData[i].status=='1'){
              this.tableData[i].status='上架';
            }else if(this.tableData[i].status=='2'){
              this.tableData[i].status='未发布';
            }

            if(this.tableData[i].channelType=='0'){
              this.tableData[i].channelType='行走书店';
            }else if(this.tableData[i].channelType=='1'){
              this.tableData[i].channelType='京东万家';
            }
          }
        }else if(5==res.data.code) {
          this.tableData = [];
          this.page.total = 0;
        }  else {
          this.$message({
            showClose: true,
            message: res.data.msg,
            type: 'error'
          });
        }
      })
    },
    getOneLevel() {
      this.loading.startLoading();
      this.$axios.post(API.GOODS_LIST_API).then(res => {
      this.loading.endLoading();
        if (0 == res.data.code) {
          this.onelevelData = res.data.data;
        } else {
          this.$message({
            showClose: true,
            message: res.data.msg,
            type: 'error'
          });
        }
      });
    },
    //下拉选择二级分类
    getSecondLevel(){
      var that=this;
      this.onelevelData.forEach(function(item){
        if(item.cateCode==that.query.oneLevel){
          that.query.twoLevel='';
          that.twoLevelData=item.secondCategory;
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
    //按钮
    chooseClick(method) {
      if ('add' == method) {
        this.add('addInfo');
      } else if ('edit' == method) {
        this.edit();
      } else if ('del' == method) {
        this.del();
      } else if ('groundingUp' == method) {
        this.grounding('1');
      } else if ('groundingDown' == method) {
        this.grounding('0');
      }else if ('modelDown' == method) {
        this.modelDown();//模板下载
      }else if ('downData' == method) {
        this.downData();//导出
      }else if ('importData' == method) {
        this.importData();//导入
      }else if ('settingGoods' == method) {
        this.settingGoods();//设置关联商品
      }else if ('importDataPic'==method) {
        this.importDataPic();//图片上传
      }else if ('importLotSizePic'==method) {
        this.importLotSizePic();//图片批量上传
      }
    },
    //新增
    add() {
      this.$refs.GoodsAdd.openDialog(true);
    },
    //修改
    edit() {
      if (1 == this.multipleSelection.length) {
        let params = {
            skuNo: this.multipleSelection[0].skuNo
        };
        // if(this.multipleSelection[0].status=="上架"){
        //   this.$message({
        //     showClose: true,
        //     message: '已上架商品不能修改',
        //     type: 'error'
        //   });
        //   return;
        // }
        this.loading.startLoading();
        this.$axios.post(API.SELECTPICURL_API,params).then(res => {
          this.loading.endLoading();
          if (0 == res.data.code) {
            //为了防止自组件的值  影响父组件
            this.$refs.goodsEdit.openDialog(true, JSON.parse(JSON.stringify(this.multipleSelection[0])),res.data.data);
          } else {
            this.$message({
              showClose: true,
              message: res.data.msg,
              type: 'error'
            });
          }
        });
      } else {
        this.$message({
          showClose: true,
          message: "请选择一条数据进行修改",
          type: 'error'
        });
      }
    },
    //设置关联商品
    settingGoods() {
      if (1 == this.multipleSelection.length) {
        let params = {
          skuNo: this.multipleSelection[0].skuNo
        };
        this.loading.startLoading();
        this.$axios.post(API.GETGOODSRECOMMEND_API,params).then(res => {
          this.loading.endLoading();
          if (0 == res.data.code) {
            //为了防止自组件的值  影响父组件
            //console.log(res);
            this.$refs.goodsRelated.openDialog(true,JSON.parse(JSON.stringify(this.multipleSelection[0])),res.data.data);
          } else {
            this.$message({
              showClose: true,
              message: res.data.msg,
              type: 'error'
            });
          }
        });        
      } else {
        this.$message({
          showClose: true,
          message: "请选择一条数据进行设置关联商品",
          type: 'error'
        });
      }
    },
    //导入数据
    importData() {
      this.$refs.goodsUpload.openDialog(true);
    },
    //上传图片
    importDataPic() {
      this.$refs.goodsUploadPic.openDialog(true);
    },
    //图片批量上传
    importLotSizePic() {
      this.$refs.goodsUploadLotSizePic.openDialog(true);
    },
    //查看详情
    lookDetail(item) {
      let params = {
          skuNo: item.skuNo
      };
      this.loading.startLoading();
      this.$axios.post(API.SELECTPICURL_API,params).then(res => {
        this.loading.endLoading();
        if (0 == res.data.code) {
          //为了防止自组件的值  影响父组件
          this.$refs.goodsView.openDialog(true, JSON.parse(JSON.stringify(item)),res.data.data);
        } else {
          this.$message({
            showClose: true,
            message: res.data.msg,
            type: 'error'
          });
        }
      });
      
    },
    //删除
    del() {
      if (0 != this.multipleSelection.length) {
        for (var i = 0; i < this.multipleSelection.length; i++) {
          if(this.multipleSelection[i].status=="上架"){
            this.$message({
              showClose: true,
              message: '已上架商品不能删除',
              type: 'error'
            });
            return;
          }
        }
        this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let params = {
            skuNo: []
          };
          for (var i = 0; i < this.multipleSelection.length; i++) {
            params.skuNo.push(this.multipleSelection[i].skuNo);
          }
          params.skuNo = params.skuNo.join(",");
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
    //上架或者下架
    grounding(value) {
      if(0 != this.multipleSelection.length){
        let params = {
            skuNo: [],
            status:value
        };
        for (var i = 0; i < this.multipleSelection.length; i++) {
          params.skuNo.push(this.multipleSelection[i].skuNo);
        }
        params.skuNo = params.skuNo.join(",");
        this.loading.startLoading();
        this.$axios.post(API.UPDATEPUTWAY_GOODS_API, params).then(res => {
          this.loading.endLoading();
          if (0 == res.data.code) {
            this.searchForm();
            this.$message({
              type: 'success',
              message: '修改成功!'
            });
          } else {
            this.$message({
              showClose: true,
              message: res.data.msg,
              type: 'error'
            });
          }
        });
      } else {
        this.$message({
          showClose: true,
          message: "请选择数据进行操作",
          type: 'error'
        });
      }
    },
    //模板下载
    modelDown() {
      //正确写法
      //window.location.href=encodeURI(this.downDataUrl+API.DELETE_GOODS_API+'?access_token=' + this.$store.state.userInfo.userInfo.access_token);
      //暂时的模板下载
      window.location.href=encodeURI("http://10.100.12.20:8001/group1/M00/00/0F/CmQMFF0DONaAfkCcAAAlI8kUYbQ83.xlsx?filename=sku模板.xlsx");
    },
    //商品导出
    downData() {
      if(0!=this.multipleSelection.length){
        let params = {
          skuNo: []
        };
        for (var i = 0; i < this.multipleSelection.length; i++) {
          params.skuNo.push(this.multipleSelection[i].skuNo);
        }
        params.skuNo = params.skuNo.join(",");
        window.location.href=encodeURI(this.downDataUrl+API.EXPORTEXCEL_GOODS_API+'?skuNo='+params.skuNo+'&access_token=' + this.$store.state.userInfo.userInfo.access_token);
      }else{
        window.location.href=encodeURI(this.downDataUrl+API.EXPORTEXCEL_GOODS_API+'?access_token=' + this.$store.state.userInfo.userInfo.access_token+'&skuName='+this.query.skuName+'&status='+this.query.status+'&advertising='+this.query.advertising+'&press='+this.query.press+'&author='+this.query.author+'&oneLevel='+this.query.oneLevel+'&twoLevel='+this.query.twoLevel);
      }
     
    },
  }
}
</script>
<style lang="scss" scoped>
  .operateBtn{
    margin-top:10px; 
  }
</style>


