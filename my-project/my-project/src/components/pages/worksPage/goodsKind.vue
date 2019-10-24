<template>
  <div class="goodsKing">
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
          商品分类详情
        </div>
        <div class="detailBody">
          <el-form :model="menuDetail" inline>
            <el-form-item label="分类名称" label-width="80px">
              <el-input v-model="menuDetail.name" readonly></el-input>
            </el-form-item>
            <el-form-item label="层级" label-width="80px">
              <el-select v-model="menuDetail.level" disabled>
                <el-option label="一级目录" value="1"></el-option>
                <el-option label="二级目录" value="2"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="备注" label-width="80px" >
              <el-input v-model="menuDetail.remark" readonly></el-input>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>
    <el-dialog title="新增分类" 
      :visible.sync="dialogAddVisible" 
      :close-on-click-modal="false"
      width="30%" 
      :before-close="cancleDialog" 
      class="addModel" 
      :modal-append-to-body="false">
      <el-form :model="addData" ref="addDataRef" :rules="rules">
        <el-form-item label="分类名称" label-width="80px" prop="cateName">
          <el-input v-model="addData.cateName" autocomplete="off"></el-input>
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
    <el-dialog title="修改分类" 
      :visible.sync="dialogEditVisible" 
      :close-on-click-modal="false"
      width="30%" 
      :before-close="cancleDialog" 
      class="addModel" 
      :modal-append-to-body="false">
      <el-form :model="editData" ref="editDataRef" :rules="rules">
        <el-form-item label="分类名称" label-width="80px" prop="name">
          <el-input v-model="editData.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="备注" label-width="80px" prop="remark">
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
        cateLevel:'',
        cateName:'',
        createBy:'',
        parentCateId:'',
        remark:'',
      },
      editData:{
        cateName:''
      },
      menuDetail:{},
      rules:{
        name:[
          { required: true, message: '请输入分类名称', trigger: 'blur'},
        ],
        cateName:[
          { required: true, message: '请输入分类名称 ', trigger: 'blur'},
        ],
      },
      selectOne:{},//当前选中的菜单
      getGoodsKindUrl:this.$store.state.url+'cate/listCateTree',
      addGoodsKindUrl:this.$store.state.url+'cate/saveCate',
      delGoodsKindUrl:this.$store.state.url+'cate/deleteCate',
      editGoodsKindUrl:this.$store.state.url+'cate/updateCate',
    }
  },
  mounted(){
    this.getData()
  },
  methods:{
    getData(){
      //调接口获取页面数据
      this.$axios.post(this.getGoodsKindUrl).then(res => {
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
      this.menuDetail.level=this.menuDetail.level+""
      this.menuDetail.remark=this.menuDetail.remark
    },
    //新增
    addMenu(){
      if(this.selectOne.level){
        if(1==this.selectOne.level){
          this.dialogAddVisible=true
        }else{
          this.$message.error('二级目录下不能新建分类');
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
            this.addData.parentCateId=this.selectOne.id
            this.addData.cateLevel=2
          }else{
            this.addData.parentCateId=0
            this.addData.cateLevel=1
          }
          this.addData.createBy=sessionStorage.getItem('userId')
          this.$axios.post(this.addGoodsKindUrl,this.addData).then(res => {
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
      }else{
        this.$message.error('请选择要修改的数据');
      }
    },
    //确认修改
    sureEidtData(){
      this.$refs['editDataRef'].validate((valid)=>{
        if(valid){
          this.editData.cateName=this.editData.name
          console.log(this.editData)
          this.$axios.post(this.editGoodsKindUrl,this.editData).then(res => {
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
          this.$axios.post(this.delGoodsKindUrl,list).then(res => {
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
.goodsKing{
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