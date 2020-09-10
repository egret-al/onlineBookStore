<template>
  <div class="user-info">
    <common-header :message='pageInfo'></common-header>
    <information></information>
    <common-footer></common-footer>
  </div>
</template>

<script>
import CommonHeader from '@/components/CommonHeader'
import CommonFooter from '@/components/CommonFooter'
import Information from './Component/Information'

export default {
  components: {
    CommonHeader,
    CommonFooter,
    Information
  },
  data() {
    return {
      pageInfo: '我的'
    }
  },
  methods: {},
  mounted() {},
}
</script>
<style lang='scss' scoped></style>
