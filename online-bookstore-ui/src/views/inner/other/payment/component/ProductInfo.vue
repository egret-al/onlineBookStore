<template>
  <div class="main">
    <div class="picture">
      <img class="cover" :src='this.book.main_cover'/>
    </div>
    <div class="info">
      <ul>
        <li>书名：{{this.book.book_name}}</li>
        <li>作者：{{this.book.author}}</li>
        <li>上架时间：{{this.book.create_time}}</li>
        <li>ISBN：{{this.book.isbn}}</li>
        <li>出版社：{{this.book.publisher}}</li>
        <li>价格：{{this.book.price}}￥</li>
    </ul>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    book: {
      type: Object,
      required: true
    }
  },
  components: {},
  data() {
    return {}
  },
  methods: {},
  mounted() {},
}
</script>
<style lang='stylus' scoped>
.main
  margin-left 5%
  padding-left 20px
  display flex
  box-shadow 0 4px 12px 0 rgba(43, 51, 59, 0.6)
  border-radius 5px
  .picture
    border 1px solid #C3C5C8
    .cover
      margin-top 10px
      width 100px
      height 100px
  .info
    width 100%
    ul
      margin-top 10px
      li
        font-size 12px
        color grey
        text-align left 
        padding-left 10%
        margin-top 5px
</style>
