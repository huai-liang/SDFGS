<template>
  <div class="userDialog">
    <el-dialog width="55%" title="修改用户" :visible.sync="dialogEdit" :close-on-click-modal="false">
      <el-form
        v-if="dialogEdit"
        :model="addInfo"
        label-width="100px"
        :rules="rules"
        ref="addInfo"
        :inline="true"
        label-position="right"
      >
        <el-form-item label="用户账号" prop="userAcct">
          <el-input v-model="addInfo.userAcct" maxlength="40" clearable></el-input>
        </el-form-item>
        <el-form-item label="用户名称" prop="userName">
          <el-input v-model="addInfo.userName" maxlength="40" clearable></el-input>
        </el-form-item>
        <el-form-item label="身份证号码" prop="idCard">
          <el-input v-model="addInfo.idCard" clearable></el-input>
        </el-form-item>
        <el-form-item label="固定电话" prop="tel">
          <el-input v-model="addInfo.tel" clearable></el-input>
        </el-form-item>
        <el-form-item label="手机号码" prop="phone">
          <el-input v-model="addInfo.phone" clearable></el-input>
        </el-form-item>
        <el-form-item label="电子邮箱" prop="email">
          <el-input v-model="addInfo.email" clearable></el-input>
        </el-form-item>
        <el-form-item label="是否是管理员" prop="isAdmin">
          <el-select v-model="addInfo.isAdmin" placeholder="请选择">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-radio-group v-model="addInfo.sex">
            <el-radio :label="'1'">男</el-radio>
            <el-radio :label="'0'">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input
            type="textarea"
            :rows="3"
            maxlength="200"
            v-model="addInfo.remark"
            clearable
            style="width:500px;"
          ></el-input>
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
import API from '@/api/systemManage/user';

export default {
  name: 'userEdit',
  props: {
    user: {
      type: Object,
      default: () => { }
    }
  },
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
      dialogEdit: false,
      addInfo: {},
      options:[{
        value: '1',
        label: '是'
      },{
        value: '0',
        label: '否'
      }],
      rules: {
        userAcct: [
          { required: true, pattern: /^[a-zA-Z0-9]{6,16}$/, message: '请输入6-16个数字、字母', trigger: 'blur' }
        ],
        userName: [
          { required: true, validator: validateName, trigger: 'blur' }
        ],
        idCard: [//身份证验证
          { pattern: /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$)/, message: '证件号码格式有误！', trigger: 'blur' }
        ],
        tel: [//固定电话
          { pattern: /^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/, message: '请输入正确的固定电话(0000-00000000)', trigger: 'blur' }
        ],
        phone: [//手机号码
          { pattern: /^[1][3,4,5,7,8][0-9]{9}$/, message: '请输入正确的手机号码', trigger: 'blur' }
        ],
        email: [//电子邮箱
          { pattern: /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/, message: '请输入正确电子邮箱', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    openDialog(flag, user) {
      this.dialogEdit = flag;
      this.$nextTick(function () {
        this.addInfo = user;
      });
    },
    cancelDialog() {
      this.dialogEdit = false;
    },
    addSure() {
      this.$refs['addInfo'].validate((valid) => {
        if (valid) {
          this.loading.startLoading();
          //删除创建和修改日期 否则会报错
          delete this.addInfo.gmtCreate;
          delete this.addInfo.gmtModified;
          this.$axios.post(API.UPDATE_USER_API, this.addInfo).then(res => {
            this.loading.endLoading();
            if (0 == res.data.code) {
              this.$message({
                showClose: true,
                message: '修改用户成功',
                type: 'success'
              });
              this.$emit('getData');
              this.cancelDialog();
            } else {
              this.$message({
                showClose: true,
                message: res.data.msg,
                type: 'error'
              });
            }
          });
        }
      });
    }
  }
}
</script>
