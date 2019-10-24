<template>
  <div class="userDialog">
    <el-dialog width="55%" title="订单备注" :visible.sync="dialogRemark" :close-on-click-modal="false">
    	<el-form
        :model="editInfo"
        label-width="100px"
        :rules="rules"
        ref="editInfo"
        :inline="true"
        label-position="right"
      >
        <el-form-item label="输入备注" prop="remark">
          <el-input v-model="editInfo.remark" maxlength="200" clearable style="width:500px;"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
      	<el-button type="primary" @click="editSure()">确 定</el-button>
        <el-button @click="cancelDialog()">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import API from '@/api/orderManage/orderManage';

export default {
  name: 'orderRemark',
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
      dialogRemark: false,
      editInfo: {
        id:'',
        orderNo:'',
        remark:'',//订单备注
      },
      rules: {
        userAcct: [
          { required: true, validator: validateAcct, trigger: 'blur' }
        ],
        userName: [
          { required: true, validator: validateName, trigger: 'blur' }
        ],
        idCard: [//身份证验证
          { pattern: /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$)/, message: '证件号码格式有误！', trigger: 'blur' }
        ],
        tel: [//固定电话
          { pattern: /^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/, message: '请输入正确的固定电话', trigger: 'blur' }
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
    openDialog(flag, order) {
      this.dialogRemark = flag;
      this.$nextTick(function () {
        this.editInfo.id = order.id;
        this.editInfo.orderNo = order.orderNo;
        this.editInfo.remark = order.remark;
      });
    },
    cancelDialog() {
      this.dialogRemark = false;
    },
    editSure() {
      this.$refs['editInfo'].validate((valid) => {
        if (valid) {
          this.loading.startLoading();
          this.$axios.post(API.EDIT_ORDER_API, this.editInfo).then(res => {
            this.loading.endLoading();
            if (0 == res.data.code) {
              this.$message({
                showClose: true,
                message: '修改成功',
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
