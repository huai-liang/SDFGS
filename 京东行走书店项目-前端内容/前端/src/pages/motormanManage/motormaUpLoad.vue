<template>
  <div class="userDialog">
    <el-dialog
      width="52%"
      title="司机管理信息导入"
      :visible.sync="dialogUpload"
      :close-on-click-modal="false"
    >
      <el-upload
        class="upload-demo"
        ref="upload"
        :action="uploadUrl"
        :on-preview="handlePreview"
        :on-remove="handleRemove"
        :on-success="handleAvatarSuccess"
        :limit="1"
        :file-list="fileList"
        :auto-upload="false"
        :before-upload="beforeUpload"
      >
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
import API from "@/api/motormanManage";
//测试环境
import { devUrl } from "@/common/config/dev.env";
//正式环境
import { prodUrl } from "@/common/config/prod.env";
export default {
  name: "motormanUpload",
  data() {
    //表单验证飞控去空格后非空
    // var validateName = (rule, value, callback) => {
    //   if (value.replace(/(^\s*)|(\s*$)/g, "") === "") {
    //     //前后去空格
    //     callback(new Error("请输入用户名称"));
    //   } else {
    //     callback();
    //   }
    // };
    // var validateAcct = (rule, value, callback) => {
    //   if (value.replace(/(^\s*)|(\s*$)/g, "") === "") {
    //     //前后去空格
    //     callback(new Error("请输入用户账号"));
    //   } else {
    //     callback();
    //   }
    // };
    return {
      dialogUpload: false,
      fileList: [], //上传文件
      uploadUrl:
        (process.env.NODE_ENV === "production" ? prodUrl : devUrl) +
        API.DRIVER_INFO_IMPORT +
        "?access_token=" +
        this.$store.state.userInfo.userInfo.access_token,
      url:
        (process.env.NODE_ENV === "production" ? prodUrl : devUrl) +
        API.DRIVER_DOWENLOAD_ERRO,
      filePach: ""
    };
  },
  methods: {
    openDialog(flag) {
      this.dialogUpload = flag;
    },
    cancelDialog() {
      this.dialogUpload = false;
      this.fileList = [];
    },
    beforeUpload(file) {
      var FileExt = file.name.replace(/.+\./, "");
      if (["xlsx", "xsl"].indexOf(FileExt.toLowerCase()) === -1) {
        this.$message({
          type: "error",
          message: "请上传后缀名为xlsx、xsl的附件！"
        });
        this.cancelDialog();
        return false;
      }
      this.isLt2k = file.size / 1024 < 500 ? "1" : "0";
      if (this.isLt2k === "0") {
        this.$message({
          message: "上传文件大小不能超过500k!",
          type: "error"
        });
        this.cancelDialog();
      }
      return this.isLt2k === "1" ? true : false;
    },
    //上传开始
    submitUpload() {
      if (this.$refs.upload.uploadFiles != 0) {
        this.$refs.upload.submit();
      } else {
        this.$message({
          message: "请选取上传文件",
          type: "error"
        });
      }
      // this.loading.startLoading(); //打开遮罩
    },
    handleRemove(file, fileList) {},
    handlePreview(file) {},
    handleAvatarSuccess(res) {
      this.loading.startLoading(); //打开遮罩
      this.loading.endLoading();
      if (res.code == "0") {
        this.fileList = [];
        this.$emit("getData");
        this.cancelDialog();
        this.$message({
          showClose: true,
          message: res.msg,
          type: "success"
        });
      } else if (res.code == "7000") {
        this.cancelDialog();
        this.filePach = res.data.FILE_PATH;
        this.errow();
        this.$message({
          showClose: true,
          message: res.msg,
          type: "error"
        });
      } else {
        this.$message({
          showClose: true,
          message: res.msg,
          type: "error"
        });
        this.cancelDialog();
      }
    },
    errow() {
      window.location.href = encodeURI(
        this.url +
          "?" +
          "filePath=" +
          this.filePach +
          "&fileName=司机信息异常数据.zip" +
          "&access_token=" +
          this.$store.state.userInfo.userInfo.access_token
      );
      this.cancelDialog();
    },
    addSure() {}
  }
};
</script>
