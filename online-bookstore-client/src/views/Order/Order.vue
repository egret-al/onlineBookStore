<template>
  <div>
    <common-header :message='pageInfo'></common-header>
    <order-main></order-main>
    <common-footer></common-footer>
  </div>
</template>

<script>
import CommonHeader from '@/components/CommonHeader'
import CommonFooter from '@/components/CommonFooter'

import OrderMain from './Component/OrderMain'

export default {
  components: {
    CommonHeader,
    CommonFooter,
    OrderMain
  },
  data() {
    return {
      pageInfo: '我的订单'
    }
  },
  methods: {},
  mounted() {},
}
</script>
<style lang='scss' scoped>
.order {
  background: wheat;
  width: 100%;
  height: 660px;
}
</style>
