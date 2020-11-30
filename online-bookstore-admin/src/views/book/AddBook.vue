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
              <el-select placeholder="请选择图书类型" v-model="bookForm.type" prop="book_type">
                <el-option v-for="item in typeList" :key="item.id" :label="item.type" :value="item.id"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="封面cdn" prop="main_cover">
              <el-input v-model="bookForm.main_cover"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="内容描述" prop="description">
          <el-input type="textarea" v-model="bookForm.description"></el-input>
        </el-form-item>
      </el-card>
      <el-card class="box-card">
        <el-row>
          <el-col :span="16">
            <el-form-item label="图片资源" prop="imgResourcesString">
              <el-input v-model="bookForm.imgResourcesString" placeholder="多个CDN地址以英文逗号分隔，添加顺序将影响显示顺序"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="5">
            <el-form-item label="库存" prop="residue">
              <el-input v-model.number="bookForm.residue" placeholder="只能输入大于0的正整数"></el-input>
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
        type: '',
        description: '',
        
        imgResourcesString: '',

        bookResources: [],
        residue: 1,
        
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
          { pattern: /^([1-9]\d?|1000)$/, message: '价格不能为负数', trigger: 'blur'}
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
        residue: [
          { required: true, message: '价格不能为空', trigger: "blur"},
          { type: 'number', message: '数据非法', trigger: "blur" },
          { pattern: /^([1-9]\d?|99999)$/, message: '库存不能为负数', trigger: 'blur'}
        ]
      }
    };
  },
  
  async created() {
    const resType = await this.$http.get("/book-server/api/v1/book/pub/selectAllType")
    this.typeList = resType.data
    console.log(this.typeList)
  },

  methods: {
    submitForm(bookForm) {
      if (this.bookForm.imgResourcesString) {
        let res = this.bookForm.imgResourcesString.split(',')
        
      }
      console.log(this.bookForm)
      this.$refs[bookForm].validate((valid) => {
        if (valid) {
          //验证合法，提交数据到后台
        } else {
          console.log('error submit!!');
          this.$message({
            showClose: true,
            message: '数据不合法，提交失败！',
            type: 'error'
          });
          return false;
        }
      });
    },
    
    resetForm(bookForm) {
      this.$refs[bookForm].resetFields();
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
</style>
