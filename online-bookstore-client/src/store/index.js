import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    //如果localStorage里面有token，则直接获取，否则返回空串
    token: localStorage.getItem('token') || ''
  },

  //同步修改state里面的值
  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
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
    }
  },
  modules: {
  }
})
