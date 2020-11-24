<template>
  <div class="main">
    <div class="content">
      <span style="margin-top: 6px">我的手机：</span><cube-input placeholder="请输入手机号码" v-model="phone" ></cube-input>
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
      phone: '',
    }
  },

  computed: {
    ...mapState({
      username: (state) => state.username,
    }),
  },

  methods: {
    validatePhone(phone) {
      var myreg=/^[1][3,4,5,7,8][0-9]{9}$/
      if (!myreg.test(phone)) {
        return false;
      } else {
        return true;
      }
    },

    async save() {
      if (!this.validatePhone(this.phone)) {
        const toast = this.$createToast({
          txt: '手机号码格式错误！',
          type: 'error',
          time: 1000
        })
        toast.show()
      } else {
        const res = await this.$http.post('/user-server/api/v1/user/pri/modifyPhone', {
          'account_username': this.username,
          'phone': this.phone
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
