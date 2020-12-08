<template>
  <div class="main">
    <div class="content">
      <div class="left">
        <span style="margin-top: 6px">手机号码：</span>
      </div>
      <div class="right">{{this.user.phone}}</div>
    </div>
    <div class="content">
      <div class="left">
        <span style="margin-top: 6px">验证码：</span>
      </div>
      <div class="right">
        <cube-input style="width: 100px; float: left; margin-right: 10px" placeholder="验证码" v-model="code"></cube-input>
        <cube-button v-if="show" class="send-button" @click="sendCode">发送</cube-button>
        <cube-button style="background-color: gray" v-if="!show" class="send-button">{{countdown}}s</cube-button>
      </div>
    </div>
    <div class="content">
      <div class="left">
        <span style="margin-top: 6px">新号码：</span>
      </div>
      <div class="right">
        <cube-input placeholder="请输入手机号码" v-model="phone" ></cube-input>
      </div>
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
      user: {},
      phone: '',
      code: '',
      show: true,
      getCode: false,
      countdown: 60
    }
  },

  computed: {
    ...mapState({
      username: (state) => state.username,
    }),
  },

  methods: {
    async sendCode() {
      const sendRes = await this.$http.post('/user-server/api/v1/user/pri/modifyPhoneSendCode', {
        'account_username': this.username,
        'phone': this.user.phone
      })
      if (sendRes.code === 1) {
        this.showToast('correct', sendRes.message, 1000)
        this.getCode = true
        this.show = false
        this.timer = setInterval(() => {
          this.countdown--
          if (this.countdown === 0) {
            this.show = true
            this.countdown = 60
            clearInterval(this.countdown)
          }
        }, 1000)
      } else {
        this.showToast('error', sendRes.message, 1000)
      }
    },

    validatePhone(phone) {
      var myreg=/^[1][3,4,5,7,8][0-9]{9}$/
      if (!myreg.test(phone)) {
        return false;
      } else {
        return true;
      }
    },

    async save() {
      if (!this.getCode) {
        this.showToast('warn', '请先获取验证码！', 1000)
        return
      }
      if (this.code === '') {
        this.showToast('warn', '请输入验证码！', 1000)
        return
      }
      if (!this.validatePhone(this.phone)) {
        this.showToast('error', '手机号码格式错误！', 1000)
      } else {
        const res = await this.$http.post('/user-server/api/v1/user/pri/modifyPhone', {
          'user': {
            'account_username': this.username,
            'phone': this.phone
          },
          'code': this.code
        })
        if (res.code === 1) {
          this.$router.go(-1)
        } else {
          this.showToast('error', res.message, 1000)
        }
      }
    },

    showToast(type, message, time) {
      const toast = this.$createToast({
        txt: message,
        type: type,
        time: time
      })
      toast.show()
    }
  },

  async created() {
    const userRes = await this.$http.post('/user-server/api/v1/user/pri/getUserInfo', { 'username': this.username })
    console.log(userRes)
    if (userRes.code === 1) this.user = userRes.data
  }
}
</script>
<style lang='stylus' scoped>
.main
  margin-top 50px
  .content 
    font-size 13px
    display flex
    padding-left 20px
    height 40px
    line-height 40px
    .left
      width 30%
    .right
      width 70%
      text-align left
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
.send-button
  width 50px
  height 30px
  margin-left 10px
  font-size 12px
  text-align center
  padding 0px
  margin-top 3px
  background-color #26A2FF
</style>
