<template>
  <div class="user">
    <div class="searchArea">
      <el-row :gutter="20">
        <el-col :span="6">
          <el-form label-width="80px">
            <el-form-item label="用户账号">
              <el-input v-model="searchList.userAcct"></el-input>
            </el-form-item>
          </el-form>
        </el-col>
        <el-col :span="6">
          <el-form label-width="80px">
            <el-form-item label="用户名称">
              <el-input v-model="searchList.userName"></el-input>
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
      <el-button type="primary" @click="editData">修改</el-button>
      <el-button type="primary" @click="delData">删除</el-button>
    </div>
    <div class="tableArea">
      <el-table :data="tableData" border style="width:100%" @select="selectOne" @select-all="selectOne">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="userAcct" label="用户账号"></el-table-column>
        <el-table-column prop="userName" label="用户姓名"></el-table-column>
        <el-table-column prop="sex" label="性别">
          <template slot-scope="scope">
            <span v-if="scope.row.sex==1">男</span>
            <span v-else>女</span>
          </template>
        </el-table-column>
        <el-table-column prop="phone" label="手机号码"></el-table-column>
        <el-table-column prop="idCard" label="身份证号"></el-table-column>
        <el-table-column prop="email" label="邮箱" show-overflow-tooltip></el-table-column>
        <el-table-column prop="remark" label="备注" show-overflow-tooltip></el-table-column>
        <!-- <el-table-column prop="score" label="积分" show-overflow-tooltip></el-table-column>
        <el-table-column prop="gmtModified" label="修改时间"></el-table-column> -->
      </el-table>
    </div>
    <div class="paginationArea">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[1,10,100,500]"
        :page-size="pageSize"
        layout="total,sizes,prev,pager,next,jumper"
        :total="total">
      </el-pagination>
    </div>
    <!--新增-->
    <el-dialog
      title="新增用户"
      :visible.sync="dialogAddVisible"
      width="40%"
      :close-on-click-modal="false"
      :destroy-on-close=true
      :before-close="handleClose"
      :modal-append-to-body="false"
      >
      <div>
        <el-row>
          <el-form label-width="80px" :model="addDataList" ref="addDataListRest" :rules="rules">
            <el-col :span="12">
              <el-form-item label="用户账号" prop="userAcct">
                <el-input v-model="addDataList.userAcct"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="用户名称" prop="userName">
                <el-input v-model="addDataList.userName"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="性别" prop="sex">
                <el-select v-model="addDataList.sex">
                  <el-option label="男" value="1"></el-option>
                  <el-option label="女" value="0"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="电话" prop="phone">
                <el-input v-model="addDataList.phone"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="邮箱地址" prop="email">
                <el-input v-model="addDataList.email"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="身份证号" prop="idCard">
                <el-input v-model="addDataList.idCard"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="密码" prop="userPwd">
                <el-input v-model="addDataList.userPwd" type="password"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="备注" prop="remark">
                <el-input v-model="addDataList.remark"></el-input>
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
    <!--修改-->
    <el-dialog
      title="修改用户"
      :visible.sync="dialogEditVisible"
      width="40%"
      :close-on-click-modal="false"
      :destroy-on-close=true
      :before-close="handleClose"
      :modal-append-to-body="false"
      >
      <div>
        <el-row>
          <el-form label-width="80px" :model="editDataList" ref="editDataListRest" :rules="rules">
            <el-col :span="12">
              <el-form-item label="用户账号" prop="userAcct">
                <el-input v-model="editDataList.userAcct"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="姓名" prop="userName">
                <el-input v-model="editDataList.userName"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="性别" prop="sex">
                <el-select v-model="editDataList.sex">
                  <el-option label="男" value="1"></el-option>
                  <el-option label="女" value="0"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="电话" prop="phone">
                <el-input v-model="editDataList.phone"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="邮箱地址" prop="email">
                <el-input v-model="editDataList.email"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="身份证号" prop="idCard">
                <el-input v-model="editDataList.idCard"></el-input>
              </el-form-item>
            </el-col>
            <!-- <el-col :span="12">
              <el-form-item label="密码" prop="userPwd">
                <el-input v-model="editDataList.userPwd" type="password"></el-input>
              </el-form-item>
            </el-col> -->
            <el-col :span="12">
              <el-form-item label="备注" prop="remark">
                <el-input v-model="editDataList.remark"></el-input>
              </el-form-item>
            </el-col>
          </el-form>
        </el-row>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancleDialog('editDataListRest')">取消</el-button>
        <el-button type="primary" @click="editDataSure('editDataListRest')">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {Loading} from 'element-ui';
