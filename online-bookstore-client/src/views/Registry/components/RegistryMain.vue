<template>
  <div class="main">
    <cube-form :model="model" @submit="submitHandler">
      <div class="accountInfo">账户信息</div>
      <cube-form-group>
        <cube-form-item :field="fields[0]"></cube-form-item>
        <cube-form-item :field="fields[1]"></cube-form-item>
      </cube-form-group>
      <div class="userInfo">用户信息</div>
      <cube-form-group>
        <cube-form-item :field="fields[2]"></cube-form-item>
        <cube-form-item :field="fields[3]"></cube-form-item>
        <cube-form-item :field="fields[4]"></cube-form-item>
        <cube-form-item :field="fields[5]" class="date">
          <cube-button class="btn" @click="showDatePicker">{{model.birthdayValue || '请选择出生日期'}}</cube-button>
        </cube-form-item>
      </cube-form-group>
      <cube-form-group class="btn-group">
        <cube-button class="reg" type="submit">注册</cube-button>
        <cube-button class="toLogin" @click="toLogin">去登录</cube-button>
      </cube-form-group>
    </cube-form>
  </div>
</template>

<script>
import { registryApi } from '@/api/user/userRequest.js'

export default {
  components: {},
  data() {
    return {
      model: {
        accountValue: '',
        passwordValue: '',
        nickNameValue: '',
        sexValue: '',
        birthdayValue: '',
        phoneValue: ''
      },
      fields: [
        {
          type: 'input',
          modelKey: 'accountValue',
          label: '账号',
          props: {
            placeholder: '请输入登录账号'
          },
          rules: {
            required: true,
            len: 10
          },
          messages: {
            required: '账号不能为空！'
          }
        },
        {
          type: 'input',
          modelKey: 'passwordValue',
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
        },
        {
          type: 'input',
          modelKey: 'nickNameValue',
          label: '昵称',
          props: {
            placeholder: '禁止非法昵称'
          },
          rules: {
            required: true,
          },
          messages: {
            required: '昵称不能为空'
          }
        },
        {
          type: 'radio-group',
          modelKey: 'sexValue',
          label: '性别',
          props: {
            horizontal: true,
            options: ['男', '女']
          },
          rules: {
            required: true
          }
        },
        {
          type: 'input',
          modelKey: 'phoneValue',
          label: '手机号码',
          props: {
            placeholder: '请输入手机号码'
          },
          rules: {
            type: 'tel',
            required: true,
            len: 11
          },
          messages: {
            required: '手机号码不能为空！'
          }
        },
        {
          modelKey: 'birthdayValue',
          label: '出生日期',
          rules: {
            required: true
          }
        },
      ],
      options: {
        scrollToInvalidField: true,
        layout: 'standard' // classic fresh
      }
    }
  },
  methods: {
    //展示日期选择器
    showDatePicker() {
      if (!this.datePicker) {
        this.datePicker = this.$createDatePicker({
          title: '出生日期',
          min: new Date(1990, 1, 1),
          max: new Date(2020, 12, 31),
          value: new Date(),
          onSelect: this.selectHandle,
          onCancel: this.cancelHandle
        })
      }
      this.datePicker.show()
    },

    //选择日期操作
    selectHandle(date, selectedVal, selectedText) {
      let dateStr = selectedVal[0] + '-' + selectedVal[1] + '-' + selectedVal[2]
      this.model.birthdayValue = dateStr
    },

    //取消日期选择
    cancelHandle() {
      this.$createToast({
        type: 'correct',
        txt: '你取消了选择',
        time: 1000
      }).show()
    },

    //提交表单
    submitHandler(e, model) {
      let account = {
        username: model.accountValue,
        password: model.passwordValue
      }
      let user = {
        nickname: model.nickNameValue,
        //yyyy-MM-dd HH:mm:ss
        birthday: model.birthdayValue + ' 00:00:00',
        sex: model.sexValue,
        phone: model.phoneValue
      }
      //注册请求
      registryApi(account, user).then(
        res => {
          let resData = res.data
          if (resData.code === 1) {
            //注册成功，跳转到登录页面
            this.$router.push({ path: '/login' })
          } else {
            //注册失败，提示用户信息
            const toast = this.$createToast({
              txt: resData.message,
              type: 'error',
              time: 1500
            })
            toast.show()
          }
        }
      )
    },

    toLogin() {
      this.$router.push({ path: '/login' })
    }
  },
  mounted() {},
}
</script>
<style lang='scss' scoped>
.date {
  display: flex;
  justify-content: center;
  .btn {
    background-color: #ffffff;
    color: #C0C0C0;
  }
}
.btn-group {
  display: flex;
  .reg {
    margin-left: 10px;
    float: left;
    width: 100px;
    background-color: #26A2FF;
  }
  .toLogin {
    float: left;
    margin-left: 30px;
    width: 100px;
    background-color: #26A2FF;
  }
}

</style>
