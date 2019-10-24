<template>
  <div class="userDialog">
    <el-dialog width="52%" title="新增商品" :visible.sync="dialogAdd" :close-on-click-modal="false">
      <template>
        <el-tabs v-model="activeName" type="border" @tab-click="handleClick">
          <el-tab-pane label="通用信息" name="first">
            <el-form
              :model="addInfo"
              label-width="100px"
              :rules="rules"
              ref="addInfo"
              :inline="true"
              label-position="right"
            >
              <el-form-item label="sku商品名称" prop="skuName">
                <el-input v-model="addInfo.skuName" maxlength="40" clearable></el-input>
              </el-form-item>
              <el-form-item label="ISBN" prop="isbn">
                <el-input v-model="addInfo.isbn" maxlength="40" clearable></el-input>
              </el-form-item>
              <el-form-item label="定价" prop="fixPrice">
                <el-input v-model="addInfo.fixPrice" clearable></el-input>
              </el-form-item>
              <el-form-item label="销售价" prop="sellingPrice">
                <el-input v-model="addInfo.sellingPrice" clearable></el-input>
              </el-form-item>
              <el-form-item label="渠道" prop="channelType">
                <el-select v-model="addInfo.channelType" placeholder="请选择" clearable>
                  <el-option   key="行走书店"  label="行走书店" value="0"
                  ></el-option>
                  <el-option   key="京东万家"  label="京东万家" value="1"
                  ></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="作者" prop="author">
                <el-input v-model="addInfo.author" clearable></el-input>
              </el-form-item>
              <el-form-item label="出版社" prop="press">
                <el-input v-model="addInfo.press" clearable></el-input>
              </el-form-item>
              <el-form-item label="一级分类" prop="oneLevel">
                <el-select v-model="addInfo.oneLevel" filterable @change='getSecondLevel'>
                  <el-option
                    v-for="item in levelData"
                    :key="item.cateName"
                    :label="item.cateName"
                    :value="item.cateCode">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="二级分类" prop="cateCode">
                <el-select v-model="addInfo.cateCode" filterable>
                  <el-option
                    v-for="item in secondLevelData"
                    :key="item.cateName"
                    :label="item.cateName"
                    :value="item.cateCode">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="广告词" prop="advertising">
                <el-input
                  type="textarea"
                  :rows="3"
                  maxlength="200"
                  v-model="addInfo.advertising"
                  clearable
                  style="width:500px;"
                ></el-input>
              </el-form-item>
            </el-form>
          </el-tab-pane>
          <el-tab-pane label="商品介绍" name="second">
            <div class="edit_container">
              <!-- 图片上传组件辅助-->
              <el-upload
                      class="avatar-uploader"
                      :action="getUploadUrl"
                      name="file"
                      :show-file-list="false"
                      :on-success="uploadSuccess"
                      :on-error="uploadError"
                      :on-exceed="handleExceedAdd"
                      multiple
                      :before-upload="beforeUpload">
              </el-upload>
              <!--富文本编辑器组件-->
              <el-row v-loading="quillUpdateImg">
                <quill-editor
                  v-model="addInfo.detail" 
                  ref="myQuillEditor"
                  :options="editorOption"
                  @blur="onEditorBlur($event)" @focus="onEditorFocus($event)"
                  @change="onEditorChange($event)"
                  @ready="onEditorReady($event)">
                </quill-editor>
              </el-row>
            </div> 
          </el-tab-pane>
          <el-tab-pane label="列表图片" name="third">
            <p style="color:red">注：最多上传五张图片，第一张默认为商品主图</p>
            <el-upload
            :action="getUploadUrl"
            list-type="picture-card"
            :on-preview="handlePictureCardPreview"
            :on-remove="handleRemove"
            :on-success="handleAvatarSuccess"
            :on-exceed="handleExceed"
            multiple
            :limit="5"
            :before-upload="beforeAvatarUpload"
            :file-list="dataUrl"
            >
            <i class="el-icon-plus"></i>
            </el-upload>
            <el-dialog :visible.sync="dialogVisible" append-to-body>
              <img width="100%" :src="dialogImageUrl" alt="">
            </el-dialog>
          </el-tab-pane>
        </el-tabs>
      </template>
     <!--  <el-form
        :model="addInfo"
        label-width="100px"
        :rules="rules"
        ref="addInfo"
        :inline="true"
        label-position="right"
      >
        <el-form-item label="sku商品名称" prop="skuName">
          <el-input v-model="addInfo.skuName" maxlength="40" clearable></el-input>
        </el-form-item>
        <el-form-item label="ISBN" prop="isbn">
          <el-input v-model="addInfo.isbn" maxlength="40" clearable></el-input>
        </el-form-item>
        <el-form-item label="定价" prop="fixPrice">
          <el-input v-model="addInfo.fixPrice" clearable></el-input>
        </el-form-item>
        <el-form-item label="销售价" prop="sellingPrice">
          <el-input v-model="addInfo.sellingPrice" clearable></el-input>
        </el-form-item>
        <el-form-item label="渠道" prop="channelType">
          <el-select v-model="addInfo.channelType" placeholder="请选择" clearable>
            <el-option   key="行走书店"  label="行走书店" value="0"
            ></el-option>
            <el-option   key="京东万家"  label="京东万家" value="1"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="作者" prop="author">
          <el-input v-model="addInfo.author" clearable></el-input>
        </el-form-item>
        <el-form-item label="出版社" prop="press">
          <el-input v-model="addInfo.press" clearable></el-input>
        </el-form-item>
        <el-form-item label="一级分类" prop="oneLevel">
          <el-select v-model="addInfo.oneLevel" filterable @change='getSecondLevel'>
            <el-option
              v-for="item in levelData"
              :key="item.cateName"
              :label="item.cateName"
              :value="item.cateCode">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="二级分类" prop="cateCode">
          <el-select v-model="addInfo.cateCode" filterable>
            <el-option
              v-for="item in secondLevelData"
              :key="item.cateName"
              :label="item.cateName"
              :value="item.cateCode">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="广告词" prop="advertising">
          <el-input
            type="textarea"
            :rows="3"
            maxlength="200"
            v-model="addInfo.advertising"
            clearable
            style="width:500px;"
          ></el-input>
        </el-form-item>
        <el-form-item label="商品介绍" prop="detail">
          <el-input
            type="textarea"
            :rows="3"
            maxlength="200"
            v-model="addInfo.detail"
            clearable
            style="width:500px;"
          ></el-input>
        </el-form-item>
        <el-form-item label="列表图片"  prop="picUrl">
          <p style="color:red">注：最多上传三张图片，第一张默认为商品主图</p>
            <el-upload
            :action="getUploadUrl"
            list-type="picture-card"
            :on-preview="handlePictureCardPreview"
            :on-remove="handleRemove"
            :on-success="handleAvatarSuccess"
            :on-exceed="handleExceed"
            :limit="3"
            :before-upload="beforeAvatarUpload"
            :file-list="dataUrl"
            style="width:546px;"
            >
            <i class="el-icon-plus"></i>
            </el-upload>
        </el-form-item>
      </el-form> -->
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="addSure('addInfo')">确 定</el-button>
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
import { prodUrl } from '@/common/config/prod.env';

