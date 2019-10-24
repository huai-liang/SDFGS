<template>
  <div class="rotationDialog">
    <el-dialog width="55%" title="新增主页轮播图" :visible.sync="dialogAdd" :close-on-click-modal="false" :before-close="handleClose">
      <el-form
        :model="addInfo"
        label-width="100px"
        :rules="rules"
        ref="addInfo"
        :inline="true"
        label-position="right"
        class="rotationDialogAdd"
      >
        <el-form-item label="轮播图" prop="imageUrl" >
          <el-upload
          class="upload-demo"
          ref="upload"
          :action="uploadUrl"
          :on-remove="handleRemovePic"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
          :on-exceed="handleExceed"
          :limit="1"
          list-type="picture">
          <el-button size="small" type="primary">点击上传</el-button>
        </el-upload>
        </el-form-item>
        <!-- 放示例图 -->
        <el-form-item label="效果图">
          <img src="../../../assets/solo.png" alt="" style="height:150px;" v-show="addInfo.type == 1">
          <img src="../../../assets/more.jpg" alt="" style="height:150px;" v-show="addInfo.type == 2">
          <img src="../../../assets/type.png" alt="" style="height:150px;" v-show="addInfo.type == 3">
        </el-form-item>
        <!-- <el-form-item label="效果图"  v-show="addInfo.type == 2">
          <img src="../../../assets/more.jpg" alt="" style="height:150px;">
        </el-form-item>
        <el-form-item label="效果图"  v-show="addInfo.type == 3">
          <img src="../../../assets/type.png" alt="" style="height:150px;">
        </el-form-item> -->
        <el-form-item label="新增类型" prop="type">
          <el-select v-model="addInfo.type" placeholder="请先选择类型">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="轮播图标题" prop="title">
          <el-input v-model="addInfo.title" clearable></el-input>
        </el-form-item>
        <el-form-item label="排序" prop="sortNo">
          <el-input v-model.number="addInfo.sortNo" clearable></el-input>
        </el-form-item>
        <el-form-item label="有效期起" prop="startTime">
          <el-date-picker
            v-model="addInfo.startTime"
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
            v-model="addInfo.endTime"
            type="date"
            format="yyyy-MM-dd"
            value-format="yyyy-MM-dd"
            placeholder="选择日期"
            @change="endTimeStatus"
            :picker-options="pickerOptionsEnd">
          </el-date-picker>
        </el-form-item>
        <span>（如果有效期止不填，则认为永久有效）</span>
        <el-form-item label="商品" prop="skuName" v-show="addInfo.type == 1">
          <el-input v-model="addInfo.skuName" readonly></el-input>
        </el-form-item>
        <el-button type="primary" plain @click="chooseShopping()" v-show="addInfo.type == 1">选择</el-button>
        <el-form-item label="一级分类" prop="parentCateCode" v-show="addInfo.type == 3">
          <el-select v-model="addInfo.parentCateCode" @change="firstCategoryChange" placeholder="请选择">
            <el-option
            v-for="item in firstOptions"
            :key="item.cateCode"
            :label="item.cateName"
            :value="item.cateCode">
          </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="二级分类" prop="cateCode" v-show="addInfo.type == 3">
          <el-select v-model="addInfo.cateCode" placeholder="请选择">
            <el-option
            v-for="item in secondOptions"
            :key="item.cateCode"
            :label="item.cateName"
            :value="item.cateCode">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div v-show="addInfo.type == 2" style="margin-left:30px;">
        <el-button @click="downloadExel()" type="primary">下载模板</el-button>
        <el-button size="small" >
          <el-upload
          class="upload-demo"
          ref="uploadExcel"
          :action="uploadExcel"
          :on-preview="handlePreview"
          :on-remove="handleRemove"
          :before-remove="beforeRemove"
          :before-upload="beforeAvatarUploadExcel"
          :on-success="handleAvatarSuccessExcel"
          :show-file-list='false'
          :file-list="fileList">点击上传</el-upload>
        </el-button>
        <el-button @click="delteTableData()">删除</el-button>
        <el-table
            :data="uploadData"  
            border
            highlight-current-row
            @current-change="handleCurrentChangeShopping"
            style="margin-top:10px;">
            <el-table-column prop="skuNo" label="商品编号" ></el-table-column>
            <el-table-column prop="skuName" label="商品名称"></el-table-column>
        </el-table>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelDialog()">取 消</el-button>
        <el-button type="primary" @click="addSure('addInfo')">确 定</el-button>
      </div>
    </el-dialog>
    <!-- 单个新增选择商品的弹出框 -->
    <el-dialog width="52%" title="选择商品" :visible.sync="dialogChoose" :close-on-click-modal="false">
      <!-- 搜索区域 -->
      <el-card class="searchModule">
        <el-form :model="queryShopping" label-width="80px" :inline="true" ref="queryFrom">
            <el-form-item label="商品编码" prop="skuNo">
              <el-input v-model="queryShopping.skuNo" clearable></el-input>
            </el-form-item>
            <el-form-item label="商品名称" prop="skuName">
              <el-input v-model="queryShopping.skuName" clearable></el-input>
            </el-form-item>
            <div class="searchBtn">
            <el-button type="primary" @click="searchForm()">查询</el-button>
            <el-button @click="resetForm('queryFrom')">重置</el-button>
            </div>
        </el-form>
      </el-card>
      <!--内容区域-->
    <div class="contentModule">
      <!-- 表格 -->
      <el-table 
        :data="tableDataShopping" 
        border
        stripe 
        highlight-current-row
        @current-change="handleSelectionChange"
        style="width: 100%"
       >
        <el-table-column prop="skuNo" label="商品编号" width='180px'></el-table-column>
        <el-table-column prop="skuName" label="商品名称" width='160px' :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="status" label="商品转态">
          <template slot-scope="scope">
          <span>{{({'0':"下架",'1':"上架"})[scope.row.status]}}</span>
        </template>
        </el-table-column>
        <el-table-column prop="oneLevel" label="一级分类"></el-table-column>
        <el-table-column prop="twoLevel" label="二级分类" ></el-table-column>
    </el-table>
    <div class="pagination">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="page.pageNum"
        :page-sizes="[10, 20, 50, 100]"
        :page-size="page.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="page.total">
      </el-pagination>
    </div>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button @click="cancelDialogShopping()">取 消</el-button>
      <el-button type="primary" @click="addShopping()">确 定</el-button>
    </div>
    </el-dialog>
  </div>
