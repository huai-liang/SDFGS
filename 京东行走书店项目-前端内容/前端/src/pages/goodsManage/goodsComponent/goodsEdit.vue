<template>
  <div class="userDialog">
    <el-dialog width="55%" title="修改商品" :visible.sync="dialogEdit" :close-on-click-modal="false">
      <template>
        <el-tabs v-model="activeName" type="border" @tab-click="handleClick">
          <el-tab-pane label="通用信息" name="first">
            <el-form
              :model="editInfo"
              label-width="100px"
              :rules="rules"
              ref="editInfo"
              :inline="true"
              label-position="right"
            >
              <el-form-item label="sku商品名称" prop="skuName">
                <el-input v-model="editInfo.skuName" maxlength="40" clearable></el-input>
              </el-form-item>
              <el-form-item label="ISBN" prop="isbn">
                <el-input v-model="editInfo.isbn" maxlength="40" clearable></el-input>
              </el-form-item>
              <el-form-item label="定价" prop="fixPrice">
                <el-input v-model="editInfo.fixPrice" clearable></el-input>
              </el-form-item>
              <el-form-item label="销售价" prop="sellingPrice">
                <el-input v-model="editInfo.sellingPrice" clearable></el-input>
              </el-form-item>
              <el-form-item label="渠道" prop="channelType">
                <el-select v-model="editInfo.channelType" placeholder="请选择" clearable>
                  <el-option   key="行走书店"  label="行走书店" value="0"
                  ></el-option>
                  <el-option   key="京东万家"  label="京东万家" value="1"
                  ></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="作者" prop="author">
                <el-input v-model="editInfo.author" clearable></el-input>
              </el-form-item>
              <el-form-item label="出版社" prop="press">
                <el-input v-model="editInfo.press" clearable></el-input>
              </el-form-item>
              <el-form-item label="一级分类" prop="oneLevel">
                <el-select v-model="editInfo.oneLevel" filterable @change='getSecondLevel'>
                  <el-option
                    v-for="item in levelData"
                    :key="item.cateName"
                    :label="item.cateName"
                    :value="item.cateCode">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="二级分类" prop="cateCode">
                <el-select v-model="editInfo.cateCode" filterable>
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
                  v-model="editInfo.advertising"
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
                      class="avatar-uploaderEdit"
                      :action="getUploadUrl"
                      name="file"
                      :show-file-list="false"
                      :on-success="uploadSuccessEdit"
                      :on-error="uploadError"
                      :on-exceed="handleExceedEdit"
                       multiple
                      :before-upload="beforeUploadEdit">
              </el-upload>
              <!--富文本编辑器组件-->
              <el-row v-loading="quillUpdateImgEdit">
                <quill-editor
                  v-model="editInfo.detail" 
                  ref="myQuillEditorEdit"
                  :options="editorOptionEdit"
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
    	<!-- <el-form
        :model="editInfo"
        label-width="100px"
        :rules="rules"
        ref="editInfo"
        :inline="true"
        label-position="right"
      >
        <el-form-item label="sku商品名称" prop="skuName">
          <el-input v-model="editInfo.skuName" maxlength="40" clearable></el-input>
        </el-form-item>
        <el-form-item label="ISBN" prop="isbn">
          <el-input v-model="editInfo.isbn" maxlength="40" clearable></el-input>
        </el-form-item>
        <el-form-item label="定价" prop="fixPrice">
          <el-input v-model="editInfo.fixPrice" clearable></el-input>
        </el-form-item>
        <el-form-item label="销售价" prop="sellingPrice">
          <el-input v-model="editInfo.sellingPrice" clearable></el-input>
        </el-form-item>
        <el-form-item label="渠道" prop="channelType">
          <el-select v-model="editInfo.channelType" placeholder="请选择" clearable>
            <el-option   key="行走书店"  label="行走书店" value="0"
            ></el-option>
            <el-option   key="京东万家"  label="京东万家" value="1"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="作者" prop="author">
          <el-input v-model="editInfo.author" clearable></el-input>
        </el-form-item>
        <el-form-item label="出版社" prop="press">
          <el-input v-model="editInfo.press" clearable></el-input>
        </el-form-item>
        <el-form-item label="一级分类" prop="oneLevel">
          <el-select v-model="editInfo.oneLevel" filterable @change='getSecondLevel'>
            <el-option
              v-for="item in levelData"
              :key="item.cateName"
              :label="item.cateName"
              :value="item.cateCode">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="二级分类" prop="cateCode">
          <el-select v-model="editInfo.cateCode" filterable>
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
            v-model="editInfo.advertising"
            clearable
            style="width:500px;"
          ></el-input>
        </el-form-item>
        <el-form-item label="商品介绍" prop="detail">
          <el-input
            type="textarea"
            :rows="3"
            maxlength="200"
            v-model="editInfo.detail"
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
      	<el-button type="primary" @click="editSure()">确 定</el-button>
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
  name: 'goodsEdit',
  props: {
    user: {
      type: Object,
      default: () => { }
    }
  },
  data() {
    //表单验证飞控去空格后非空
    var validateAcct = (rule, value, callback) => {
      if (value.replace(/(^\s*)|(\s*$)/g, "") === '') {//前后去空格
        callback(new Error('请输入sku商品名称'));
      } else {
        callback();
      }
    };
    return {
      dialogEdit: false,
      activeName: 'first',
      dialogImageUrl: '',//放大图片地址
      dialogVisible: false,//放大图片
      editInfo: {},
      levelData:[],//一级分类
      secondLevelData:[],//二级分类
      editorOptionEdit: {
        placeholder: '',
        theme: 'snow',  // or 'bubble'
        modules: {
          toolbar: {
            container: toolbarOptions,  // 工具栏
            handlers: {
              'image': function (value) {
                if (value) {
                    document.querySelector('.avatar-uploaderEdit input').click()
                } else {
                    this.quill.format('image', false);
                }
              }
            }
          }
        }
      },//富文本编辑器配置
      quillUpdateImgEdit: false, // 根据图片上传状态来确定是否显示loading动画，刚开始是false,不显示

      dataUrl:[],//存放上传图片路径
      picture:[],//存放返回来的图片路径
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
    beforeUploadEdit(res, file) {
      // 显示loading动画
      this.quillUpdateImgEdit = true
    },
    // 上传图片成功
    uploadSuccessEdit(res, file) {
      //console.log(res);
      // res为图片服务器返回的数据
      // 获取富文本组件实例
      let quill = this.$refs.myQuillEditorEdit.quill;
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
      this.quillUpdateImgEdit = false
    },
    //上传限制提示
    handleExceedEdit(files, fileList) {
      this.$message.warning(`当前限制只能选择 10 个文件`);
    },
    // 上传图片失败
    uploadError(res, file) {
      // loading动画消失
      this.quillUpdateImgEdit = false
      this.$message.error(res.msg)
    },
    openDialog(flag, goods,picData) {
      this.activeName='first';
      this.loading.startLoading();
      this.$axios.post(API.GOODS_LIST_API).then(res => {
      this.loading.endLoading();
        if (0 == res.data.code) {
          this.levelData = res.data.data;
          var that=this;
          this.levelData.forEach(function(item){
            if(item.cateName==goods.oneLevel){
              that.secondLevelData=item.secondCategory;
            }
          })
        } else {
          this.$message({
            showClose: true,
            message: res.data.msg,
            type: 'error'
          });
        }
      });
      this.dialogEdit = flag;
      this.$nextTick(function () {
        this.editInfo = goods;
        this.picture=[];
        var that=this;
        picData.forEach(function(item){
          var obj={};
          obj.url=item.picUrl;
          obj.id=item.id;
          that.picture.push(obj);
        })
        this.dataUrl=this.picture;
        //console.log(this.picture);
        //console.log(this.editInfo);
      });
    },
    cancelDialog() {
      this.dialogEdit = false;
      this.quillUpdateImgEdit = false
    },
    //下拉选择二级分类
    getSecondLevel(){
      var that=this;
      this.levelData.forEach(function(item){
        if(item.cateCode==that.editInfo.oneLevel){
          that.editInfo.cateCode="";
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
    editSure() {
      this.$refs['editInfo'].validate((valid) => {
        if (valid) {
          let dataUrl=[];
          this.dataUrl.forEach(function(item){
            if(item.response){
               dataUrl.push(item.response.data);
             
            }else{
              dataUrl.push(item.url);
            }
          });
          if(dataUrl&&dataUrl.length==0){
            this.$message({
              showClose: true,
              message: '列表图片不能为空',
              type: 'error'
            });
            return;
          }
          //console.log(dataUrl);
          this.editInfo.picUrl=dataUrl.join(",");
          if(this.editInfo.status=='下架'){
            this.editInfo.status='0';
          }else if(this.editInfo.status=='上架'){
            this.editInfo.status='1';
          }else if(this.editInfo.status=='未发布'){
            this.editInfo.status='2';
          }

          if(this.editInfo.channelType=='行走书店'){
            this.editInfo.channelType='0';
          }else if(this.editInfo.channelType=='京东万家'){
            this.editInfo.channelType='1';
          }
          this.loading.startLoading();
          //this.editInfo.picUrl=dataUrl.join(",");
          //this.editInfo.picId=picId;
          //删除创建和修改日期 否则会报错
          delete this.editInfo.gmtCreate;
          delete this.editInfo.gmtModified;
          delete this.editInfo.putawayTime;

          this.$axios.post(API.UPDATE_GOODS_API, this.editInfo).then(res => {
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
