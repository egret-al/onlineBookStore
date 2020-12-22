<template>
  <div class="forgot-main">
    <ul>
      <li>
        <div class="label">账号：</div>
        <div class="input-content">
          <cube-input v-model="account.username" ></cube-input>
        </div>
      </li>
      <li>
        <div class="label">新密码：</div>
        <div class="input-content">
          <cube-input :placeholder="placeholder" :type="passwordType" :maxlength="maxlength" :eye="eye" v-model="account.password" ></cube-input>
        </div>
      </li>
      <li>
        <div class="label">确认密码：</div>
        <div class="input-content">
          <cube-input :placeholder="placeholder" :type="passwordType" :maxlength="maxlength" :eye="eye" v-model="confirmPassword" ></cube-input>
        </div>
      </li>
      <li>
        <div class="label">手机号码：</div>
        <div class="input-content">
          <cube-input v-model="phone" ></cube-input>
        </div>
        <div class="send-box">
          <cube-button v-if="show" class="send-button" @click="sendCode">发送</cube-button>
          <cube-button style="background-color: gray" v-if="!show" class="send-button">{{times}}s</cube-button>
        </div>
      </li>
      <li>
        <div class="label">短信验证码：</div>
        <div class="input-content">
          <cube-input v-model="code" ></cube-input>
        </div>
      </li>
      <p>
        <cube-button class="reset-button" @click="resetPassword">重置密码</cube-button>
      </p>
    </ul>
  </div>
</template>

<script>
import { isPhoneNumber } from '@/util/verifyUtils.js'

export default {
  data() {
    return {
      account: {
        username: '',
        password: ''
      },
      confirmPassword: '',
      phone: '',
      code: '',

      getCode: false,

      show: true,
      times: 60,

      placeholder: '请输入内容',
      passwordType: 'password',
      maxlength: 10,
      autofocus: true,
      eye: {
        open: false,
        reverse: false
      }
    }
  },
  
  methods: {
    async sendCode() {
      if (this.account.username == '' || this.phone == '') {
        this.showToast('warn', '请输入账号和绑定的手机号！', 1000)
        return
      }
      if (!isPhoneNumber(this.phone)) {
        this.showToast('warn', '请输入正确的手机号码！', 1000)
        return
      }
      const sendRes = await this.$http.post('/user-server/api/v1/account/pub/forgotPassword', {
        'account': this.account,
        'phone': this.phone
      })
      if (sendRes.code === 1) {
        this.showToast('correct', sendRes.message, 1000)
        this.show = false
        this.getCode = true
        this.timer = setInterval(() => {
          this.times--
          if (this.times === 0) {
            this.show = true
            this.times = 60
            clearInterval(this.timer)
          }
        }, 1000)
      } else {
        this.showToast('error', sendRes.message, 1000)
      }
    },

    async resetPassword() {
      if (!this.getCode) {
        this.showToast('warn', '请先获取验证码！', 1000)
        return
      }
      if (this.account.username == '' || this.account.password == '' || this.code == '') {
        this.showToast('warn', '请确保信息完善！', 1000)
        return
      }
      if (this.account.password != this.confirmPassword) {
        this.showToast('warn', '两次输入密码不一致！', 1000)
        return
      }
      if (!isPhoneNumber(this.phone)) {
        this.showToast('warn', '请输入正确的手机号码！', 1000)
        return
      }
      const resetRes = await this.$http.post('/user-server/api/v1/account/pub/resetPassword', {
        'account': this.account,
        'code': this.code
      })
      if (resetRes.code === 1) {
        this.showToast('correct', resetRes.message, 1000)
        this.$router.go(-1)
      } else {
        this.showToast('error', resetRes.message, 1000)
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
  }
}
</script>
<style lang='stylus' scoped>
.forgot-main
  margin-top 40px
  width 100%
  li
    border-bottom 1px solid gray
    margin-top 5px
    margin-bottom 5px
    display flex
    .label
      width 25%
      line-height 40px
      font-size 14px
    .input-content
      width 55%
    .send-button
      width 50px
      height 30px
      margin-left 10px
      font-size 12px
      text-align center
      padding 0px
      margin-top 3px
      background-color #26A2FF
  .reset-button
    height 40px
    width 35%
    padding 0px
    margin-left 30%
    margin-top 30px
    background-color #26A2FF
</style>
