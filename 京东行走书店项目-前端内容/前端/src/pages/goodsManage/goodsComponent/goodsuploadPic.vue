<template>
  <div class="userDialog">
    <el-dialog width="52%" title="上传图片" :visible.sync="dialogUploadPic" :close-on-click-modal="false">
       <el-upload
        class="upload-demo"
        ref="upload"
        :action="uploadUrl"
        :on-preview="handlePreview"
        :on-remove="handleRemove"
        :on-success="handleAvatarSuccess"
        multiple
        :limit="6"
        :before-upload="beforeAvatarUpload"
        :on-exceed="handleExceed"
        :file-list="fileList"
        :auto-upload="false">
        <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitUpload()">确 定</el-button>
        <el-button @click="cancelDialog()">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import API from '@/api/goodsManage/goodsManage';
//测试环境
import { devUrl } from '@/common/config/dev.env'
//正式环境
import { prodUrl } from '@/common/config/prod.env'
export default {
  name: 'goodsUploadPic',
  data() {
    return {
      dialogUploadPic: false,
      fileList:[],//上传文件
      uploadUrl: (process.env.NODE_ENV === 'production'?prodUrl:devUrl)+API.BATCHUPLOAD_API+'?access_token=' + this.$store.state.userInfo.userInfo.access_token,
    }
  },
  methods: {
    openDialog(flag) {
      this.dialogUploadPic = flag;
    },
    cancelDialog() {
      this.dialogUploadPic = false;
      this.fileList=[];
    },
    //上传开始
    submitUpload() {
      this.$refs.upload.submit();
      //this.loading.startLoading();//打开遮罩
    },
    handleRemove(file, fileList) {
      //console.log(file, fileList);
    },
    handlePreview(file) {
      //console.log(file);
    },
    beforeAvatarUpload(file) {

      const isJPG = (file.type === 'image/jpeg')||(file.type === 'image/png')||(file.type === 'image/jpg')||(file.type === 'image/gif')||(file.type === 'image/tiff');
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        //this.loading.endLoading();
        this.$message.error('上传图片只能是 JPG、JPEG、GIF、PNG、TIFF格式!');
      }
      if (!isLt2M) {
        //this.loading.endLoading();
        this.$message.error('上传图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    },
    handleAvatarSuccess(res) {
      //this.loading.endLoading();
      //console.log(res);
      if(res.code=='0'){
        this.fileList=[];
        this.$emit('getData');
        this.cancelDialog();
      }else{
        this.$message({
          showClose: true,
          message: res.msg,
          type: 'error'
        });
      }
    },
    //上传限制提示
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制只能上传 6 个图片`);
    },
    addSure() {
      
    }
  }
}
</script>
