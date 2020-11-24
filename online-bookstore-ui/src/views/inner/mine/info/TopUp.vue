<template>
  <div class="main">
    <div class="content">
      <span style="margin-top: 14px">充值账号：{{this.username}}</span>
    </div>
    <div class="content">
      <span style="margin-top: 14px">账号余额：{{this.balance}}</span>
    </div>
    <div class="content">
      <span style="margin-top: 14px">充值金额：</span>
      <cube-select v-model="count" :options="options"></cube-select>
    </div>
    <div class="operation">
      <cube-button @click="save" class="operation-button">充值</cube-button>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";

export default {
  components: {},

  data() {
    return {
      count: 6,
      balance: -1,
      options: [6, 10, 50, 100, 500, 1000]
    }
  },

  computed: {
    ...mapState({
      username: (state) => state.username,
    }),
  },

  methods: {
    async save() {
      if (this.count > 0) {
        const res = await this.$http.post('/user-server/api/v1/account/pri/topUpResidue', {
          'username': this.username,
          'count': this.count
        })
        if (res.code === 1) {
          this.balance += this.count
          const toast = this.$createToast({
            txt: '充值成功！',
            type: 'correct',
            time: 1000
          })
          toast.show()
        }
      }
    }
  },

  async created() {
    const res = await this.$http.get(`/user-server/api/v1/account/pub/getAccountWithUser/${this.username}`)
    if (res.code === 1) {
      this.balance = res.data.balance
    }
  },

  mounted() {},
}
</script>
<style lang='stylus' scoped>
.main
  margin-top 50px
  .content 
    height 40px
    font-size 13px
    display flex
    padding-left 20px
    .cube-input
      width 60%
      height 40px
  .operation
    margin-top 20px
    display flex
    justify-content center
    .operation-button
      width 30%
      font-size 13px
      line-height 0px
      height 40px
      background-color #fff
      color #26a2ff
      border 1px solid #26a2ff
</style>