// 工具栏配置
const toolbarOptions = [
  ['bold', 'italic', 'underline', 'strike'],        // toggled buttons
  ['blockquote', 'code-block'],

  [{'header': 1}, {'header': 2}],               // custom button values
  [{'list': 'ordered'}, {'list': 'bullet'}],
  [{'script': 'sub'}, {'script': 'super'}],      // superscript/subscript
  [{'indent': '-1'}, {'indent': '+1'}],          // outdent/indent
  [{'direction': 'rtl'}],                         // text direction

  [{'size': ['small', false, 'large', 'huge']}],  // custom dropdown
  [{'header': [1, 2, 3, 4, 5, 6, false]}],

  [{'color': []}, {'background': []}],          // dropdown with defaults from theme
  [{'font': []}],
  [{'align': []}],
  ['link', 'image', 'video'],
  ['clean']                                         // remove formatting button
]

export default {
  name: 'goodsAdd',
  data() {
    var validateAcct = (rule, value, callback) => {
      if (value.replace(/(^\s*)|(\s*$)/g, "") === '') {//前后去空格
        callback(new Error('请输入sku商品名称'));
      } else {
        callback();
      }
    };
    return {
      dialogAdd: false,
      activeName: 'first',
      dialogImageUrl: '',
      dialogVisible: false,
      addInfo: {
        skuName:'',//sku商品名称
        isbn:'',//ISBN
        fixPrice:'',//定价
        sellingPrice:'',//销售价
        channelType:'',//渠道
        oneLevel:'',//一级分类
        cateCode:'',//二级分类
        author:'',//作者
        press:'',//出版社
        advertising:'',//广告词
        detail:'',//商品介绍
        picUrl:'',//商品图片
      },
      dataUrl:[],//存放上传图片路径
      levelData:[],//一级分类
      secondLevelData:[],//二级分类
      editorOption: {
        placeholder: '',
        theme: 'snow',  // or 'bubble'
        modules: {
          toolbar: {
            container: toolbarOptions,  // 工具栏
            handlers: {
              'image': function (value) {
                if (value) {
                    document.querySelector('.avatar-uploader input').click()
                } else {
                    this.quill.format('image', false);
                }
              }
            }
          }
        }
      },//富文本编辑器配置
      quillUpdateImg: false, // 根据图片上传状态来确定是否显示loading动画，刚开始是false,不显示
      getUploadUrl: (process.env.NODE_ENV === 'production'?prodUrl:devUrl)+API.UPLOAD_API+'?access_token=' + this.$store.state.userInfo.userInfo.access_token,
      rules: {
        skuName: [
          { required: true, validator: validateAcct, trigger: 'blur' }
        ],
        isbn: [
          { required: true, pattern: /^(\d{10}|\d{13})$/, message: 'ISBN为10或13位数字', trigger: 'blur' }
        ],
        fixPrice: [//定价
          { required: true, pattern: /((^[1-9]\d{0,6})|^0)(\.\d{0,2}){0,1}$/, message: '请输入非负数字', trigger: 'blur' }
        ],
        sellingPrice: [//销售价
          { required: true, pattern: /((^[1-9]\d{0,6})|^0)(\.\d{0,2}){0,1}$/, message: '请输入非负数字', trigger: 'blur' }
        ],
        channelType: [
          { required: true, message: '请选择渠道', trigger: 'change' }
        ],
        author: [
          { required: true, message: '请输入作者', trigger: 'blur' }
        ],
        press: [
          { required: true, message: '请输入出版社', trigger: 'blur' }
        ],
        oneLevel: [
          { required: true, message: '请选择一级分类', trigger: 'change' }
        ],
        cateCode: [
          { required: true, message: '请选择二级分类', trigger: 'change' }
        ],
      }
    }
  },
  // computed() {
  //   editor() {
  //     return this.$refs.myQuillEditor.quill;
  //   }
  // },
  methods: {
    //切换tab页
    handleClick(tab, event) {
      if(tab.name=="four"){
        
      }
      
    },
    // 准备编辑器
    onEditorReady(editor) { 

    },
    // 失去焦点事件
    onEditorBlur(){}, 
    // 获得焦点事件
    onEditorFocus(){},
    // 内容改变事件 
    onEditorChange(){}, 
    // 上传图片前
    beforeUpload(res, file) {
      // 显示loading动画
      this.quillUpdateImg = true
    },
    // 上传图片成功
    uploadSuccess(res, file) {
      //console.log(res);
      // res为图片服务器返回的数据
      // 获取富文本组件实例
      let quill = this.$refs.myQuillEditor.quill
      //console.log(quill.getSelection());
      // 如果上传成功
      if (res.code =='0') {
          // 获取光标所在位置
          let length = quill.getSelection().index;
          // 插入图片  res.info为服务器返回的图片地址
          quill.insertEmbed(length, 'image', res.data)
          // 调整光标到最后
          quill.setSelection(length + 1)
      } else {
          this.$message.error(res.msg)
      }
      // loading动画消失
      this.quillUpdateImg = false
    },
    // 上传图片失败
    uploadError(res, file) {
      // loading动画消失
      this.quillUpdateImg = false
      this.$message.error(res.msg)
    },
    //上传限制提示
    handleExceedAdd(files, fileList) {
      this.$message.warning(`当前限制只能选择 10 个文件`);
    },
    openDialog(flag) {
      this.activeName='first';
      this.loading.startLoading();
      this.$axios.post(API.GOODS_LIST_API).then(res => {
      this.loading.endLoading();
        if (0 == res.data.code) {
          this.levelData = res.data.data;
        } else {
          this.$message({
            showClose: true,
            message: res.data.msg,
            type: 'error'
          });
        }
      });
      this.dialogAdd = flag;
      this.$nextTick(() => {
        this.$refs.addInfo.resetFields();
        this.addInfo.detail="";
        this.dataUrl=[];
      });
    },
    cancelDialog() {
      this.dialogAdd = false;
      this.quillUpdateImg = false
    },
    //下拉选择二级分类
    getSecondLevel(){
      var that=this;
      this.levelData.forEach(function(item){
        if(item.cateCode==that.addInfo.oneLevel){
          that.addInfo.cateCode="";
          that.secondLevelData=item.secondCategory;
        }
      })
    },
    //删除图片
    handleRemove(file, fileList) {
      this.dataUrl=fileList;
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    beforeAvatarUpload(file) {
      const isJPG = (file.type === 'image/jpeg')||(file.type === 'image/png')||(file.type === 'image/jpg')||(file.type === 'image/gif')||(file.type === 'image/tiff');
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传图片只能是 JPG、JPEG、GIF、PNG、TIFF格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    },
    handleAvatarSuccess(res, file,fileList) {
      if(res.code==0){
        this.dataUrl=fileList;
      }else{
        this.dataUrl=[];
        this.$message({
          showClose: true,
          message: res.msg,
          type: 'error'
        });
      }
      
    },
    //上传限制提示
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制只能选择 5 个文件`);
    },
    addSure() {
      this.$refs['addInfo'].validate((valid) => {
        if (valid) {
          let dataUrl=[];
          if(this.dataUrl&&this.dataUrl.length){
            this.dataUrl.forEach(function(item){
              dataUrl.push(item.response.data);
            })
          }else{
            this.$message({
              showClose: true,
              message: '列表图片不能为空',
              type: 'error'
            });
            return;
          }
          this.addInfo.picUrl=dataUrl.join(",");
          this.loading.startLoading();
          this.$axios.post(API.ADD_GOODS_API, this.addInfo).then(res => {
            this.loading.endLoading();
            if (0 == res.data.code) {
              this.$message({
                showClose: true,
                message: '新增成功',
                type: 'success'
              });
              this.dataUrl=[];
              this.$emit('search');
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
