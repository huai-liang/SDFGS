<template>
  <div class="shopEdit">
    <el-dialog title :visible.sync="dialogEdit" width="53%" :close-on-click-modal="false">
      <el-form
        :model="addInfo"
        label-width="100px"
        ref="addInfo"
        :rules="rules"
        :inline="true"
        label-position="right"
      >
        <el-form-item label="店名" prop="storeName">
          <el-input v-model="addInfo.storeName" maxlength="40" clearable></el-input>
        </el-form-item>
        <el-form-item label="联系电话" prop="storePhone" label-width="110px">
          <el-input v-model="addInfo.storePhone" maxlength="40" clearable></el-input>
        </el-form-item>
        <el-form-item label="万家账号" prop="wanjiaAccount">
          <el-input v-model="addInfo.wanjiaAccount" maxlength="40" clearable></el-input>
        </el-form-item>
        <el-form-item label="店长姓名" prop="userName" label-width="110px">
          <el-input v-model="addInfo.userName" maxlength="40" clearable></el-input>
        </el-form-item>
        <el-form-item label="店长身份证" prop="identityCard">
          <el-input v-model="addInfo.identityCard" maxlength="40" clearable></el-input>
        </el-form-item>
        <el-form-item label="营业执照编码" prop="businessLicense" label-width="110px">
          <el-input v-model="addInfo.businessLicense" maxlength="40" clearable></el-input>
        </el-form-item>
        <el-form-item label="门店账号" prop="userAcct">
          <el-input v-model="addInfo.userAcct" maxlength="40" clearable></el-input>
        </el-form-item>
        <el-form-item label="门店密码" prop="userPwd" label-width="110px">
          <el-input v-model="addInfo.userPwd" maxlength="40" clearable placeholder="*************"></el-input>
        </el-form-item>
        <el-form-item label="所在省份" prop="province">
          <el-select v-model="addInfo.province" placeholder="请选择" @change="provinceChange" disabled>
            <el-option
              v-for="item in optionProvince"
              :key="item.value"
              :label="item.provinceName"
              :value="item.provinceCode"
            ></el-option>
          </el-select>
        </el-form-item>
        <!-- <el-form-item label="所在城市" prop="city">
          <el-select v-model="addInfo.city" placeholder="请选择" @change="cityChange">
            <el-option
              v-for="item in optionsCity"
              :key="item.value"
              :label="item.value"
              :value="item.code"
            >{{item.value}}</el-option>
          </el-select>
        </el-form-item>-->
        <el-form-item label="所在区" prop="county" label-width="110px">
          <el-select v-model="addInfo.county" placeholder="请选择" disabled>
            <el-option
              v-for="item in optionsTown"
              :key="item.value"
              :label="item.countyName"
              :value="item.countyName"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="详情地址" prop="storeAddress">
          <el-input v-model="addInfo.storeAddress" maxlength="40" clearable></el-input>
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
import API from "@/api/shopManage";