</template>
<script>
import API from '@/api/rotationManage/rotationChart';
import SiruiTable from '@/components/SiruiTable'
//测试环境
import { devUrl } from '@/common/config/dev.env'
//正式环境
import { prodUrl } from '@/common/config/prod.env'

export default {
  components: {
    SiruiTable,
  },
  name: 'rotationAdd',
  data() {
    //表单验证飞控去空格后非空
    var validateAcct = (rule, value, callback) => {
      if (value.replace(/(^\s*)|(\s*$)/g, "") === '') {//前后去空格
        callback(new Error('请输入轮播图标题'));
      } else {
        callback();
      }
    };
    return {
      load: false,
      dialogAdd: false,
      dialogChoose:false,
      imageUrlRotation: '',//轮播图上传的地址
      addInfo: {
        type: '1',
        sortNo: '',
        title: '',
        startTime: '',
        endTime: '',
        skuNo: '',
        skuName:'',
        skuNos: '',
        parentCateCode: '',
        cateCode: '',
        imageUrl:'',//上传之后返回的示例图的地址
        goods:[],
      },
      options:[{
        value: '1',
        label: '单个商品'
      },{
        value: '2',
        label: '多个商品'
      },{
        value: '3',
        label: '单个分类'
      }],
      firstOptions:[],
      secondOptions:[],
      tableDataShopping: [],
      uploadData:[],
      fileList:[],
      page: {
        pages: 0,//总共多少页
        pageNum: 1,//第几页
        pageSize: 10,//一页多少条数据
        total: 0//总共多少条
      },
      queryShopping: {
        skuNo: "",
        skuName: "",
        status:'1'
      },
      multipleSelection:{},//存放选中的值
      //开始时间小于结束时间
      pickerOptionsStart: {
        disabledDate: time => {
          let endDateVal = this.addInfo.endTime;
          if (endDateVal) {
            return time.getTime() > new Date(endDateVal).getTime();
          }
        }
      },
      //结束时间大于开始时间
      pickerOptionsEnd: {
        disabledDate: time => {
          let beginDateVal = this.addInfo.startTime;
          if (beginDateVal) {
            return (
              time.getTime() <
              new Date(beginDateVal).getTime()
            );
          }
        },
      },
      currentRow: {},
      //下载地址
      downDataUrl:process.env.NODE_ENV === 'production'?prodUrl:devUrl,
      //图片上传接口
      uploadUrl:(process.env.NODE_ENV === 'production'?prodUrl:devUrl) + API.UPLOADPIC_ROTATION_API +'?access_token=' + this.$store.state.userInfo.userInfo.access_token,
      //文件导入
      uploadExcel:(process.env.NODE_ENV === 'production'?prodUrl:devUrl) + API.UPLOAD_ROTATION_API +'?access_token=' + this.$store.state.userInfo.userInfo.access_token,
      rules: {
        title: [
          { required: true, validator: validateAcct, trigger: 'blur' }
        ],
        sortNo: [
          { required: true, message: '排序号不能为空'},
          { type: 'number', message: '排序号必须为数字值'}
        ],
        startTime: [
          { required: true, message: '请选择有效期起', trigger: 'blur' }
        ],
        imageUrl: [
          { required: true, message: '请上传轮播图片', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    // 时间开始选择器
    startTimeStatus:function(value){
      this.addInfo.startTime = value
    },
    // 时间结束选择器
    endTimeStatus:function(value){
      this.addInfo.endTime = value
    },
    openDialog(flag) {
      this.dialogAdd = flag;
      this.uploadData = [];
      this.$nextTick(() => {
        this.getFirstCategory();
        this.$refs.addInfo.resetFields();
      });
    },
    cancelDialog() {
      this.dialogAdd = false;
      this.$refs.upload.clearFiles(); 
    },
    //新增弹窗的关闭按钮，清除导入的图片的list
    handleClose(){
      this.dialogAdd = false;
      this.$refs.upload.clearFiles(); 
    },
    chooseShopping(){
      this.dialogChoose = true;
      this.getData();
    },
    cancelDialogShopping(){
      this.dialogChoose = false;
    },
    //搜索
    searchForm() {
      this.page.pageNum = 1;
      this.getData()
    },
    //获取一级分类
    getFirstCategory(){
      this.$axios.post(API.FIRST_GOODS_API).then(res => {
        if (0 == res.data.code) {
          this.firstOptions = res.data.data;
        } else {
          this.$message({
            showClose: true,
            message: res.data.msg,
            type: 'error'
          });
        }
      })
    },
    //获取二级分类
    firstCategoryChange(){
      this.addInfo.cateCode = '';
      let params = {
        cateCode: this.addInfo.parentCateCode,
      };
      this.$axios.post(API.SECOND_GOODS_API, params).then(res => {
        if (0 == res.data.code) {
          this.secondOptions = res.data.data;
        } else {
          this.$message({
            showClose: true,
            message: res.data.msg,
            type: 'error'
          });
        }
      })
    },
    //获取数据
    getData() {
      this.queryShopping.pageSize = this.page.pageSize;
      this.queryShopping.pageNum = this.page.pageNum;
      this.load = true;
      this.$axios.post(API.GETPAGETGOODSSKU_LIST_API, this.queryShopping).then(res => {
        this.load = false;
        if (0 == res.data.code) {
          this.tableDataShopping = res.data.data.list;
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
    handleSelectionChange(val) {
      this.multipleSelection = val;
      // this.addInfo.skuNo = val.skuNo;
      // this.addInfo.skuName = val.skuName;
    },
    //新增商品
    addShopping(){
      this.addInfo.skuNo = this.multipleSelection.skuNo;
      this.addInfo.skuName = this.multipleSelection.skuName;
      this.dialogChoose = false;
    },
    //新增轮播图
    addSure() {
      this.$refs['addInfo'].validate((valid) => {
        if (valid) {
          if(this.addInfo.type == '1'){
            if(this.addInfo.skuNo == ''){
              this.$message({
              showClose: true,
              message:'新增单个商品必须选择一个商品',
              type: 'error'
              });
              return
            }
          }else if(this.addInfo.type == '2'){
            if(this.uploadData.length == 0){
              this.$message({
              showClose: true,
              message:'新增多个商品必须导入数据',
              type: 'error'
              });
              return
            }else{
              // console.log(this.uploadData);
              // for(var i = 0; i<this.uploadData.length;i++){
              //   var good = {};
              //   good.skuNo =this.uploadData[i].skuNo;
              //   good.skuName =this.uploadData[i].skuName
              //   this.addInfo.goods.push(good);
              // }
              this.addInfo.goods = JSON.stringify(this.uploadData);
            }
          }else{
            if(this.addInfo.cateCode == ''){
              this.$message({
              showClose: true,
              message:'新增单个分类，分类必须都要选择',
              type: 'error'
              });
              return
            }
          }
          this.loading.startLoading();
          this.$axios.post(API.ADD_ROTATION_API, this.addInfo).then(res => {
            this.loading.endLoading();
            if (0 == res.data.code) {
              this.$message({
                showClose: true,
                message: '新增成功',
                type: 'success'
              });
              this.$emit('search');
              this.cancelDialog();
            } else {
              this.loading.endLoading();
              this.$message({
                showClose: true,
                message: res.data.msg,
                type: 'error'
              });
            }
          });
        }
      });
    },
    //表格选中事件
    handleCurrentChangeShopping(val) {
      this.currentRow = val;
      // splice(index,1);
    },
    //列表的数据删除
    delteTableData(){
      for(var i = 0; i<this.uploadData.length;i++){
        if(this.currentRow.skuNo == this.uploadData[i].skuNo){
          this.uploadData.splice(i,1);
        }
      }
    },
    //图片上传
    handleAvatarSuccess(res, file) {
      this.imageUrlRotation = URL.createObjectURL(file.raw);
      if('0' == res.code){
        this.addInfo.imageUrl = res.data;
      }else{
        this.$message({
          showClose: true,
          message: res.msg,
          type: 'error'
        });
      }
    },
    //上传之前的图片格式检验
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isLt1M = file.size / 1024 / 1024 < 1;
      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!');
      }
      if (!isLt1M) {
        this.$message.error('上传头像图片大小不能超过 1MB!');
      }
      return isJPG && isLt1M;
    },
    //图片限制只能上传1个
    handleExceed(files, fileList) {
      this.$message.warning(`只能上传1张轮播图`);
    },
    //图片的删除
    handleRemovePic(file, fileList) {
      if(file.response == undefined){     //防止上传过大，删除报错
        return
      }
      let params = {
        filePath: file.response.data
      };
      this.$axios.post(API.DELTEPIC_ROTATION_API, params).then(res => {
        if (0 == res.data.code) {
          this.addInfo.imageUrl = '';     //清空校验
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
    },
    //下载模板
    downloadExel(){
      window.location.href = 'http://10.100.12.20:8001/group1/M00/00/00/CmQMFFzjqeCANH9VAAAivI9-Boo98.xlsx?filename=轮播图商品导入模板.xlsx'
      // window.location.href=encodeURI(this.downDataUrl+API.DOWNLOAD_GOODS_API+'?access_token=' + this.$store.state.userInfo.userInfo.access_token);
    },
    //数据导入
    handleRemove(file, fileList) {
        
    },
    handlePreview(file) {

    },
    beforeRemove(file, fileList) {
      
    },
    //上传之前，判断类型
    beforeAvatarUploadExcel(file) {
      const fileType = file.type;
      const isType = ( fileType === 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' || fileType === 'application/vnd.ms-excel');
      if(!isType){
        this.$message.error('导入文件应为excel，请重新上传！');
      }
    return isType;
    },
    //导入成功
    handleAvatarSuccessExcel(res, file) {
        // this.loading.endLoading();
        this.$refs.uploadExcel.clearFiles();      //导入之后移除文件
        if (0 == res.code) {
          if(res.data.returnCode == '00'){
            if(undefined == res.data.filepath){
              this.$message({
              showClose: true,
              message: '导入成功',
              type: 'success'
              });
            }else{
              this.$message({
              showClose: true,
              message:res.data.returnMsg+'，其他数据异常',
              type: 'error'
              });
              //下载异常数据
              window.location.href=encodeURI(this.downDataUrl+ API.DOWNLOADEXCEL_GOODS_API+'?filepath='+res.data.filepath+ '&access_token=' + this.$store.state.userInfo.userInfo.access_token);
            }
            this.uploadData = res.data.bannerGoods;
          }else{
            this.$message({
              showClose: true,
              message:res.data.returnMsg,
              type: 'error'
              });
          }
        } else {
          this.$message({
          showClose: true,
          message: res.msg,
          type: 'error'
          });
        }
    },
  }
}
</script>
<style lang="scss" scoped>
.rotationDialog{
  .rotationDialogAdd{
    .upload-demo{
      width: 215px;
    }
  }
  .pagination{
    margin: 0;
  }
  .pagination .el-pagination{
    margin-bottom: 0;
  }
}
</style>