<template>
  <div class="root">
    <el-form :model="bookForm" :rules="rules" ref="bookForm" label-width="100px" class="demo-ruleForm">
      <el-card class="box-card">
        <el-row>
          <el-col :span="12">
            <el-form-item label="图书名称" prop="book_name">
              <el-input v-model="bookForm.book_name"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="ISBN" prop="isbn">
              <el-input v-model="bookForm.isbn"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="作者" prop="author">
              <el-input v-model="bookForm.author"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="出版社" prop="publisher">
              <el-input v-model="bookForm.publisher"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="5">
            <el-form-item label="价格" prop="price">
              <el-input v-model.number="bookForm.price"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="7">
            <el-form-item label="类型">
              <el-select placeholder="请选择图书类型" v-model="bookForm.t_id" prop="book_type">
                <el-option v-for="item in typeList" :key="item.id" :label="item.type" :value="item.id"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col class="main-cover" :span="24">
            <el-col :span="4">
              <div class="main-cover-label">封面</div>
            </el-col>
            <el-col :span="20">
              <el-upload class="avatar-uploader" action="http://localhost:9527/book-server/api/v1/file/pri/upload" :show-file-list="false"
                :on-success="handleMainCoverSuccess" :before-upload="beforeMainCoverUpload" :headers="getToken()">
                <img v-if="bookForm.main_cover" :src="bookForm.main_cover" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
            </el-col>
          </el-col>
        </el-row>
        <el-form-item style="margin-top: 20px" label="内容描述" prop="description">
          <el-input :autosize="{ minRows: 1, maxRows: 20}" type="textarea" v-model="bookForm.description"></el-input>
        </el-form-item>
      </el-card>
      <el-card class="box-card">
        <el-row class="show-picture-list">
          <p>详细图片</p>
          <el-upload action="http://localhost:9527/book-server/api/v1/file/pri/upload" :on-success="handleImgsSuccess" list-type="picture-card" 
            :headers="getToken()" :auto-upload="true">
            <i slot="default" class="el-icon-plus"></i>
            <div slot="file" slot-scope="{file}">
              <img class="el-upload-list__item-thumbnail" :src="file.url" alt="">
              <span class="el-upload-list__item-actions">
                <span class="el-upload-list__item-preview" @click="handlePictureCardPreview(file)">
                  <i class="el-icon-zoom-in"></i>
                </span>
                <span v-if="!disabled" class="el-upload-list__item-delete" @click="handleDownload(file)">
                  <i class="el-icon-download"></i>
                </span>
                <span v-if="!disabled" class="el-upload-list__item-delete" @click="handleRemove(file)">
                  <i class="el-icon-delete"></i>
                </span>
              </span>
            </div>
          </el-upload>
          <el-dialog :visible.sync="dialogVisible">
            <img width="100%" :src="dialogImageUrl" alt="">
          </el-dialog>
        </el-row>
        <el-row style="margin-top: 20px">
          <el-col :span="5">
            <el-form-item label="库存" prop="residue_count">
              <el-input v-model.number="bookForm.residue_count" placeholder="只能输入大于0的正整数"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-card>
      <el-form-item>
        <el-button type="primary" @click="submitForm('bookForm')">立即创建</el-button>
        <el-button @click="resetForm('bookForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      bookForm: {
        book_name: '',
        isbn: '',
        author: '',
        publisher: '',
        price: 0,
        main_cover: '',
        t_id: 1,
        description: '',

        bookResources: [],
        bookStorage: {
          residue_count: 1
        },
        residue_count: 1
      },
      typeList: [],
      rules: {
        book_name: [
          { required: true, message: "请输入图书名称", trigger: "blur" },
        ],
        isbn: [
          { required: true, message: "请输入ISBN", trigger: "blur" },
          { min: 13, max: 13, message: "ISBN错误", trigger: 'blur' }
        ],
        author: [
          { required: true, message: "请输入作者名字", trigger: "blur" },
        ],
        publisher: [
          { required: true, message: "请输入出版社名称", trigger: "blur" },
        ],
        price: [
          { required: true, message: '价格不能为空', trigger: "blur"},
          { type: 'number', message: '数据非法', trigger: "blur" },
          { pattern: /(^[1-9]\d*$)/, message: '价格不能为负数', trigger: 'blur'}
        ],
        main_cover: [
          { required: true, message: "路径不能为空", trigger: "blur" }
        ],
        type: [
          { required: true, message: "请填写活动形式", trigger: "blur" }
        ],
        description: [
          { required: true, message: "描述不能为空", trigger: "blur" }
        ],
        book_type: [
          { required: true, message: "类型不能为空", trigger: "blur" }
        ],
        residue_count: [
          { required: true, message: '库存不能为非法值', trigger: "blur"},
          { type: 'number', message: '数据非法', trigger: "blur" },
          { pattern: /(^[1-9]\d*$)/, message: '库存不能为负数', trigger: 'blur'}
        ]
      },

      mainUrl: '',
      dialogImageUrl: '',
      dialogVisible: false,
      disabled: false,
      imagesList: [],
      orderImagesList: 1
    };
  },
  
  async created() {
    const resType = await this.$http.get("/book-server/api/v1/book/pub/selectAllType")
    this.typeList = resType.data
  },

  methods: {
    handleImgsSuccess(res, file) {
      this.imagesList.push(res.data)
      this.bookForm.bookResources.push({
        symbol: 'detail' + this.orderImagesList,
        resource_url: res.data
      })
      this.orderImagesList++
    },

    handleRemove(file) {
      console.log('handleRemove')
      console.log(file)
    },

    handlePictureCardPreview(file) {
      console.log('handlePictureCardPreview')
      this.dialogImageUrl = file.url
      this.dialogVisible = true
    },

    handleDownload(file) {
      console.log('handleDownload')
      console.log(file)
    },

    getToken() {
      let data = { 'token': localStorage.getItem('token') }
      return data
    },

    //main_cover成功后的回调
    handleMainCoverSuccess(res, file) {
      console.log(res)
      console.log(file)
      //this.imageUrl = URL.createObjectURL(file.raw);
      this.bookForm.main_cover = res.data
    },

    //main_cover校验
    beforeMainCoverUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传封面只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传封面大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    },

    async submitForm(bookForm) {
      let isPass = true
      console.log(this.bookForm)
      this.$refs[bookForm].validate((valid) => {
        console.log(valid)
        if (valid) {
          isPass = true
        } else {
          isPass = false
          this.$message({
            showClose: true,
            message: '数据不合法，提交失败！',
            type: 'error'
          });
        }
      });
      if (!isPass) return false
      if (this.bookForm.main_cover == '') {
        this.$message({
          showClose: true,
          message: '请提交封面！',
          type: 'error'
        })
        return
      }
      console.log('数据合法')
      this.bookForm.bookStorage.residue_count = this.bookForm.residue_count
      const addRes = await this.$http.post('/book-server/api/v1/book/pri/addBook', this.bookForm)
      if (addRes.code === 1) {
        this.$message({
          showClose: true,
          message: '添加成功！',
          type: 'success'
        })
        this.$router.go(0)
      } else {
        this.$message({
          showClose: true,
          message: addRes.message,
          type: 'error'
        })
      }
    },
    
    resetForm(bookForm) {
      this.$refs[bookForm].resetFields()
    },

    openError() {
      
    }
  }
};
</script>

<style lang="scss" scoped>
.el-card {
  margin-bottom: 10px;
}
.show-picture-list {
  margin-left: 60px;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.main-cover-label {
  padding-left: 60px;
  margin-top: 30%;
  font-weight: bold;
  font-size: 14px;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
  border: gray dashed 1px;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