export default {
  name: "roleEdit",
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
      addInfo: {
        roleName: "",
        remark: "",
        userPwd: "",
        storePhone: ""
      },
      acct: "",
      phone: "",
      provinceNoParams: "",
      countyNoParams: "",
      optionProvince: [],
      optionsCity: [],
      optionsTown: [],
      id: "",
      userCode: "", // 用户编码
      rules: {
        storeName: [
          {
            required: true,
            // pattern: /^[\u4E00-\u9FA5A-Za-z]+$/,
            trigger: "blur",
            message: "请输入店名"
          }
        ],
        storePhone: [
          //手机号码
          {
            required: true,
            pattern: /^[1][3,4,5,7,8][0-9]{9}$/,
            message: "请输入正确的手机号",
            trigger: "blur"
          }
        ],
        wanjiaAccount: [
          {
            required: false,
            //pattern: /^[1][3,4,5,7,8][0-9]{9}$/,
            message: "请输入万家账号",
            trigger: "blur"
          }
        ],
        userName: [
          {
            required: true,
            pattern: /^[\u4E00-\u9FA5A-Za-z]+$/,
            trigger: "blur",
            message: "请输入1-20位中文或英文"
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
        businessLicense: [
          {
            required: true,
            pattern: /^[a-zA-Z0-9]{1,20}$/,
            message: "请输入正确的营业执照编码",
            trigger: "blur"
          }
        ],
        userAcct: [
          {
            required: true,
            pattern: /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$/,
            message: "请输入6-16位字母+数字",
            trigger: "blur"
          }
        ],
        userPwd: [
          {
            required: false,
            pattern: /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$/,
            message: "请输入6-16位字母+数字",
            trigger: "blur"
          }
        ],
        // userPwd: [
        //   {
        //     required: true,
        //     //pattern: /^[1][3,4,5,7,8][0-9]{9}$/,
        //     message: "请输入门店密码",
        //     trigger: "blur"
        //   }
        // ],
        province: [
          {
            required: true,
            trigger: "blur",
            message: "请选择所属省份"
          }
        ],
        county: [
          {
            required: true,
            trigger: "blur",
            message: "请选择所属县区"
          }
        ],
        address: [
          {
            required: true,
            trigger: "blur",
            message: "请输入详细地址"
          }
        ]
      }
    };
  },
  mounted() {
    this.initData();
  },
  methods: {
    initData() {
      //let params = {};
      this.$axios.post(API.PROVEINCE_QUERY).then(res => {
        if (0 == res.data.code) {
          this.optionProvince = res.data.data;
          //alert(this.optionProvince);
        } else {
          this.$message({
            showClose: true,
            message: res.data.msg,
            type: "error"
          });
        }
      });
      // this.$axios
      //   .get("http://localhost:8888/static/js/districts.json")
      //   .then(res => {
      //     this.optionProvince = res.data;
      //   });
    },
    //省的change事件
    provinceChange(val) {
      let paramsCounty = {
        provinceNo: val
      };
      this.$axios.post(API.COUNTY_QUERY, paramsCounty).then(res => {
        if (0 == res.data.code) {
          this.optionsTown = res.data.data;
        } else {
          this.$message({
            showClose: true,
            message: res.data.msg,
            type: "error"
          });
        }
      });
    },
    openDialog(flag, val) {
      this.dialogEdit = flag;
      this.$nextTick(function() {
        this.addInfo = val;
        this.userCode = val.userCode;
        this.id = val.id;
        this.acct = val.userAcct;
        this.phone = val.storePhone;
        this.provinceNoParams = val.provinceNo;
        this.countyNoParams = val.countyNo;
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
        provinceNo: this.provinceNoParams, // 省份编码
        countyNo: this.countyNoParams, // 县区编码
        storeName: this.addInfo.storeName, // 商店名称
        wanjiaAccount: this.addInfo.wanjiaAccount, // 万家账号
        userName: this.addInfo.userName, // 店长姓名
        identityCard: this.addInfo.identityCard, // 身份证号
        businessLicense: this.addInfo.businessLicense, // 营业执照编号
        userPwd: this.addInfo.userPwd, // 门店密码
        province: this.addInfo.province, //所在省份
        county: this.addInfo.county, //所在区县
        storeAddress: this.addInfo.storeAddress, //详细地址,
        id: this.id,
        userCode: this.userCode // 用户编码
      };
      if (
        this.acct == this.addInfo.userAcct &&
        this.phone == this.addInfo.storePhone
      ) {
      } else {
        if (this.acct == this.addInfo.userAcct) {
          pamars.storePhone = this.addInfo.storePhone;
        } else if (this.phone == this.addInfo.storePhone) {
          pamars.userAcct = this.addInfo.userAcct;
        } else {
          pamars.userAcct = this.addInfo.userAcct;
          pamars.storePhone = this.addInfo.storePhone;
        }
      }
      this.$refs["addInfo"].validate(valid => {
        if (valid) {
          this.loading.startLoading();
          //   if (this.acct != this.addInfo.userAcct) {
          //   } else {
          //     let pamars = {
          //       userAcct: ""
          //     };
          //   }
          this.$axios.post(API.STORE_INFO_UPDATE, pamars).then(res => {
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
