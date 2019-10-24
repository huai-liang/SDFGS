<template>
  <div class="banner">
    <div class="searchArea">
      <el-row :gutter="20">
        <el-col :span="6">
          <el-form label-width="80px">
            <el-form-item label="是否启用">
              <el-select v-model="searchList.status">
                <el-option label="启用" value="0"></el-option>
                <el-option label="禁用" value="1"></el-option>
              </el-select>
            </el-form-item>
          </el-form>
        </el-col>
        <el-col :span="6">
          <el-button type="primary" @click="getData">查询</el-button>
          <el-button @click="resetData">重置</el-button>
        </el-col>
      </el-row>
    </div>
    <div class="buttonArea">
      <el-button type="primary" @click="addData">新增</el-button>
      <el-button type="primary" @click="delData">删除</el-button>
      <el-button type="primary" @click="openOrClose(0)">启用</el-button>
      <el-button type="primary" @click="openOrClose(1)">禁用</el-button>
    </div>
    <div class="tableArea">
      <el-table :data="tableData" border style="width:100%" @select="selectOne" @select-all="selectOne">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="url" label="轮播图地址"></el-table-column>
        <el-table-column prop="url" label="轮播图预览">
          <template slot-scope="scope">
            <img :src="scope.row.url" style="width:100px"/>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="是否启用" width="100px">
          <template slot-scope="scope">
            <span v-if="scope.row.status==0">启用</span>
            <span v-else>禁用</span>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="paginationArea">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[1,10,50,100,500]"
        :page-size="pageSize"
        layout="total,sizes,prev,pager,next,jumper"
        :total="total">
      </el-pagination>
    </div>
    <!--新增-->
    <el-dialog
      title="新增轮播图"
      :visible.sync="dialogAddVisible"
      width="40%"
      :close-on-click-modal="false"
      :destroy-on-close=true
      :before-close="handleClose"
      :modal-append-to-body="false"
      >
      <div>
        <el-row>
          <el-form label-width="100px" :model="addDataList" ref="addDataListRest" :rules="rules">
            <el-col :span="20">
              <el-form-item label="轮播图地址" prop="url">
                <el-input v-model="addDataList.url" readonly=""></el-input>
              </el-form-item>
              <div class="upLoadImg">
                <el-upload
                  :action="upLoadIMgUrl"
                  :show-file-list="false"
                  :on-success="upLoadSuccess"
                  :before-upload="beforeAvatarUpload">
                  <el-button size="small" type="primary">上传</el-button>
                </el-upload>
              </div>
            </el-col>
            <el-col :span="20">
              <el-form-item label="是否开启" prop="status">
                <el-select v-model="addDataList.status" disabled>
                  <el-option label="开启" value="0"></el-option>
                  <el-option label="禁用" value="1"></el-option>
                </el-select>
            </el-form-item>
            </el-col>
          </el-form>
        </el-row>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancleDialog('addDataListRest')">取消</el-button>
        <el-button type="primary" @click="addDataSure('addDataListRest')">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {Loading, Upload} from 'element-ui';
