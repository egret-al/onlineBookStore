<template>
  <div class="search">
    <common-header :message='pageInfo'></common-header>
    <search-main></search-main>
    <common-footer></common-footer>
  </div>
</template>

<script>
import CommonHeader from '@/components/CommonHeader'
import CommonFooter from '@/components/CommonFooter'

import SearchMain from './Component/SearchMain'

export default {
  components: {
    CommonHeader,
    CommonFooter,
    SearchMain
  },
  data() {
    return {
      pageInfo: '搜索'
    }
  },
  methods: {},
  mounted() {},
}
</script>
<style lang='scss' scoped></style>
