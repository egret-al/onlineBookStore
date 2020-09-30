import Vue from 'vue'
import './cube-ui'
import App from './App.vue'
import router from './router'
import store from './store'

import axios from 'axios'

import request from './request'
request()
Vue.config.productionTip = false
//挂载
Vue.prototype.$http = axios
//路由守卫
router.beforeEach((to, from, next) => {
  //无论是刷新还是跳转路由，都会进入到路由的前置钩子函数
  store.commit('setToken', localStorage.getItem('token'))
  if (to.meta.requireAuth) {
    //如果目的路由需要登录权限而且当前有token，则直接放行
    if (store.state.token) {
      next()
    } else {
      next({
        path: '/login',
        query: {
          redirect: to.fullPath
        }
      })
    }
  } else {
    next()
  }
})

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')