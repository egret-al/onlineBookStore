<template>
  <div class="main">
    <cube-form :model="model" @submit="submitHandler">
      <cube-form-group>
        <!--账号-->
        <cube-form-item :field="fields[0]"></cube-form-item>
        <!--密码-->
        <cube-form-item :field="fields[1]"></cube-form-item>
      </cube-form-group>

      <cube-form-group>
        <cube-button type="submit">登录</cube-button>
      </cube-form-group>
    </cube-form>
    <router-link to="/registry" class="reg">注册</router-link>
  </div>
</template>

<script>
//按需导入
import { loginApi } from '@/api/user/userRequest.js'

export default {
  components: {},
  data() {
    return {
      model: {
        username: '',
        password: ''
      },
      fields: [
        //校验规则
        {
          type: 'input',
          modelKey: 'username',
          label: '账号',
          props: {
            placeholder: '请输入登录账号'
          },
          rules: {
            required: true
          },
          messages: {
            required: '账号不能为空！'
          }
        },
        {
          type: 'input',
          modelKey: 'password',
          label: '密码',
          props: {
            placeholder: '请输入登录密码',
            type: 'password',
            eye: {
              open: false
            }
          },
          rules: {
            required: true
          },
          messages: {
            required: '密码不能为空！'
          }
        }
      ]
    }
  },
  methods: {
    submitHandler(e, model) {
      e.preventDefault()
      //发起登录请求
      loginApi(model.username, model.password).then(
        res => {
          let resData = res.data
          if (resData.code === 1) {
            //后端约定状态码为1则都为请求成功，否则失败
            let token = resData.data.token
            //token存入localStorage和vuex
            localStorage.setItem('token', token)
            this.$store.dispatch('setToken', token)

            //登录成功，跳转到首页  
            this.$router.push({ path: '/' })
          } else {
            //登录失败，提示用户信息
            const toast = this.$createToast({
              txt: resData.message,
              type: 'error',
              time: 1500
            })
            toast.show()
          }
        }
      )
      console.log(e, model)
    }
  },
  mounted() {},
}
</script>
<style lang='scss' scoped>
.main {
  padding: 70% 5% 0;
  text-align: center;
}
//登录
.cube-btn {
  margin-top: 20px;
}
//注册
.reg {
  display: inline-block;
  margin-top: 30px;
  font-size: 18px;
}
</style>
