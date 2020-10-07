<template>
  <div>
    <div class="head">
      <img class="main_cover" :src="this.book.main_cover">
      <ul>
        <li>图书名称：{{this.book.book_name}}</li>
        <li>上架时间：{{this.book.create_time}}</li>
        <li>作者：{{this.book.author}}</li>
        <li>ISBN：{{this.book.isbn}}</li>
        <li>描述：<p>{{this.book.description}}</p>
        </li>
      </ul>
    </div>
    <div class="picture" v-for="item in this.book.bookResources" :key="item.resource_url">
      <img :src="item.resource_url" />
    </div>
  </div>
</template>

<script>
export default {
  components: {},
  data() {
    return {
      book: {
        author: '',
        bookResources: [],
        book_name: '',
        create_time: '',
        description: '',
        id: -1,
        isbn: '',
        main_cover: '',
        price: -1,
        publisher: '',
        t_id: -1,
      },
    }
  },
  methods: {},
  mounted() {},
  async created() {
    let bookId = this.$route.query.id
    //通过图书id查询图书信息+资源信息
    const result = await this.$http.get(
      `http://127.0.0.1:9527/book-server/api/v1/book/pub/selectBookAndResourceByBookId/${bookId}`,
    )
    this.book = result.data
  },
}
</script>
<style lang='stylus' scoped>
.head
  margin-top 50px
  .main_cover
    width 230px
    height 230px
  ul
    li
      font-size 14px
      text-align left
      margin-top 8px
      padding-left 10px
      padding-right 10px
      p
        line-height 2
        text-indent 2em
.picture
  line-height 0
  img
    width 100%
</style>
