<template>
  <div class="main">
    <common-header :message="pageInfo"></common-header>
  </div>
</template>

<script>
import CommonHeader from '@/components/CommonHeader'

export default {
  components: {
    CommonHeader
  },
  data() {
    return {
      pageInfo: '注册'
    }
  },
  methods: {},
  mounted() {},
}
</script>
<style lang='scss' scoped></style>
