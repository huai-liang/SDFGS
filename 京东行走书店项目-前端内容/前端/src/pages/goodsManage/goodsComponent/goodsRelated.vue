<template>
  <!-- 设置关联商品 -->
  <div class="userDialog">
    <el-dialog width="55%" title="设置关联商品" :visible.sync="dialogRelated" :close-on-click-modal="false">
      <div class="contentModule" style="margin-bottom: 10px;">
        <el-button type="primary" icon="el-icon-plus"    @click="addRelated()">新增</el-button>
        <el-button type="primary" icon="el-icon-delete"    @click="delRelated()">删除</el-button>
      </div>
      <el-table 
        :data="settingData" 
        border
        stripe 
        style="width: 100%"
        @select="chooseDelOne"
        @select-all="chooseDelAll"  
       >
        <el-table-column type="selection" width="55" ></el-table-column>
        <el-table-column prop="skuRecommend" label="商品编号" fixed="left" sortable>
        </el-table-column>
         <el-table-column prop="skuName" label="商品名称">
        </el-table-column>
      </el-table>
        <!-- 内层弹窗 -->
        <el-dialog
          width="50%"
          title="商品查询"
          :visible.sync="innerVisible"
          append-to-body 
          :close-on-click-modal="false">
          <!-- 搜索区域 -->
          <el-card class="searchModule">
            <el-form :model="queryRelated" label-width="100px" :inline="true" ref="queryFrom">
              <el-form-item label="商品名称" prop="skuName">
                <el-input v-model="queryRelated.skuName" clearable></el-input>
              </el-form-item>
              <div class="searchBtn">
                <el-button type="primary" @click="searchFormRelated()">查询</el-button>
                <el-button @click="resetFormRelated('queryFrom')">重置</el-button>
              </div>
            </el-form>
          </el-card>
          <el-table 
            :data="tableRelatedData" 
            border
            stripe
            :maxHeight="380"
            @select="chooseOne"
            @select-all="chooseAll" 
            style="width: 100%" 
           >
            <el-table-column type="selection" width="55" ></el-table-column>
            <el-table-column prop="skuNo" label="商品编号" fixed="left" sortable>
            </el-table-column>
             <el-table-column prop="skuName" label="商品名称">
            </el-table-column>
          </el-table>
          <div class="">
            <el-pagination style='margin-bottom:0;'
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="pageNum"
              :page-sizes="[10, 20, 50, 100]"
              :page-size="pageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total">
            </el-pagination>
          </div>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="editSure()">确 定</el-button>
            <el-button @click="cancelInnerDialog()">取 消</el-button>
          </div>
        </el-dialog>
    </el-dialog>
  </div>
