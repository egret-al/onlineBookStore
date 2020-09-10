<template>
  <div class="home">
    <common-header></common-header>
    <banner-list></banner-list>
    <common-footer></common-footer>
  </div>
</template>

<script>
import CommonHeader from '@/components/CommonHeader'
import CommonFooter from '@/components/CommonFooter'
import BannerList from './Component/Banner'

export default {
  components: {
    CommonHeader,
    CommonFooter,
    BannerList
  },
  data() {
    return {}
  },
  methods: {},
  mounted() {},
}
</script>
<style lang='scss' scoped></style>
