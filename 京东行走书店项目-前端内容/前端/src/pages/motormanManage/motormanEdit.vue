<template>
  <div class="shopEdit">
    <el-dialog title :visible.sync="dialogEdit" width="52%" :close-on-click-modal="false">
      <el-form
        :model="addInfo"
        label-width="100px"
        :rules="rules"
        ref="addInfo"
        :inline="true"
        label-position="right"
      >
        <el-form-item label="司机姓名" prop="driverName">
          <el-input v-model="addInfo.driverName" maxlength="40" clearable></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="driverPhone">
          <el-input v-model="addInfo.driverPhone" maxlength="40" clearable></el-input>
        </el-form-item>
        <el-form-item label="账号" prop="userAcct">
          <el-input v-model="addInfo.userAcct" maxlength="40" clearable></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="userPwd">
          <el-input v-model="addInfo.userPwd" maxlength="40" clearable placeholder="*************"></el-input>
        </el-form-item>
        <el-form-item label="身份证号" prop="identityCard">
          <el-input v-model="addInfo.identityCard" maxlength="40" clearable></el-input>
        </el-form-item>
        <el-form-item label="所在省份" prop="province">
          <el-select v-model="addInfo.province" placeholder="请选择" disabled>
            <el-option
              v-for="item in optionProvince"
              :key="item.value"
              :label="item.value"
              :value="item.code"
            >{{item.value}}</el-option>
          </el-select>
        </el-form-item>
        <!-- <el-form-item label="所在城市" prop="city">
          <el-select v-model="addInfo.city" placeholder="请选择" @change="cityChange" disabled>
            <el-option
              v-for="item in optionsCity"
              :key="item.value"
              :label="item.value"
              :value="item.code"
            >{{item.value}}</el-option>
          </el-select>
        </el-form-item>-->
        <el-form-item label="所在区" prop="county">
          <el-select v-model="addInfo.county" placeholder="请选择" disabled>
            <el-option
              v-for="item in optionsTown"
              :key="item.value"
              :label="item.value"
              :value="item.code"
            >{{item.value}}</el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelDialog()">取 消</el-button>
        <el-button type="primary" @click="addSure('form')">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import API from "@/api/motormanManage";

export default {
  name: "motormanEdit",
  data() {
    //表单验证
    // var validateName = (rule, value, callback) => {
    //   if (value.replace(/(^\s*)|(\s*$)/g, "") === "") {
    //     //前后去空格
    //     callback(new Error("请输入角色名称"));
    //   } else {
    //     callback();
    //   }
    // };
    return {
      dialogEdit: false,
      optionProvince: [],
      optionsCity: [],
      optionsTown: [],
      id: "",
      userCode: "", // 用户编码
      addInfo: {
        roleName: "",
        remark: ""
      },
      provinceNo: "",
      countyNo: "",
      acct: "",
      phone: "",
      //  rules: {
      //   province: [{ required: true, validator: validateName, trigger: "blur" }]
      // }
      rules: {
        driverName: [
          {
            required: true,
            pattern: /^[\u4E00-\u9FA5A-Za-z]+$/,
            trigger: "blur",
            message: "请输入1-20位中文或英文"
          }
        ],
        driverPhone: [
          {
            required: true,
            pattern: /^[1][3,4,5,7,8][0-9]{9}$/,
            message: "请输入正确的手机号",
            trigger: "blur"
          }
        ],
        userAcct: [
          {
            required: true,
            trigger: "blur",
            pattern: /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$/,
            message: "请输入6-16位字母+数字"
          }
        ],
        userPwd: [
          {
            required: false,
            trigger: "blur",
            pattern: /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$/,
            message: "请输入6-16位字母+数字"
          }
        ],
        identityCard: [
          {
            required: true,
            pattern: /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$)/,
            message: "证件号码格式有误",
            trigger: "blur"
          }
        ],
        province: [{ required: true, trigger: "blur" }],
        city: [{ required: true, trigger: "blur" }],
        county: [{ required: true, trigger: "blur" }]
      }
    };
  },
  mounted() {
    //this.initData();
  },
  methods: {
    openDialog(flag, val) {
      this.dialogEdit = flag;
      this.$nextTick(function() {
        this.addInfo = val;
        this.userCode = val.userCode;
        this.id = val.id;
        this.provinceNo = val.provinceNo;
        this.countyNo = val.countyNo;
        this.acct = val.userAcct;
        this.phone = val.driverPhone;
      });
      this.$nextTick(() => {
        this.$refs.addInfo.resetFields();
      });
    },
    cancelDialog() {
      this.dialogEdit = false;
    },
    addSure() {
      let pamars = {
        driverName: this.addInfo.driverName,
        provinceNo: this.provinceNo,
        countyNo: this.countyNo,
        identityCard: this.addInfo.identityCard,
        userPwd: this.addInfo.userPwd,
        province: this.addInfo.province,
        county: this.addInfo.town,
        id: this.id,
        userCode: this.userCode // 用户编码
      };
      if (
        this.addInfo.userAcct == this.acct &&
        this.phone == this.addInfo.driverPhone
      ) {
      } else {
        if (this.addInfo.userAcct == this.acct) {
          pamars.driverPhone = this.addInfo.driverPhone;
        } else if (this.phone == this.addInfo.driverPhone) {
          pamars.userAcct = this.addInfo.userAcct;
        } else {
          pamars.driverPhone = this.addInfo.driverPhone;
          pamars.userAcct = this.addInfo.userAcct;
        }
      }
      this.$refs["addInfo"].validate(valid => {
        if (valid) {
          this.loading.startLoading();
          this.$axios.post(API.DRIVER_INFO_UPDATE, pamars).then(res => {
            this.loading.endLoading();
            if (0 == res.data.code) {
              this.$message({
                showClose: true,
                message: res.data.msg,
                type: "success"
              });
              this.$emit("getData");
              this.cancelDialog();
            } else {
              this.$message({
                showClose: true,
                message: res.data.msg,
                type: "error"
              });
            }
          });
        }
      });
    }
  }
};
</script>
<style lang="scss" scoped>
.roleEdit {
  .el-input {
    width: 90%;
  }
}
</style>
