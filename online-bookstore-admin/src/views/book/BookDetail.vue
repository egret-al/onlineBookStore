<template>
  <div class="main">
    <el-row>
      <el-col :span="8">
        <img class="main-cover" :src="this.book.main_cover" />
      </el-col>
      <el-col :span="16">
        <ul>
          <li>图书名称：《{{ this.book.book_name }}》</li>
          <li>ISBN：{{ this.book.isbn }}</li>
          <li>定价：{{ this.book.price }}</li>
          <li>上架时间：{{ this.book.create_time }}</li>
          <li>出版社：{{ this.book.publisher }}</li>
          <li>描述：{{ this.book.description }}</li>
        </ul>
      </el-col>
    </el-row>
    <div class="down">
      <el-tabs type="border-card">
        <el-tab-pane label="图片"> </el-tab-pane>
        <el-tab-pane label="评论">评论</el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script>
export default {
  components: {},
  data() {
    return {
      book: {}
    };
  },
  methods: {},
  mounted() {},
  async created() {
    let result = await this.$http.get(
      `/book-server/api/v1/book/pub/selectBookContainAllInfoById/${
        this.$route.query.id
      }`
    );
    this.book = result.data;
    console.log(result);
  }
};
</script>
<style lang="scss" scoped>
.main {
  padding-top: 40px;
  padding-left: 20px;
  padding-right: 30px;
  .main-cover {
    margin-left: 100px;
    width: 300px;
    height: 300px;
  }
  .down {
    margin-left: 5%;
    margin-right: 5%;
  }
}
li {
  margin-right: 100px;
  list-style: none;
  color: #999;
  font-size: 12px;
  line-height: 25px;
}
</style>
