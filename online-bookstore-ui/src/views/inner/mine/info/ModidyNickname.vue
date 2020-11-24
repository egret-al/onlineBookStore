<template>
  <div class="main">
    <div class="content">
      <span style="margin-top: 6px">我的昵称：</span><cube-input placeholder="请输入昵称" v-model="nickname" ></cube-input>
    </div>
    <div class="operation">
      <cube-button @click="save" class="operation-button">保存</cube-button>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";

export default {
  components: {},

  data() {
    return {
      nickname: '',
    }
  },

  computed: {
    ...mapState({
      username: (state) => state.username,
    }),
  },

  methods: {
    async save() {
      if (this.nickname === '') {
        const toast = this.$createToast({
          txt: '请输入昵称！',
          type: 'error',
          time: 1000
        })
        toast.show()
      } else {
        const res = await this.$http.post('/user-server/api/v1/user/pri/modifyNickname', {
          'account_username': this.username,
          'nickname': this.nickname
        })
        if (res.code === 1) {
          this.$router.go(-1)
        } else {
          const toast = this.$createToast({
            txt: res.message,
            type: 'error',
            time: 1000
          })
          toast.show()
        }
      }
    }
  },

  mounted() {},
}
</script>
<style lang='stylus' scoped>
.main
  margin-top 50px
  .content 
    font-size 13px
    display flex
    padding-left 20px
    .cube-input
      width 60%
      height 30px
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
