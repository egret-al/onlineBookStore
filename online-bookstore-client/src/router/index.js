import Vue from 'vue'
import VueRouter from 'vue-router'

import Home from '../views/Home/Home.vue'
import Login from '@/views/Login/Login'

Vue.use(VueRouter)

  const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/user-info',
    name: 'UserInfo',
    component: () => import('../views/User/UserInfo.vue')
  },
  {
    path: '/shopping-cart',
    name: 'ShoppingCart',
    component: () => import('../views/ShoppingCart/ShoppingCart.vue')
  },
  {
    path: '/order',
    name: 'Order',
    component: () => import('../views/Order/Order.vue')
  },
  {
    path: '/search',
    name: 'Search',
    component: () => import('../views/Search/Search.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  }
]

const router = new VueRouter({
  routes
})

const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

export default router
