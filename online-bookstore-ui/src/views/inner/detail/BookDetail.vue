<template>
  <div class="main">
    <book-detail-picture :cover="cover"></book-detail-picture>
    <book-detail-info :bookData="bookData"></book-detail-info>
  </div>
</template>

<script>
import BookDetailPicture from './component/BookDetailPicture'
import BookDetailInfo from './component/BookDetailInfo'

export default {
  components: {
    BookDetailInfo,
    BookDetailPicture,
  },
  data() {
    return {
      cover: '',
      bookData: {
        author: '',
        bookResources: {},
        bookStorage: {
          book_id: -1,
          id: -1,
          last_add_time: '1999-01-01 00:00:00',
          residue_count: -1,
        },
        book_name: '',
        create_time: '1999-01-01 00:00:00',
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
    //获取到图书id，发起查询请求
    let bookId = this.$route.query.id
    const result = await this.$http.get(
      `http://localhost:9527/book-server/api/v1/book/pub/selectBookAndStorageByBookId/${bookId}`,
    )
    this.bookData = result.data
    this.cover = result.data.main_cover
  },
}
</script>
<style lang='stylus' scoped>
.main
  padding-top 10px
</style>
