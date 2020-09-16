<template>
  <div class="home">
    <common-header></common-header>
    <search></search>
    <banner-list></banner-list>
    <book-list></book-list>
    <common-footer></common-footer>
  </div>
</template>

<script>
import CommonHeader from '@/components/CommonHeader'
import CommonFooter from '@/components/CommonFooter'

import BannerList from './Component/Banner'
import BookList from './Component/BookList'
import Search from './Component/Search'

export default {
  components: {
    CommonHeader,
    CommonFooter,
    BannerList,
    BookList,
    Search
  },
  data() {
    return {}
  },
  methods: {},
  mounted() {},
}
</script>
<style lang='scss' scoped></style>
