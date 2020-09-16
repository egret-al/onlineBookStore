import Vue from 'vue'
import './cube-ui'
import App from './App.vue'
import router from './router'
import store from './store'

import Cube from 'cube-ui'

Vue.config.productionTip = false

Vue.use(Cube)


//配置路由拦截，根据元数据进行判断
router.beforeEach((to, from, next) => {
  //获取localStorage的token
  const token = localStorage.getItem('token')
  //筛选路由
  if (to.matched.some(record => record.meta.requiresAuth)) {
    if (token) {
      //token有效，进行放行
      next()
    } else {
      //token无效或者未登录，路由转发到登录
      next({
        path: '/login'
      })
      console.log('目的路径无效', to.path)
    }
  } else {
    //该路径不需要权限访问
    next()
  }
})

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
