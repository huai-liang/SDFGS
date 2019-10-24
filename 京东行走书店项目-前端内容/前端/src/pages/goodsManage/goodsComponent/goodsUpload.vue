<template>
  <div class="userDialog">
    <el-dialog width="52%" title="导入商品" :visible.sync="dialogUpload" :close-on-click-modal="false">
       <el-upload
        class="upload-demo"
        ref="upload"
        :action="uploadUrl"
        :on-preview="handlePreview"
        :on-remove="handleRemove"
        :on-success="handleAvatarSuccess"
        :limit="1"
        :before-upload="beforeAvatarUpload"
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
  name: 'goodsUpload',
  data() {
    //表单验证飞控去空格后非空
    var validateName = (rule, value, callback) => {
      if (value.replace(/(^\s*)|(\s*$)/g, "") === '') {//前后去空格
        callback(new Error('请输入用户名称'));
      } else {
        callback();
      }
    };
    var validateAcct = (rule, value, callback) => {
      if (value.replace(/(^\s*)|(\s*$)/g, "") === '') {//前后去空格
        callback(new Error('请输入用户账号'));
      } else {
        callback();
      }
    };
    return {
      dialogUpload: false,
      fileList:[],//上传文件
      uploadUrl: (process.env.NODE_ENV === 'production'?prodUrl:devUrl)+API.IMPORTEXCEL_API+'?access_token=' + this.$store.state.userInfo.userInfo.access_token,
      downloadUrl: (process.env.NODE_ENV === 'production'?prodUrl:devUrl)+API.DOWNLOADEXCEL_API+'?access_token=' + this.$store.state.userInfo.userInfo.access_token,
    }
  },
  methods: {
    openDialog(flag) {
      this.dialogUpload = flag;
    },
    cancelDialog() {
      this.dialogUpload = false;
      this.fileList=[];
    },
    //上传开始
    submitUpload() {
      if(0 !=this.$refs.upload.uploadFiles.length){
        this.$refs.upload.submit();
        
      }else {
        this.$message({
          showClose: true,
          message: '请先选取上传文件',
          type: 'error'
        });
      }
    },
    handleRemove(file, fileList) {
      //console.log(file, fileList);
    },
    handlePreview(file) {
      //console.log(file);
    },
    beforeAvatarUpload(file) {
      const isExcel=/(\.xl)/i.test(file.name);
      const isLt10M = file.size / 1024 / 1024 < 10;

      if (!isExcel) {
        this.$message.error('请上传excel模板文件');
        this.loading.endLoading();
      }
      if (!isLt10M) {
        this.$message.error('上传excel大小不能超过 10MB!');
      }
      if(isExcel && isLt10M) {
        this.loading.startLoading();
      }
      return isExcel && isLt10M;
    },
    handleAvatarSuccess(res) {
      this.loading.endLoading();
      if(res.code=='0'){
        this.fileList=[];
        this.$emit('getData');
        this.cancelDialog();
      }else if(res.code=='7000'){        
        this.fileList=[];
        this.$emit('getData');
        this.cancelDialog();
        this.$message({
          showClose: true,
          message: res.msg,
          type: 'error'
        });
        window.location.href=encodeURI(this.downloadUrl+"&filePath="+res.data.filePath+"&fileName=errorList.zip");
      }else{
        this.$message({
          showClose: true,
          message: res.msg,
          type: 'error'
        });
        this.$refs.upload.uploadFiles=[];
      }
    },
    addSure() {
      
    }
  }
}
</script>
