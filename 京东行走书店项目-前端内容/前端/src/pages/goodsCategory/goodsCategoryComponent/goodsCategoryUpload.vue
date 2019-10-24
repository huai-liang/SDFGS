<template>
  <div class="uploadDialog">
    <el-dialog width="32%" title="导入商品分类" :visible.sync="dialogUpload" :close-on-click-modal="false">
     <el-upload
        class="upload-demo"
        ref="upload"
        :action='uploadUrl'
        :on-preview="handlePreview"
        :on-remove="handleRemove"
        :before-remove="beforeRemove"
        :before-upload="beforeAvatarUpload"
        :on-success="handleAvatarSuccess"
        :file-list="fileList">
        <el-button size="small" type="primary">点击导入</el-button>
        </el-upload>
    </el-dialog>
  </div>
</template>
<script>
import API from '@/api/goodsCategory/goodsCategory';
//测试环境
import { devUrl } from '@/common/config/dev.env'
//正式环境
import { prodUrl } from '@/common/config/prod.env'

export default {
  name: 'goodsCategoryUpload',
  data() {
    return {
      dialogUpload: false,
      fileList:[],
      downDataUrl:process.env.NODE_ENV === 'production'?prodUrl:devUrl,//下载地址
      //上传的接口
      uploadUrl:(process.env.NODE_ENV === 'production'?prodUrl:devUrl) + API.UPLOAD_GOODS_API + '?access_token=' + this.$store.state.userInfo.userInfo.access_token
    }
  },
  methods: {
    openDialog(flag) {
      this.dialogUpload = flag;
    },
    cancelDialog() {
      this.dialogUpload = false;
    },
    handleRemove(file, fileList) {
        
    },
    handlePreview(file) {
      this.loading.startLoading();
    },
    beforeRemove(file, fileList) {
    
    },
    //上传之前，判断类型
    beforeAvatarUpload(file) {
      const fileType = file.type;
      const isType = ( fileType === 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' || fileType === 'application/vnd.ms-excel');
      if(!isType){
        this.$message.error('导入文件应为excel，请重新上传！');
      }
    return isType;
    },
    //导入成功
    handleAvatarSuccess(res, file) {
        this.loading.endLoading();
        this.dialogUpload = false;
        this.$refs.upload.clearFiles();      //导入之后移除文件
        if (0 == res.code) {
            if(res.data.returnCode == '00'){
              if('' == res.data.filepath){
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
            }else{
              this.$message({
                showClose: true,
                message:res.data.returnMsg,
                type: 'error'
              });
            }
            this.$emit('getData');
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
.uploadDialog{
    .upload-demo{
        text-align: center;
    }
}
</style>