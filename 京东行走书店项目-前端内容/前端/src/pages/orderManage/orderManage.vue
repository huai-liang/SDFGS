<template>
  <div class="goodsManage">
    <!-- 搜索区域 -->
    <el-card class="searchModule">
      <el-form :model="query" label-width="100px" :inline="true" ref="queryFrom">
        <el-form-item label="订单号" prop="orderNo">
          <el-input v-model="query.orderNo" clearable></el-input>
        </el-form-item>
        <el-form-item label="订单日期" prop="orderTime">
          <el-date-picker format="yyyy-MM-dd HH:mm" value-format="yyyy-MM-dd HH:mm" v-model="orderTime" clearable type="datetimerange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" style="padding:0;width:320px;">
          </el-date-picker>  
        </el-form-item>
        <el-form-item label="校边店" prop="storeName">
          <el-input v-model="query.storeName" clearable></el-input>
        </el-form-item>
        <el-form-item label="订单状态" prop="orderStatus">
          <el-select v-model="query.orderStatus" placeholder="请选择">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="付款时间" prop="payTime">
          <el-date-picker format="yyyy-MM-dd HH:mm" value-format="yyyy-MM-dd HH:mm" v-model="payTime" clearable type="datetimerange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" style="padding:0;width:320px;">
          </el-date-picker>  
        </el-form-item>
        <el-form-item label="下单人" prop="userName">
          <el-input v-model="query.userName" clearable></el-input>
        </el-form-item>
        <el-form-item label="下单人手机号" prop="phone">
          <el-input v-model="query.phone" clearable></el-input>
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

        <!-- <el-button type="primary" class="operateBtn" icon="el-icon-share"    @click="view()">查看详情</el-button>
        <el-button type="primary" class="operateBtn" icon="el-icon-download"   @click="downData()">导出</el-button>
        <el-button type="primary" class="operateBtn" icon="el-icon-edit"    @click="edit('是否确认取消选中的订单？','6','cancelOrder')">取消订单</el-button>
        <el-button type="primary" class="operateBtn" icon="el-icon-edit"    @click="editRemark()">订单备注</el-button>
        <el-button type="primary" class="operateBtn" icon="el-icon-edit"    @click="edit('选中的订单是否确认付款？','1','confirmPay')">确认付款</el-button>
        <el-button type="primary" class="operateBtn" icon="el-icon-edit"    @click="edit('是否确认取消付款？','0','cancelPay')">取消付款</el-button>
        <el-button type="primary" class="operateBtn" icon="el-icon-edit"    @click="edit('是否确认订单到货？','2','orderArrival')">订单到货</el-button>
        <el-button type="primary" class="operateBtn" icon="el-icon-edit"    @click="edit('是否确认取消到货？','1','cancelArrival')">取消到货</el-button>
        <el-button type="primary" class="operateBtn" icon="el-icon-edit"    @click="edit('是否确认订单已取货？','3','pickUp')">订单已取货</el-button>
        <el-button type="primary" class="operateBtn" icon="el-icon-edit"    @click="edit('是否确认取消已取货？','2','cancelPickUp')">取消已取货</el-button> -->
       <!--  <el-button type="primary" class="operateBtn" icon="el-icon-edit"    @click="lookRecord()">查看日志</el-button> -->
      </div>
      <!--内容-->
      <sirui-table
        class="operateTable"
        :page="page"
        :loading="load"
        :data="tableData"
        :tableHeight="450"
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
              <a href="javascript:void(0);">详情</a>
            </span>
            <span
              v-else-if="item.prop === 'gmtCreate' || item.prop === 'payTime'"
            >{{scope.row[item.prop] | dateTimeFormat}}</span>
            <span v-else>{{scope.row[item.prop]}}</span>
          </template>
        </el-table-column>
      </sirui-table>
    </div>
     <!--查看详情-->
    <orderView ref="orderView" ></orderView>
    <!--订单备注-->
    <orderRemark ref="orderRemark" v-on:getData="getData"></orderRemark>
    <!--查看日志-->
    <orderRecord ref="orderRecord" ></orderRecord>
    <!-- 部分到货 -->
    <div class="userDialog">
      <el-dialog width="30%" title="订单到货" :visible.sync="dialogOrderArrival" :close-on-click-modal="false">
        <el-form
          :model="orderArrivalInfo"
          label-width="100px"
          ref="orderArrivalInfo"
          :inline="true"
          label-position="right"
        >
          <el-form-item label="订单到货来源" prop="remark">
            <el-checkbox-group v-model="orderArrivalInfo.remark">
              <el-checkbox label="1">京东万家</el-checkbox>
              <el-checkbox label="0">行走书店</el-checkbox>
            </el-checkbox-group>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="orderArrivalSure()">确定部分到货</el-button>
          <el-button @click="cancelOrderArrivalDialog()">取 消</el-button>
        </div>
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
import API from '@/api/orderManage/orderManage';
import SiruiTable from '@/components/SiruiTable';
import orderView from '@/pages/orderManage/orderComponent/orderView';
import orderRemark from '@/pages/orderManage/orderComponent/orderRemark';
import orderRecord from '@/pages/orderManage/orderComponent/orderRecord';
export default {
  components: {
    SiruiTable,
    orderView,
    orderRemark,
    orderRecord
  },
  name: 'orders',
  data() {
    return {
      options: [{
        value: '0',
        label: '待付款'
      }, {
        value: '1',
        label: '已付款'
      },{
        value: '2',
        label: '待取货'
      },{
        value: '3',
        label: '已完成'
      },{
        value: '6',
        label: '已取消'
      },{
        value: '8',
        label: '部分到货'
      }],
      orderTime:[],//订单日期
      payTime:[],//付款日期
      query: {
        orderNo:'',//订单号
        startTime:'',//订单开始日期
        endTime:'',//订单结束日期
        storeName:'',//校边店
        orderStatus:'',//订单状态
        userName:'',//下单人
        phone:'',//下单人手机号
        payStartTime:'',//付款起止时间
        payEndTime:'',//付款截止时间
      },
      load: false,
      menuButtonList: [],
      columnList: [
        { prop: 'orderNo', label: '订单号', sort: true, fixed: true, width: 150 },
        { prop: 'orderStatus', label: '订单状态', width: 150 },
        { prop: 'storeName', label: '校边店', width: 80 },
        { prop: 'goodsCnt', label: '商品总数', width: 120 },
        { prop: 'orderMoney', label: '订单金额', width: 120 },
        { prop: 'userName', label: '下单人', width: 80 },
        { prop: 'phone', label: '下单人手机号', width: 180 },
        { prop: 'gmtCreate', label: '订单日期', width: 180 },
        { prop: 'payTime', label: '确认付款时间', width: 180 },
        { prop: 'remark', label: '备注', width: 200 },
        //{ prop: 'detail', label: '操作', fixed: 'right', width: 80 }
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
      dialogOrderArrival: false,//订单
      orderArrivalInfo: {
        remark:[]
      },//订单
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
      if(0!=this.orderTime.length){
        this.query.startTime=this.orderTime[0];
        this.query.endTime=this.orderTime[1];
      }else{
        this.query.startTime="";
        this.query.endTime="";
      }
      if(0!=this.payTime.length){
        this.query.payStartTime=this.payTime[0];
        this.query.payEndTime=this.payTime[1];
      }else{
        this.query.payStartTime="";
        this.query.payEndTime="";
      }
      this.load = true;
      this.$axios.post(API.ORDER_LIST_API,this.query).then(res => {
        this.load = false;
        if (0 == res.data.code) {
          this.tableData = res.data.data.list;
          this.page.total = res.data.data.total;
          for(var i=0;i<this.tableData.length;i++){
            if(this.tableData[i].orderStatus=='0'){
              this.tableData[i].orderStatus='待付款';
            }else if(this.tableData[i].orderStatus=='1'){
              this.tableData[i].orderStatus='已付款';
            }else if(this.tableData[i].orderStatus=='2'){
              this.tableData[i].orderStatus='待取货';
            }else if(this.tableData[i].orderStatus=='3'){
              this.tableData[i].orderStatus='已完成';
            }else if(this.tableData[i].orderStatus=='6'){
              this.tableData[i].orderStatus='已取消';
            }else if(this.tableData[i].orderStatus=='8'){
              this.tableData[i].orderStatus='部分到货';
            }
          }
        }else if(5==res.data.code){
          this.tableData = [];
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
      this.orderTime=[];//订单日期
      this.payTime=[];//付款日期
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
      if ('view' == method) {
        this.view();//查看详情
      }else if('downData' == method) {
        this.downData();//导出
      }else if('cancelOrder' == method) {
        this.edit('是否确认取消选中的订单？','6','cancelOrder');//取消订单
      }else if('editRemark' == method) {
        this.editRemark();//订单备注
      }else if('confirmPay' == method) {
        this.edit('选中的订单是否确认付款？','1','confirmPay');//确认付款
      }else if('cancelPay' == method) {
        this.edit('是否确认取消付款？','0','cancelPay');//取消付款
      }else if('orderArrival' == method) {
        this.orderArrival();//确认订单是否到货
      }else if('cancelArrival' == method) {
        this.edit('是否确认取消到货？','1','cancelArrival');//取消到货
      }else if('pickUp' == method) {
        this.edit('是否确认订单已取货？','3','pickUp');//订单已取货
      }else if('cancelPickUp' == method) {
        this.edit('是否确认取消已取货？','2','cancelPickUp');//取消已取货
      }else if('lookRecord'==method) {//查看日志
        this.lookRecord();
      }
    },
    //查看详情
    view() {
      if (1 == this.multipleSelection.length) {
        //为了防止自组件的值  影响父组件
        this.$refs.orderView.openDialog(true, JSON.parse(JSON.stringify(this.multipleSelection[0])));
      } else {
        this.$message({
          showClose: true,
          message: "请选择一条数据进行查看",
          type: 'error'
        });
      }
    },
    //查看日志
    lookRecord() {
      if (1 == this.multipleSelection.length) {
        //为了防止自组件的值  影响父组件
        this.$refs.orderRecord.openDialog(true, JSON.parse(JSON.stringify(this.multipleSelection[0])));
      } else {
        this.$message({
          showClose: true,
          message: "请选择一条数据进行查看",
          type: 'error'
        });
      }
    },
    //订单导出
    downData() {
      if(0!=this.multipleSelection.length){
        let params = {
          id: []
        };
        for (var i = 0; i < this.multipleSelection.length; i++) {
          params.id.push(this.multipleSelection[i].id);
        }
        window.location.href=encodeURI(this.downDataUrl+API.EXPORT_ORDER_API+'?orderList='+params.id+'&access_token=' + this.$store.state.userInfo.userInfo.access_token);
      }else{
        if(0!=this.orderTime.length){
          this.query.startTime=this.orderTime[0];
          this.query.endTime=this.orderTime[1];
        }else{
          this.query.startTime="";
          this.query.endTime="";
        }
        if(0!=this.payTime.length){
          this.query.payStartTime=this.payTime[0];
          this.query.payEndTime=this.payTime[1];
        }else{
          this.query.payStartTime="";
          this.query.payEndTime="";
        }
        window.location.href=encodeURI(this.downDataUrl+API.EXPORT_ORDER_API+'?access_token=' + this.$store.state.userInfo.userInfo.access_token+'&orderNo='+this.query.orderNo+'&startTime='+this.query.startTime+'&endTime='+this.query.endTime+'&storeName='+this.query.storeName+'&orderStatus='+this.query.orderStatus+'&userName='+this.query.userName+'&phone='+this.query.phone+'&payStartTime='+this.query.payStartTime+'&payEndTime='+this.query.payEndTime);
      }
    },
    //取消订单等接口
    edit(key,value,nameStatus) {
      for (var i = 0; i < this.multipleSelection.length; i++) {
        if(nameStatus=='cancelOrder'&&this.multipleSelection[i].orderStatus!='待付款'){
          this.$message({
            showClose: true,
            message: '有订单已进入流程不能取消该订单',
            type: 'error'
          });
          return;
        }else if(nameStatus=='confirmPay'&&this.multipleSelection[i].orderStatus!='待付款'){//确认付款
          this.$message({
            showClose: true,
            message: '当前订单状态不是待付款不能操作此订单',
            type: 'error'
          });
          return;
        }else if(nameStatus=='cancelPay'&&this.multipleSelection[i].orderStatus!='已付款'){//取消付款
          this.$message({
            showClose: true,
            message: '当前订单状态不是已付款不能操作此订单',
            type: 'error'
          });
          return;
        }else if(nameStatus=='cancelArrival'&&this.multipleSelection[i].orderStatus!='待取货'){//取消到货
          this.$message({
            showClose: true,
            message: '当前订单状态不是待取货不能操作此订单',
            type: 'error'
          });
          return;
        }else if(nameStatus=='pickUp'&&this.multipleSelection[i].orderStatus!='待取货'){//订单已取货
          this.$message({
            showClose: true,
            message: '当前订单状态不是待取货不能操作此订单',
            type: 'error'
          });
          return;
        }else if(nameStatus=='cancelPickUp'&&this.multipleSelection[i].orderStatus!='已完成'){//取消已到货
          this.$message({
            showClose: true,
            message: '当前订单状态不是已完成不能操作此订单',
            type: 'error'
          });
          return;
        }
      }
      if (0 != this.multipleSelection.length) {
        this.$confirm(key, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let params = {
            id:[],
            orderNo: [],
            //isDeleted:1,
            orderStatus:value
          };
          // if(value=='isDeleted'){
          //   params.isDeleted=1;
          //   delete params.orderStatus;
          // }else{
          //   params.orderStatus=value;
          //   delete params.isDeleted;
          // }
          for (var i = 0; i < this.multipleSelection.length; i++) {
            params.id.push(this.multipleSelection[i].id);
            params.orderNo.push(this.multipleSelection[i].orderNo);
          }
          params.id = params.id.join(",");
          params.orderNo = params.orderNo.join(",");
          this.loading.startLoading();
          this.$axios.post(API.EDIT_ORDER_API, params).then(res => {
            this.loading.endLoading();
            if (0 == res.data.code) {
              this.dialogOrderArrival=false;
              this.searchForm();
              this.$message({
                type: 'success',
                message: res.data.msg
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
            message: '已取消'
          });
        });
      } else {
        this.$message({
          showClose: true,
          message: "请选择数据进行操作",
          type: 'error'
        });
      }
    },
    //订单部分到货或者全部到货 
    orderArrival() {
      if(1==this.multipleSelection.length) {
        if(this.multipleSelection[0].orderStatus=='已付款') {
          let params = {
            orderId: this.multipleSelection[0].id
          };          
          this.$axios.post(API.LISTCHANNEL_ORDER_API, params).then(res => {
            if (0 == res.data.code) {
              //console.log(res.data.data.length);
              if(res.data.data.length==2){
                this.orderArrivalInfo.remark=[];
                this.dialogOrderArrival=true;
              }else if(res.data.data.length==1) {
                this.edit('是否确认订单到货？','2','orderArrival');//订单到货
              }
            } else {
              this.$message({
                showClose: true,
                message: res.data.msg,
                type: 'error'
              });
            }
          })
        } else if(this.multipleSelection[0].orderStatus=='部分到货') {
          this.edit('是否确认订单到货？','2','orderArrival');//订单到货
        } else {
          this.$message({
            showClose: true,
            message: '当前订单状态不是已付款或者部分到货不能操作此订单',
            type: 'error'
          });
          return;
        }
      }else{
        this.$message({
          showClose: true,
          message: "请选择一条数据进行操作",
          type: 'error'
        });
      }
    },
    //确定部分到货
    orderArrivalSure() {
      if (1 == this.orderArrivalInfo.remark.length) {
        this.$confirm('是否确认订单到货', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let params = {
            id: this.multipleSelection[0].id,
            orderNo: this.multipleSelection[0].orderNo,
            orderStatus:'8',
            channelType:this.orderArrivalInfo.remark[0]
          };
          this.loading.startLoading();
          this.$axios.post(API.EDIT_ORDER_API, params).then(res => {
            this.loading.endLoading();
            if (0 == res.data.code) {
              this.dialogOrderArrival=false;
              this.searchForm();
              this.$message({
                type: 'success',
                message: res.data.msg
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
            message: '已取消'
          });
        });
      }else if(2 == this.orderArrivalInfo.remark.length) {
        this.edit('是否确认订单到货？','2','orderArrival');//订单到货
      } else {
        this.$message({
          showClose: true,
          message: "请选择订单到货来源",
          type: 'error'
        });
      }
    },
    //取消
    cancelOrderArrivalDialog() {
      this.orderArrivalInfo.remark=[];
      this.dialogOrderArrival=false;
    },
    //订单备注
    editRemark() {
      if (1 == this.multipleSelection.length) {
        //为了防止自组件的值  影响父组件
        this.$refs.orderRemark.openDialog(true, JSON.parse(JSON.stringify(this.multipleSelection[0])));
      } else {
        this.$message({
          showClose: true,
          message: "请选择一条数据进行备注",
          type: 'error'
        });
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


