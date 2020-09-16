export default {
  state: {
    //如果localStorage里面有token，则直接获取，否则返回空串
    token: localStorage.getItem('token') || '',
    //账户信息+用户信息
    account: JSON.parse(localStorage.getItem('account')) || {}
  },

  //同步修改state里面的值
  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },

    SET_ACCOUNT: (state, account) => {
      state.account = account
    }
  },

  //异步修改mutations里面的方法，context.commit利用上下文触发mutations里面的方法
  //在vue中，this.$store.dispatch触发action里面的方法
  actions: {
    //设置token
    setToken(context, token) {
      context.commit('SET_TOKEN', token)
    },

    //清除token
    clearToken(context) {
      context.commit('SET_TOKEN', '')
    },

    clearAccount(context) {
      context.commit('SET_ACCOUNT', { })
    },

    //设置用户信息
    setAccount(context, account) {
      context.commit('SET_ACCOUNT', account)
    }
  }
}