export default {
  data() {
    return {
      ruleForm:{
        pass:'',
        checkPass:'',
        age:''
      },
      searchList:{
        userAcct:"",
        userName:""
      },
      tableData:[],
      total:0,
      pageSize:10,//每页条数
      currentPage:1,//当前页

      dialogAddVisible:false,
      dialogEditVisible:false,
      addDataList:{
        email:'',
        idCard:'',
        phone:'',
        remark:'',
        sex:'0',
        userAcct:'',
        userName:'',
        userPwd:'',
        score:'0',
        userType:'1'//0客户  1用户
      },
      editDataList:{},
      hasChooseList:[],
      rules:{
        userAcct:[
          {required:true,message:'请输入账号',trigger:'blur'},
          {min:1,max:10,message:'长度在 1 到 10 个字符', trigger:'blur'}
        ],
        userName:[
          {required:true,message:'请输入名称',trigger:'blur'},
        ],
        sex:[
          {required:true,message:'性别',trigger:'blur'},
        ],
        phone:[
          {required:true,message:'请输入电话',trigger:'blur'},
        ],
        email:[
          {required:true,message:'请输入邮箱地址',trigger:'blur'},
        ],
        idCard:[
          {required:true,message:'请输入身份证号',trigger:'blur'},
        ],
        userPwd:[
          {required:true,message:'请输入密码',trigger:'blur'},
        ],
      },
      getDataUrl:this.$store.state.url+'customer/listCustomerByPage',
      addDataUrl:this.$store.state.url+'customer/insertCustomer',
      editDataUrl:this.$store.state.url+'customer/updateCustomer',
      delDataUrl:this.$store.state.url+'customer/deleteCustomer',
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
    getData(){
      let list={
        userType:'1',//0客户 1用户
        pageNum:this.currentPage,
        pageSize:this.pageSize,
        userAcct:this.searchList.userAcct,
        userName:this.searchList.userName,
      }
      this.openFullScreen()
      this.$axios.post(this.getDataUrl,list).then(res => {
      this.closeFullScreen()
        if(0 == res.data.code){
          this.tableData=res.data.data.list
          this.total=res.data.data.total
          for(var i=0;i<this.tableData.length;i++){
            this.tableData[i].sex= this.tableData[i].sex+''
          }
          // for(var i=0;i<this.tableData.length;i++){
          //   if(0==this.tableData[i].sex){
          //     this.tableData[i].sex="女"
          //   }else{
          //     this.tableData[i].sex="男"
          //   }
          // }
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
      console.log(formName)
      this.$refs[formName].resetFields();
    },
    //重置查询区域
    resetData(formName){
      this.searchList={};
    },
    //每页条数改变时触发
    handleSizeChange(val){
      console.log('每页 ${val} 条');
      this.pageSize=val
      this.getData()
    },
    //页数改变时触发
    handleCurrentChange(val){
      console.log('当前页：${val}');
      this.currentPage=val
      this.getData()
    },
    //点击新增
    addData(addDataList){
      this.dialogAddVisible=true;
      //this.$refs['addDataListRest'].resetFields();
    },
    //确认新增用户
    addDataSure(formName){
      this.$refs[formName].validate((valid) => {
        if(valid){
          this.dialogAddVisible=false;
          //调接口
          //if接口成功，就清空里面的输入值
          console.log(this.addDataList)
          this.$axios.post(this.addDataUrl,this.addDataList).then(res => {
            if(0 == res.data.code){
              this.$refs["addDataListRest"].resetFields();
              this.dialogAddVisible=false
              this.getData();
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
          console.log(this.editDataList)
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
      if(0<this.hasChooseList.length){
        this.$confirm('此操作将永久删除该文件，是否继续?','提示',{
          confirmButtonText:'确定',
          cancelButtonText:'取消',
          type:'warning'
        }).then(()=>{
          //调接口 把值传给后台
          let list=[]
          for(var i=0;i<this.hasChooseList.length;i++){
            list.push(this.hasChooseList[i].id)
          }
          let arrayList={
            "idList":list
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
        this.$message.error('请选择要删除的数据');
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
</style>
