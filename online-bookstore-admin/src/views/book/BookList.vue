<template>
  <div class="root">
    <!--轮播图-->
    <!-- <book-banner :bannerItems="bannerItems"></book-banner> -->
    <el-input v-model="searchValue" placeholder="请输入图书名称"></el-input>
    <!--图书列表-->
    <book-list-item :bookList="bookList"></book-list-item>
  </div>
</template>

<script>
import BookBanner from "./components/BookBanner";
import BookListItem from "./components/BookListItem";

export default {
  components: {
    BookBanner,
    BookListItem
  },

  data() {
    return {
      bannerItems: [],
      bookList: [],
      bookListBK: [],
      searchValue: ''
    }
  },

  methods: {
    isContains(str, substr) {
      return str.indexOf(substr) >= 0
    }
  },
  
  async created() {
    const bookBannerList = await this.$http.get('/book-server/api/v1/book/pub/selectAllBanner')
    this.bannerItems = bookBannerList.data
    const bookListResult = await this.$http.get('/book-server/api/v1/book/pub/selectAllBookAndStorage')
    this.bookList = bookListResult.data
    this.bookList.forEach(v => this.bookListBK.push(v))
  },

  watch: {
    'searchValue': {
      handler(newValue, oldValue) {
        this.bookList = []
        if (newValue === '') {
          this.bookListBK.forEach(v => this.bookList.push(v))
        } else {
          this.bookListBK.forEach(v => {
            if (this.isContains(v.book_name, newValue)) {
              this.bookList.push(v)
            }
          })
        }
        console.log('newValue', newValue)
        console.log('oldValue', oldValue)
      }
    }
  }
}
</script>

<style></style>
