import Vue from 'vue'
import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import locale from 'element-ui/lib/locale/lang/en' // lang i18n

import '@/styles/index.scss' // global css

import App from './App'
import store from './store'
import router from './router'
import request from '@/utils/request'

import '@/icons' // icon

// set ElementUI lang to EN
Vue.use(ElementUI, { locale })

//挂载axios
Vue.prototype.$http = request

Vue.config.productionTip = false

//路由守卫
router.beforeEach((to, from, next) => {
  //无论是刷新还是跳转路由，都会进入到路由的前置钩子函数
  store.commit('user/setToken', localStorage.getItem('token'))
  if (to.meta.requireAuth) {
    //如果目的路由需要登录权限而且当前有token，则直接放行
    if (store.user.state.token) {
      next()
    } else {
      next({
        path: '/',
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
  el: '#app',
  router,
  store,
  render: h => h(App)
})
