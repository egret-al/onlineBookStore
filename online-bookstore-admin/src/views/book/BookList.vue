<template>
  <div class="root">
    <!--轮播图-->
    <!-- <book-banner :bannerItems="bannerItems"></book-banner> -->
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
      bookList: []
    };
  },
  async created() {
    const bookBannerList = await this.$http.get(
      "/book-server/api/v1/book/pub/selectAllBanner"
    );
    this.bannerItems = bookBannerList.data;
    const bookListResult = await this.$http.get(
      "/book-server/api/v1/book/pub/selectAllBookAndStorage"
    );
    this.bookList = bookListResult.data;
  },
  methods: {}
};
</script>

<style></style>
