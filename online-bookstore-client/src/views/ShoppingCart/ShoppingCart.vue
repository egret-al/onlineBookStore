<template>
  <div>
    <common-header :message='pageInfo'></common-header>
    <shopping-cart-main></shopping-cart-main>
    <common-footer></common-footer>
  </div>
</template>

<script>
import CommonHeader from '@/components/CommonHeader'
import CommonFooter from '@/components/CommonFooter'

import ShoppingCartMain from './Component/ShoppingCartMain'

export default {
  components: {
    CommonHeader,
    CommonFooter,
    ShoppingCartMain
  },
  data() {
    return {
      pageInfo: '购物车'
    }
  },
  methods: {},
  mounted() {},
}
</script>
<style lang='scss' scoped></style>