export default {
  data() {
    return {
      searchList:{
        status:"",
      },
      tableData:[],
      total:0,
      pageSize:10,//每页条数
      currentPage:1,//当前页
      dialogAddVisible: false,
      addDataList:{
        filePath:'',
        url:'',
        status:'1',
        createBy:sessionStorage.getItem("userId")
      },
      hasChooseList:[],
      rules:{
        url:[
          {required:true,message:'请上传图片',trigger:'blur'},
        ],
        status:[
          {required:true,message:'请选择是否开启',trigger:'blur'},
        ],

      },
      upLoadIMgUrl:this.$store.state.url+'file/uploadFile',
      getDataUrl:this.$store.state.url+'shuffling/listShufflingByPage',
      addDataUrl:this.$store.state.url+'shuffling/saveShuffling',
      delDataUrl:this.$store.state.url+'shuffling/deleteShufflingById',
      openOrcloseUrl:this.$store.state.url+'shuffling/updShuffling',
    };
  },
  mounted(){
    this.getData()
  },
  methods:{
    openFullScreen(){
      const loading = this.$loading({
        lock:true,
        text:'Loading',
        spinner:'el-icon-loading',
        background:'rgba(0,0,0,0.7)'
      });
    },
    closeFullScreen(){
      const loading = this.$loading({
        lock:true,
        text:'Loading',
        spinner:'el-icon-loading',
        background:'rgba(0,0,0,0.7)'
      });
      loading.close();
    },
    //获取表格数据
    getData(){
      let list={
        status:this.searchList.status,
        pageNum:this.currentPage,
        pageSize:this.pageSize,
      }
      this.openFullScreen()
      this.$axios.post(this.getDataUrl,list).then(res => {
      this.closeFullScreen()
        if(0 == res.data.code){
          this.tableData=res.data.data.list
          this.total=res.data.data.total
          //查询成功
        }else{
          this.$message({
            showClose:true,
            message: res.data.msg,
            type:"error"
          });
        }
      });
    },
    resetForm(formName){
      this.$refs[formName].resetFields();
    },
    //重置查询区域
    resetData(formName){
      this.searchList={};
    },
    //每页条数改变时触发
    handleSizeChange(val){
      this.pageSize=val
      this.getData()
    },
    //页数改变时触发
    handleCurrentChange(val){
      this.currentPage=val
      this.getData()
    },
    //点击新增
    addData(addDataList){
      this.dialogAddVisible=true;
      //this.$refs['addDataListRest'].resetFields();
    },
    //确认新增
    addDataSure(formName){
      this.openFullScreen()
      this.$refs[formName].validate((valid) => {
      this.closeFullScreen()
        if(valid){
          //调接口
          //if接口成功，就清空里面的输入值
          this.$axios.post(this.addDataUrl,this.addDataList).then(res => {
            if(0 == res.data.code){
              this.getData();
              this.dialogAddVisible=false
              this.$refs["addDataListRest"].resetFields();
            }else{
              this.$message({
                showClose: true,
                message: res.data.msg,
                type:"error"
              });
            }
          });
        }else{
          return false;
        }
      });
    },
    //取消新增用户
    cancleDialog(val){
      this.$refs[val].resetFields();
      this.dialogAddVisible=false;
      this.dialogEditVisible=false;
    },
    //点击X取消新增弹框
    handleClose(){
      if(this.$refs['addDataListRest']){
        this.$refs['addDataListRest'].resetFields();
      }
      if(this.$refs['editDataListRest']){
        this.$refs['editDataListRest'].resetFields();
      }
      this.dialogAddVisible=false;
      this.dialogEditVisible=false;
    },
    //点击一行选中checkbox时触发
    selectOne(selection){
      this.hasChooseList=selection
    },
    //点击全选
    selectAll(selection){
      this.hasChooseList=selection
    },
    //点击修改按钮
    editData(){
      if(1!=this.hasChooseList.length){
        this.$message.error('请选择一条数据进行修改');
      }else{
        //开始打开修改框
        this.dialogEditVisible=true;
        this.editDataList=JSON.parse(JSON.stringify(this.hasChooseList[0]))
      }
    },
    //确认修改
    editDataSure(formName){
      //调接口
      //if 接口返回成功，就关闭输入框
      this.openFullScreen()
      this.$refs[formName].validate((valid) => {
      this.closeFullScreen()
        if(valid){
            this.$axios.post(this.editDataUrl,this.editDataList).then(res => {
              if(0 == res.data.code){
                this.getData();
                this.dialogEditVisible=false;
                this.$refs['editDataListRest'].resetFields();
                //查询成功
              }else{
                this.$message({
                  showClose: true,
                  message: res.data.msg,
                  type:"error"
                });
              }
            });
        }else{
          return false;
        }
      });
    },
    //删除提示
    delData(){
      if(1==this.hasChooseList.length){
        this.$confirm('此操作将永久删除该文件，是否继续?','提示',{
          confirmButtonText:'确定',
          cancelButtonText:'取消',
          type:'warning'
        }).then(()=>{
          //调接口 把值传给后台
          let arrayList={
            "id":this.hasChooseList[0].id
          }
          this.$axios.post(this.delDataUrl,arrayList).then(res => {
            if(0 == res.data.code){
              this.$message({
                type:'success',
                message:'删除成功！'
              });
              this.getData();
            }else{
              this.$message({
                showClose: true,
                message: res.data.msg,
                type:"error"
              });
            }
          });
        }).catch(()=>{
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      }else{
        this.$message.error('请选择一条数据进行删除');
      }
    },
    //上传校验
    beforeAvatarUpload(file){
      const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size /1024/1024<2;
      if(!isJPG){
        this.$message.error('上传图片只能是 JPG 格式！');
      }
      if(!isLt2M){
        this.$message.error('上传图片大小不能超过 2MB！')
      }
      return isJPG && isLt2M;
    },
    //上传成功
    upLoadSuccess(response){
      this.addDataList.url=response.data.fileUrl
      this.addDataList.filePath=response.data.filePath
    },
    //启用or禁用
    openOrClose(val){
      var text;
      if(0==val){//启用
        text="启用";
      }else{//禁用
        text="禁用";
      }
      if(1==this.hasChooseList.length){
        this.$confirm('此操作将'+text+'该文件，是否继续?','提示',{
          confirmButtonText:'确定',
          cancelButtonText:'取消',
          type:'warning'
        }).then(()=>{
          //调接口 把值传给后台
          let arrayList={
            "id":this.hasChooseList[0].id,
            "status":val+""
          }
          this.$axios.post(this.openOrcloseUrl,arrayList).then(res => {
            if(0 == res.data.code){
              this.$message({
                type:'success',
                message: text+'成功！'
              });
              this.getData();
            }else{
              this.$message({
                showClose: true,
                message: res.data.msg,
                type:"error"
              });
            }
          });
        }).catch(()=>{
          this.$message({
            type: 'info',
            message: '已取消'+text
          });
        });
      }else{
        this.$message.error('请选择一条数据进行删除'+text);
      }
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.searchArea{
  height: 50px;
  padding: 20px;
}
.buttonArea{
  padding-left: 20px;
}
.tableArea{
  padding: 20px;
}
.paginationArea{
  height: 40px;
  float: right;
  padding-right: 20px;
}
.upLoadImg{
  position: absolute;
  top: 0;
  right: 0;
}
</style>

