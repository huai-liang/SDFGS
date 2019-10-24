<template>
  <div class="userDialog">
    <el-dialog width="52%" title="上传图片压缩包(.zip结尾的文件)" :visible.sync="dialogUploadLotSizePic" :close-on-click-modal="false">
       <el-upload
        class="upload-demo"
        ref="upload"
        :action="uploadUrl"
        :on-preview="handlePreview"
        :on-remove="handleRemove"
        :on-success="handleAvatarSuccess"
        :limit="1"
        :before-upload="beforeAvatarUpload"
        :on-exceed="handleExceed"
        :file-list="fileListLotSiz"
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
  name: 'goodsUploadLotSizePic',
  data() {
    return {
      dialogUploadLotSizePic: false,
      fileListLotSiz:[],//上传文件
      uploadUrl: (process.env.NODE_ENV === 'production'?prodUrl:devUrl)+API.GOODPICZIP_API+'?access_token=' + this.$store.state.userInfo.userInfo.access_token,
    }
  },
  methods: {
    openDialog(flag) {
      this.dialogUploadLotSizePic = flag;
    },
    cancelDialog() {
      this.dialogUploadLotSizePic = false;
      this.fileListLotSiz=[];
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
      const isZip=/(\.zip)$/i.test(file.name);
      const isLt10M = file.size / 1024 / 1024 < 1000;

      if (!isZip) {
        this.$message.error('请上传压缩包以.zip结尾的文件');
      }
      if (!isLt10M) {
        this.$message.error('上传压缩包大小不能超过 1000MB!');
      }
      if(isZip && isLt10M) {
        this.loading.startLoading();
      }
      return isZip && isLt10M;
    },
    handleAvatarSuccess(res) {
      this.loading.endLoading();
      //console.log(res);
      if(res.code=='0'){
        this.fileListLotSiz=[];
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
      this.$message.warning(`当前限制只能上传 1 个文件夹`);
    },
    addSure() {
      
    }
  }
}
</script>
