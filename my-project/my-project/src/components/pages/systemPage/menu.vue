<template>
  <div class="menu">
    <div class="menuContent">
      <div class="treeList">
        <div class="buttonArea">
          <el-button type="primary" @click="addMenu">新增</el-button>
          <el-button type="primary" @click="editMenu">修改</el-button>
          <el-button type="primary" @click="delMenu">删除</el-button>
          <el-button type="primary" @click="getData">刷新</el-button>
        </div>
        <div>
          <el-tree :data="data" :props="defaultProps" highlight-current @node-click="handleNodeClick"></el-tree>
        </div> 
      </div>
      <div class="detailList">
        <div class="detailTitle">
          菜单详情
        </div>
        <div class="detailBody">
          <el-form :model="menuDetail" inline>
            <el-form-item label="菜单名称" label-width="80px">
              <el-input v-model="menuDetail.name" readonly></el-input>
            </el-form-item>
            <el-form-item label="菜单路径" label-width="80px" >
              <el-input v-model="menuDetail.url" readonly></el-input>
            </el-form-item>
            <el-form-item label="分类" label-width="80px">
              <el-select v-model="menuDetail.type" disabled>
                <el-option label="目录" value=2></el-option>
                <el-option label="菜单" value=1></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="排序" label-width="80px" >
              <el-input v-model="menuDetail.index" readonly></el-input>
            </el-form-item>
            <el-form-item label="备注" label-width="80px" >
              <el-input v-model="menuDetail.remark" readonly></el-input>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>
    <el-dialog title="新增菜单" 
      :visible.sync="dialogAddVisible" 
      width="30%" 
      :close-on-click-modal="false"
      :before-close="cancleDialog" 
      class="addModel" 
      :modal-append-to-body="false">
      <el-form :model="addData" ref="addDataRef" :rules="rules">
        <el-form-item label="菜单名称" label-width="80px" prop="menuName">
          <el-input v-model="addData.menuName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="分类" label-width="80px" prop="menuType">
          <el-select v-model="addData.menuType">
            <el-option label="目录" value="2"></el-option>
            <el-option label="菜单" value="1"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="菜单路径" label-width="80px" prop="menuUrl">
          <el-input v-model="addData.menuUrl" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="排序" label-width="80px" prop="index">
          <el-input v-model="addData.index" type="number" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="备注" label-width="80px" prop="remark">
          <el-input v-model="addData.remark" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancleDialog">取消</el-button>
        <el-button type="primary" @click="sureAddData">确定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="修改菜单" 
      :visible.sync="dialogEditVisible" 
      width="30%" 
      :close-on-click-modal="false"
      :before-close="cancleDialog" 
      class="addModel" 
      :modal-append-to-body="false">
      <el-form :model="editData" ref="editDataRef" :rules="rules">
        <el-form-item label="菜单名称" label-width="80px" prop="menuName">
          <el-input v-model="editData.menuName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="菜单路径" label-width="80px" prop="menuUrl">
          <el-input v-model="editData.menuUrl" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="排序" label-width="80px" prop="index">
          <el-input v-model="editData.index" type="number" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="菜单备注" label-width="80px" prop="remark">
          <el-input v-model="editData.remark" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancleDialog">取 消</el-button>
        <el-button type="primary" @click="sureEidtData">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      data: [],
      defaultProps:{
        children:'children',
        label:'name'
      },
      dialogAddVisible:false,
      dialogEditVisible:false,
      addData:{
        menuName:'',
        menuIcon:'',
        index:'',
        menuType:'1',//默认新增目录
        remark:'',
        parentId:'',//父级菜单id
        menuLevel:0,//菜单等级
      },
      editData:{},
      menuDetail:{},
      rules:{
        menuName:[
          { required: true, message: '请输入菜单名称', trigger: 'blur'},
        ],
        menuUrl:[
          { required: true, message: '请输入菜单路径 ', trigger: 'blur'},
        ],
        menuType:[
          {required: true,},
        ],
      },
      selectOne:{},//当前选中的菜单
      getMenuUrl:this.$store.state.url+'menu/listMenuTree',
      addMenuUrl:this.$store.state.url+'menu/saveMenu',
      delMenuUrl:this.$store.state.url+'menu/deleteMenu',
      editMenuUrl:this.$store.state.url+'menu/updateMenu',
    }
  },
  mounted(){
    this.getData()
  },
  methods:{
    getData(){
      //调接口获取页面数据
      this.$axios.post(this.getMenuUrl).then(res => {
        if (0 == res.data.code) {
          this.data=res.data.data
        } else {
          this.$message({
          showClose: true,
          message: res.data.msg,
          type: "error"
         });
        }
      });
      this.selectOne={}
      this.menuDetail={}
    },
    handleNodeClick(val){
      this.selectOne=val
      this.menuDetail=val
      this.menuDetail.type=this.menuDetail.type+""
      this.menuDetail.remark=this.menuDetail.menu.remark
      console.log(this.menuDetail)
    },
    //新增
    addMenu(){
      if(this.selectOne.flag){
        if(0==this.selectOne.flag){
          this.dialogAddVisible=true
        }else{
          this.$message.error('菜单下不能新建菜单');
        }
      }else{
        this.dialogAddVisible=true
      }
    },
    //取消新增
    cancleDialog(){
      if(this.$refs["addDataRef"]){
        this.$refs['addDataRef'].resetFields();
      }
      if(this.$refs["editDataRef"]){
        this.$refs['editDataRef'].resetFields();
      }
      this.dialogAddVisible=false
      this.dialogEditVisible=false
    },
    //确定新增
    sureAddData(){
      this.$refs['addDataRef'].validate((valid)=>{
        if(valid){
          if(this.selectOne.id){
            this.addData.parentId=this.selectOne.id
            this.addData.menuLevel=1
          }else{
            this.addData.parentId=0
            this.addData.menuLevel=0
          }
          this.$axios.post(this.addMenuUrl,this.addData).then(res => {
            if(0 == res.data.code){
              this.$refs["addDataRef"].resetFields();
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
          console.log('error submit!!');
          return false;
        }
      });
    },
    //修改
    editMenu(){
      if(this.selectOne.id){
        this.editData=JSON.parse(JSON.stringify(this.selectOne))
        this.dialogEditVisible=true
        this.editData=this.editData.menu
        // this.editData.menuType=this.editData.menuType+''
        console.log(this.editData)
      }else{
        this.$message.error('请选择要修改的数量');
      }
    },
    //确认修改
    sureEidtData(){
      this.$refs['editDataRef'].validate((valid)=>{
        if(valid){
          console.log(this.editData)
          this.$axios.post(this.editMenuUrl,this.editData).then(res => {
            console.log(res)
            if(0 == res.data.code){
              this.$refs["editDataRef"].resetFields();
              this.dialogEditVisible=false
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
          console.log('error submit!!');
          return false;
        }
      });
    },
    //删除
    delMenu(){
      //删除提示
      if(this.selectOne.id){
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          //调接口 把值传给后台
          //if删除成功
          let list={
            id:this.selectOne.id
          }
          this.$axios.post(this.delMenuUrl,list).then(res => {
            console.log(res)
            if(0 == res.data.code){
              this.getData();
              this.$message({
                type: 'success',
                message: '删除成功!'
              });
            }else{
              this.$message({
                showClose:true,
                message: res.data.msg,
                type:"error"
              });
            }
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });          
        });
      }else{
        this.$message.error('请选择要删除的数据')
      }
    },
  }
}
</script>

<style scoped>
.menu{
  height:calc(100% - 20px);
}
.menuContent{
  overflow: hidden;
  height: 100%;
  padding: 10px;
}
.buttonArea{
  height: 40px;
  line-height: 40px;
  border-bottom: 1px solid #c9bebe;
}
.buttonArea div{
  padding: 0 10px;
}
.treeList{
  float: left;
  width: 30%;
  height: 100%;
  overflow: auto;
  border: 1px solid #c9bebe;
}
.detailList{
  float: left;
  width: calc(70% - 20px);
  height: 100%;
  border: 1px solid #c9bebe;
  padding-left: 0;
}
.detailTitle{
  height: 40px;
  line-height: 40px;
  border-bottom: 1px solid #c9bebe;
  padding-left: 100px;
}
.detailBody{
  padding:10px;
}
</style>
<style>
.menu .el-button{
  padding: 5px 10px !important;
}
.detailBody .el-form-item{
  width: 310px;
}
.detailBody .el-form-item_content{
  width: 200px;
}
.menu .el-input.is-disabled .el-input_inner{
  background: #fff;
  color: #606266;
  border:1px solid #DCDFE6;
}
.addModel .el-select{
  width: 100%;
}
</style>