</template>
<script>
import API from '@/api/goodsManage/goodsManage';
export default {
  name: 'goodsRelated',
  props: {
    user: {
      type: Object,
      default: () => { }
    }
  },
  data() {
    return {
      dialogRelated: false,//外层弹窗
      innerVisible: false,//内层弹窗

      tableRelatedData: [],//表格数据
      settingData:[],//已经设置
      skuNo:'',//列表页的sku
      selectId:[],//选中的商品编号
      selectedId: [],//删除选中的商品编号
      queryRelated: {
        skuName:'',//商品名称
      },
      pages: 0,//总共多少页
      pageNum: 1,//第几页
      pageSize: 10,//一页多少条数据
      total: 0//总共多少条
    }
  },
  methods: {
    //搜索
    searchFormRelated() {
      this.pageNum = 1;
      this.getDataRelated();
    },
    //重置
    resetFormRelated(from) {
      this.$refs[from].resetFields();
    },
    //查询
    getDataRelated() {
      this.queryRelated.pageSize = this.pageSize;
      this.queryRelated.pageNum = this.pageNum;
      this.loading.startLoading();
      this.$axios.post(API.GETPAGETGOODSSKU_LIST_API,this.queryRelated).then(res => {
        this.loading.endLoading();
        if (0 == res.data.code) {
          this.tableRelatedData = res.data.data.list;
          this.total = res.data.data.total;
        }else if(5==res.data.code) {
          this.tableData = [];
          this.total = 0;
        }  else {
          this.$message({
            showClose: true,
            message: res.data.msg,
            type: 'error'
          });
        }
      })
    },
    //每页条数
    handleSizeChange(val) {
      this.pageSize = val;
      this.getDataRelated();
    },
    //第几页
    handleCurrentChange(val) {
      this.pageNum = val;
      this.getDataRelated();      
    },
    //打开内部弹窗
    addRelated() {
      this.innerVisible=true;
    },
    //关闭内部弹窗
    cancelInnerDialog() {
      this.innerVisible=false;
    },
    //打开外部弹窗
    openDialog(flag, goods,settingData) {
      this.queryRelated.pageSize = this.pageSize;
      this.queryRelated.pageNum = this.pageNum;
      this.loading.startLoading();
      this.$axios.post(API.GETPAGETGOODSSKU_LIST_API,this.queryRelated).then(res => {
        this.loading.endLoading();
        if (0 == res.data.code) {
          this.tableRelatedData = res.data.data.list;
          this.total = res.data.data.total;
          this.dialogRelated = flag;
        }else if(5==res.data.code) {
          this.tableData = [];
          this.total = 0;
          this.dialogRelated = flag;
        }  else {
          this.$message({
            showClose: true,
            message: res.data.msg,
            type: 'error'
          });
        }
      })
      
      this.$nextTick(function () {
        this.skuNo=goods.skuNo;
        this.settingData=settingData;
      });
    },
    cancelDialog() {
      this.dialogRelated = false;
    },
    //选中checkbox，取消选中 此处返回的是以选中的val
    chooseOne(val) {
      var relatedCode=[];
      for(var i=0;i<val.length;i++){
        relatedCode.push(val[i].skuNo)
      }
      this.selectId = relatedCode;//将选中的id存起来
      
    },
    //全选
    chooseAll(val){
      var relatedCode=[];
      for(var i=0;i<val.length;i++){
        relatedCode.push(val[i].skuNo)
      }
      this.selectId = relatedCode;//将选中的id存起来
    },
    editSure() {
      //console.log(this.skuNo);
      //console.log(this.selectId);
      if(undefined!=this.selectId.length&&0<this.selectId.length){
        let params = {
            skuNo: this.skuNo,
            skuRecommend:this.selectId.join(",")
        };
        this.loading.startLoading();
        this.$axios.post(API.UPDATEGOODSRECOMMEND_API,params).then(res => {
          this.loading.endLoading();
          if (0 == res.data.code) {
            this.$message({
              showClose: true,
              message: '关联成功',
              type: 'success'
            });
            this.relatedGoods();
            this.cancelInnerDialog();
          } else {
            this.$message({
              showClose: true,
              message: res.data.msg,
              type: 'error'
            });
          }
        });
      }else{
        this.$message({
          showClose: true,
          message: "请选择条数据进行商品关联",
          type: 'error'
        });
      }
    },
    //查询已关联商品
    relatedGoods() {
      let params = {
        skuNo: this.skuNo
      };
      this.loading.startLoading();
      this.$axios.post(API.GETGOODSRECOMMEND_API,params).then(res => {
        this.loading.endLoading();
        if (0 == res.data.code) {
          this.settingData=res.data.data;
        } else {
          this.$message({
            showClose: true,
            message: res.data.msg,
            type: 'error'
          });
        }
      }); 
    },
    //选中checkbox，取消选中 此处返回的是以选中的val
    chooseDelOne(val) {
      var skuRecommend=[];
      for(var i=0;i<val.length;i++){
        skuRecommend.push(val[i].skuRecommend)
      }
      this.selectedId = skuRecommend;//将选中的id存起来
      
    },
    //全选
    chooseDelAll(val){
      var skuRecommend=[];
      for(var i=0;i<val.length;i++){
        skuRecommend.push(val[i].skuRecommend)
      }
      this.selectedId = skuRecommend;//将选中的id存起来
    },
    //删除关联的商品
    delRelated() {
      if (0 != this.selectedId.length) {
        this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let params = {
            skuNo: this.selectedId.join(",")
          };
          this.loading.startLoading();
          this.$axios.post(API.DELETEGOODSRECOMMEND_API, params).then(res => {
            this.loading.endLoading();
            if (0 == res.data.code) {
              this.$message({
                type: 'success',
                message: '删除成功!'
              });
              this.selectedId=[];
              this.relatedGoods();
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
          message: "请选择条数据进行删除",
          type: 'error'
        });
      }
    },
  }
}
</script>
