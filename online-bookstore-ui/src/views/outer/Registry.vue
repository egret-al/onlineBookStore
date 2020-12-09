<template>
  <div class="main">
    <cube-form :model="model" @submit="submitHandler">
      <div class="accountInfo">账户信息</div>
      <cube-form-group>
        <cube-form-item :field="fields[0]"></cube-form-item>
        <cube-form-item :field="fields[1]"></cube-form-item>
        <cube-form-item :field="fields[2]"></cube-form-item>
      </cube-form-group>
      <div class="userInfo">用户信息</div>
      <cube-form-group>
        <cube-form-item :field="fields[3]"></cube-form-item>
        <cube-form-item :field="fields[4]"></cube-form-item>
        <cube-form-item :field="fields[5]"></cube-form-item>
        <cube-form-item :field="fields[6]" class="date">
          <cube-button class="btn" @click="showDatePicker">
            {{ model.birthdayValue || "请选择出生日期" }}
          </cube-button>
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
export default {
  components: {},
  data() {
    return {
      model: {
        accountValue: '',
        passwordValue: '',
        nickNameValue: '',
        confirmPassword: '',
        sexValue: '',
        birthdayValue: '',
        phoneValue: '',
      },
      fields: [
        {
          type: "input",
          modelKey: "accountValue",
          label: "账号",
          props: {
            placeholder: "账号长度为10",
          },
          rules: {
            required: true,
            len: 10,
          },
          messages: {
            required: "账号不能为空！",
          },
        },
        {
          type: "input",
          modelKey: "passwordValue",
          label: "密码",
          props: {
            placeholder: "6到10位登录密码",
            type: "password",
            eye: {
              open: false,
            },
          },
          rules: {
            required: true,
          },
          messages: {
            required: "密码不能为空！",
          },
        },
        {
          type: "input",
          modelKey: "confirmPassword",
          label: "确认密码",
          props: {
            placeholder: "再次输入密码",
            type: "password",
            eye: {
              open: false,
            },
          },
          rules: {
            required: true,
          },
          messages: {
            required: "密码不能为空！",
          },
        },
        {
          type: "input",
          modelKey: "nickNameValue",
          label: "昵称",
          props: {
            placeholder: "禁止非法昵称",
          },
          rules: {
            required: true,
          },
          messages: {
            required: "昵称不能为空",
          },
        },
        {
          type: "radio-group",
          modelKey: "sexValue",
          label: "性别",
          props: {
            horizontal: true,
            options: ["男", "女"],
          },
          rules: {
            required: true,
          },
        },
        {
          type: "input",
          modelKey: "phoneValue",
          label: "手机号码",
          props: {
            placeholder: "请输入手机号码",
          },
          rules: {
            type: "tel",
            required: true,
            len: 11,
          },
          messages: {
            required: "手机号码不能为空！",
          },
        },
        {
          modelKey: "birthdayValue",
          label: "我的生日",
          rules: {
            required: true,
          },
        },
      ],
      options: {
        scrollToInvalidField: true,
        layout: "standard", // classic fresh
      },
    };
  },

  methods: {
    showToast(type, txt, time) {
      this.$createToast({
        type: type,
        txt: txt,
        time: time,
      }).show()
    },

    leadingZero(num) {
      if (num >= 1 && num <= 9) {
        return `0${num}`
      }
      return `${num}`
    },

    //展示日期选择器
    showDatePicker() {
      if (!this.datePicker) {
        this.datePicker = this.$createDatePicker({
          title: "出生日期",
          min: new Date(1990, 1, 1),
          max: new Date(2020, 12, 31),
          value: new Date(),
          onSelect: this.selectHandle,
          onCancel: this.cancelHandle,
        })
      }
      this.datePicker.show()
    },

    //选择日期操作
    selectHandle(date, selectedVal, selectedText) {
      let dateStr = selectedVal[0] + "-" + this.leadingZero(selectedVal[1]) + "-" + this.leadingZero(selectedVal[2])
      this.model.birthdayValue = dateStr
    },

    //取消日期选择
    cancelHandle() { },

    //提交表单
    async submitHandler(e, model) {
      e.preventDefault()
      if (model.confirmPassword.trim() !== model.passwordValue.trim()) {
        this.showToast('error', '两次输入密码不一致！', 1000)
        return
      }
      if (model.passwordValue.trim().length < 6 || model.passwordValue.trim().length > 10) {
        this.showToast('error', '密码不符合要求！', 1000)
        return
      }
      const res = await this.$http.post('/user-server/api/v1/account/pub/registry', {
        'account': {
          'username': model.accountValue,
          'password': model.passwordValue
        },
        'user': {
          'nickname': model.nickNameValue,
          'birthday': model.birthdayValue,
          'sex': model.sexValue,
          'phone': model.phoneValue
        }
      })
      if (res.code === 1) {
        this.showToast('correct', res.message, 1000)
        this.$router.go(-1)
      } else {
        this.showToast('error', res.message, 1000)
      }
      console.log(model)
    },

    toLogin() {
      this.$router.go(-1)
    },
  },
  mounted() {},
};
</script>
<style lang="stylus" scoped>
.main
  margin-top 40px
.date
  display flex
  justify-content center
  .btn
    background-color #ffffff
    color #c0c0c0
.reg
  margin-left 20%
  width 60%
  margin-top 50px
  background-color #26a2ff
.toLogin
  margin-left 20%
  width 60%
  margin-top 20px
  background-color #26a2ff
</style>
