<template>
  <div class="user-info">
    <common-header :message='pageInfo'></common-header>
    <user-information></user-information>
    <common-footer></common-footer>
  </div>
</template>

<script>
import CommonHeader from '@/components/CommonHeader'
import CommonFooter from '@/components/CommonFooter'
import UserInformation from './Component/UserInformation'


export default {
  components: {
    CommonHeader,
    CommonFooter,
    UserInformation
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
