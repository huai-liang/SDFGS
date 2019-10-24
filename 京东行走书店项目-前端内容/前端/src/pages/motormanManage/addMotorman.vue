<template>
  <div class="userDialog">
    <el-dialog width="52%" title :visible.sync="dialogAdd" :close-on-click-modal="false">
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
          <el-input v-model="addInfo.userPwd" maxlength="40" clearable></el-input>
        </el-form-item>
        <el-form-item label="身份证号" prop="identityCard">
          <el-input v-model="addInfo.identityCard" maxlength="40" clearable></el-input>
        </el-form-item>
        <el-form-item label="所在省份" prop="province">
          <el-select
            v-model="addInfo.province"
            filterable
            placeholder="请选择"
            @change="provinceChange"
          >
            <el-option
              v-for="item in optionProvince"
              :key="item.value"
              :label="item.provinceName"
              :value="item"
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
        <el-form-item label="所在区" prop="country">
          <el-select v-model="addInfo.country" placeholder="请选择" filterable @change="countryChange">
            <el-option
              v-for="item in optionsTown"
              :key="item.value"
              :label="item.countyName"
              :value="item"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelDialog()">取 消</el-button>
        <el-button type="primary" @click="addSure('addInfo')">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import API from "@/api/motormanManage";
import APISHOP from "@/api/shopManage";
export default {
  name: "addData",
  data() {
    //表单验证飞控去空格后非空
    // var validateName = (rule, value, callback) => {
    //   if (value.replace(/(^\s*)|(\s*$)/g, "") === '') {//前后去空格
    //     callback(new Error('请输入用户名称'));
    //   } else {
    //     callback();
    //   }
    // };
    // var validateAcct = (rule, value, callback) => {
    //   if (value.replace(/(^\s*)|(\s*$)/g, "") === '') {//前后去空格
    //     callback(new Error('请输入用户账号'));
    //   } else {
    //     callback();
    //   }
    // };
    return {
      dialogAdd: false,
      addInfo: {
        province: "",
        country: "",
        driverName: "",
        userAcct: "",
        identityCard: "",
        driverPhone: "",
        userPwd: ""
      },
      optionProvince: [],
      optionsTown: [],
      provinceNo: "", // 省份编码
      countyNo: "", // 县区编码
      //  county: "", // 县区名称
      rules: {
        country: [
          {
            required: true,
            trigger: "blur",
            message: "请选择所在区"
          }
        ],
        province: [
          {
            required: true,
            trigger: "blur",
            message: "请选择所属省份"
          }
        ],
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
            required: true,
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
        ]
      }
    };
  },
  mounted() {
    this.initData();
  },
  methods: {
    initData() {
      let pamarsAdd = {};
      this.$axios.post(APISHOP.PROVEINCE_QUERY, pamarsAdd).then(res => {
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
    },
    //省的change事件
    provinceChange(value) {
      this.addInfo.country = "";
      this.provinceNo = value.provinceCode;
      // 通过省份code作为入参查询
      let paramsCode = {
        provinceNo: this.provinceNo
      };
      this.$axios.post(APISHOP.COUNTY_QUERY, paramsCode).then(res => {
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
      this.addInfo.province = value.provinceName;
    },
    // 区县的change事件
    countryChange(value) {
      this.countyNo = value.countyCode;
      //this.county = value.countyName;
      this.addInfo.country = value.countyName;
    },
    openDialog(flag) {
      this.dialogAdd = flag;
      this.$nextTick(() => {
        this.$refs.addInfo.resetFields();
      });
    },
    cancelDialog() {
      this.dialogAdd = false;
    },
    addSure() {
      this.$refs["addInfo"].validate(valid => {
        if (valid) {
          this.loading.startLoading();
          let pamars = {
            driverName: this.addInfo.driverName,
            driverPhone: this.addInfo.driverPhone,
            provinceNo: this.provinceNo,
            countyNo: this.countyNo,
            identityCard: this.addInfo.identityCard,
            userAcct: this.addInfo.userAcct,
            userPwd: this.addInfo.userPwd,
            province: this.addInfo.province,
            county: this.addInfo.country
          };
          this.$axios.post(API.DRIVER_INFO_ADD, pamars).then(res => {
            this.loading.endLoading();
            if (0 == res.data.code) {
              this.$message({
                showClose: true,
                message: res.data.msg,
                type: "success"
              });
              this.$emit("search");
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
