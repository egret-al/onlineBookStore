import Vue from 'vue'
import VueRouter from 'vue-router'
import Registry from '../views/outer/Registry.vue'

Vue.use(VueRouter)

const routes = [{
    path: '/',
    name: 'registry',
    redirect: '/login'
  },
  {
    path: '/registry',
    name: 'registry',
    component: Registry
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('../views/outer/Login.vue')
  },
  {
    path: '/footer',
    name: 'footer',
    component: () => import('../components/FooterNav.vue'),
    children: [{
        path: 'index',
        name: 'index',
        component: () => import('../views/inner/index/Index')
      },
      {
        path: 'classify',
        name: 'classify',
        component: () => import('../views/inner/classify/Classify.vue')
      },
      {
        path: 'search',
        name: 'search',
        component: () => import('../views/inner/search/Search.vue')
      },
      {
        path: 'cart',
        name: 'cart',
        component: () => import('../views/inner/cart/Cart.vue'),
        meta: {
          requireAuth: true
        }
      },
      {
        path: 'mine',
        name: 'mine',
        component: () => import('../views/inner/mine/Mine.vue'),
        meta: {
          requireAuth: true
        }
      },
    ]
  },
  {
    path: '/detail',
    name: 'detail',
    component: () => import('../views/inner/detail/BookDetail.vue')
  },
  {
    path: '/introduce',
    name: 'introduce',
    component: () => import('../views/inner/detail/BookIntroduce.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

export default router