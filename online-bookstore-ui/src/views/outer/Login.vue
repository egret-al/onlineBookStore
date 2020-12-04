<template>
  <div class="main">
    <div class="hint">
      <cube-loading v-if="isLoading" :size="40"></cube-loading>
    </div>
    <cube-form class="loginInfo" :model="model" @submit="submitHandler">
      <cube-form-group>
        <!--账号-->
        <cube-form-item :field="fields[0]"></cube-form-item>
        <!--密码-->
        <cube-form-item :field="fields[1]"></cube-form-item>
      </cube-form-group>
      <router-link :to="{path: '/forgot'}">
        <p class="forgot">忘记密码</p>
      </router-link>
      <cube-form-group>
        <cube-button type="submit">登录</cube-button>
      </cube-form-group>
    </cube-form>
    <router-link to="/registry" class="reg">注册</router-link>
  </div>
</template>

<script>
export default {
  components: {},
  data() {
    return {
      isLoading: false, //默认是不加载的状态
      model: {
        username: '',
        password: '',
      },
      fields: [
        //校验规则
        {
          type: 'input',
          modelKey: 'username',
          label: '账号',
          props: {
            placeholder: '请输入登录账号',
          },
          rules: {
            required: true,
          },
          messages: {
            required: '账号不能为空！',
          },
        },
        {
          type: 'input',
          modelKey: 'password',
          label: '密码',
          props: {
            placeholder: '请输入登录密码',
            type: 'password',
            eye: {
              open: false,
            },
          },
          rules: {
            required: true,
          },
          messages: {
            required: '密码不能为空！',
          },
        },
      ],
    }
  },
  methods: {
    async submitHandler(e, model) {
      e.preventDefault()
      this.isLoading = true
      try {
        const result = await this.$http.post(
          '/user-server/api/v1/account/pub/login',
          {
            username: this.model.username,
            password: this.model.password,
          },
        )
        if (result.code === 1) {
          this.$store.commit('setToken', result.data.token)
          window.localStorage.setItem('token', result.data.token)
          this.$store.commit('setUsername', result.data.account.username)
          window.localStorage.setItem('username', result.data.account.username)
          //恢复未加载状态
          this.isLoading = false
          //判断路由是否带参，如果有参，则去参数地址，否则跳转首页
          if (this.$route.query.redirect) {
            this.$router.replace({path: this.$route.query.redirect})
          } else {
            this.$router.replace({path: '/footer/index'})
          }
        } else {
          this.isLoading = false
          //登录失败，提示用户信息
          const toast = this.$createToast({
            txt: result.message,
            type: 'error',
            time: 1500,
          })
          toast.show()
        }
      } catch (err) {
        console.log(err)
      }
    },
  },
  mounted() {},
}
</script>
<style lang="stylus" scoped>
.main
  text-align center
  .hint
    width 100%
    margin-top 300px
    padding-left 45%
    position fixed
    z-index 999
  .loginInfo
    padding 70% 5% 0
    .forgot
      margin-top 30px
      font-size 12px
  .cube-btn
    background-color #489fe2
    margin-top 20px
  .reg
    display inline-block
    margin-top 30px
    font-size 14px
    color red
</style>
