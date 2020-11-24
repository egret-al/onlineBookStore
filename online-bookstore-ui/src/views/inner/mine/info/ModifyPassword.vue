<template>
  <div class="main">
    <div class="group">
      <div class="des">我的账号：</div>
      <div class="des-value" style="line-height: 40px">{{this.username}}</div>
    </div>
    <div style="margin-top: 14px" class="group">
      <div class="des">旧密码：</div>
      <div class="des-value">
        <cube-input v-model="oldPassword" placeholder="旧密码" :type="type" :autofocus="autofocus" :eye="eye"></cube-input>
      </div>
    </div>
    <div style="margin-top: 14px" class="group">
      <div class="des">新密码：</div>
      <div class="des-value">
        <cube-input v-model="newPassword" placeholder="请输入6到10位的密码" :type="type" :eye="eye"></cube-input>
      </div>
    </div>
    <div style="margin-top: 14px" class="group">
      <div class="des">确认密码：</div>
      <div class="des-value">
        <cube-input v-model="confirmPassword" placeholder="请再次输入新密码" :type="type" :eye="eye"></cube-input>
      </div>
    </div>
    <div class="operation">
      <cube-button @click="save" class="operation-button">修改</cube-button>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";

export default {
  components: {},

  data() {
    return {
      oldPassword: '',
      newPassword: '',
      confirmPassword: '',
      eye: {
        open: false,
        reverse: false
      },
      autofocus: true,
      type: 'password'
    }
  },

  computed: {
    ...mapState({
      username: (state) => state.username,
    }),
  },

  methods: {

    showToast(type, msg) {
      if (type === 1) {
        const toast = this.$createToast({
          txt: msg,
          type: 'correct',
          time: 1000
        })
        toast.show()
      } else if (type === -1) {
        const toast = this.$createToast({
          txt: msg,
          type: 'error',
          time: 1000
        })
        toast.show()
      }
    },

    async save() {
      if (this.oldPassword === '' || this.newPassword === '' || this.confirmPassword === '') {
        this.showToast(-1, '信息不能为空！')
        return
      }
      if (this.newPassword.length < 6 || this.newPassword.length > 10) {
        this.showToast(-1, '请输入6到10位的密码！')
        return
      }
      if (this.newPassword === this.confirmPassword) {
        const res = await this.$http.post('/user-server/api/v1/account/pri/modifyPassword', {
          'username': this.username,
          'oldPassword': this.oldPassword,
          'newPassword': this.newPassword
        })
        if (res.code === 1) {
          this.$router.go(-1)
        } else {
          this.showToast(-1, res.message)
        }
      } else {
        this.showToast(-1, '两次输入密码不一致！')
      }
    }
  },

  mounted() {},
}
</script>
<style lang='stylus' scoped>
.main
  margin-top 50px
  .group 
    height 40px
    font-size 13px
    display flex
    padding-left 20px
    .des
      text-align right 
      width 20%
      line-height 40px
    .des-value
      width 70%
      text-align left 
    .cube-input
      width 90%
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
