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
    path: '/forgot',
    name: 'forgot',
    component: () => import('../views/outer/ForgotPassword.vue')
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
    path: '/comment',
    name: 'comment',
    component: () => import('../views/inner/detail/comment/Comment.vue'),
    meta: {
      requireAuth: true
    }
  },
  {
    path: '/introduce',
    name: 'introduce',
    component: () => import('../views/inner/detail/BookIntroduce.vue')
  },
  {
    path: '/payment',
    name: 'payment',
    component: () => import('../views/inner/other/payment/Payment.vue'),
    meta: {
      requireAuth: true
    }
  },
  {
    path: '/order',
    name: 'order',
    component: () => import('../views/inner/other/order/Order.vue'),
    meta: {
      requireAuth: true
    }
  },
  {
    path: '/order-detail',
    name: 'orderDetail',
    component: () => import('../views/inner/other/order/OrderDetail.vue'),
    meta: {
      requireAuth: true
    }
  },
  {
    path: '/address',
    name: 'address',
    component: () => import('../views/inner/other/address/Address.vue'),
    meta: {
      requireAuth: true
    }
  },
  {
    path: '/address-edit',
    name: 'addressEdit',
    component: () => import('../views/inner/other/address/AddressEdit.vue'),
    meta: {
      requireAuth: true
    }
  },
  {
    path: '/address-add',
    name: 'addressAdd',
    component: () => import('../views/inner/other/address/AddressAdd.vue'),
    meta: {
      requireAuth: true
    }
  },
  {
    path: '/infomation',
    name: 'infomation',
    component: () => import('../views/inner/mine/info/MineInfo.vue'),
    meta: {
      requireAuth: true
    }
  },
  {
    path: '/modify-nickname',
    name: 'modifyNickname',
    component: () => import('../views/inner/mine/info/ModidyNickname.vue'),
    meta: {
      requireAuth: true
    }
  },
  {
    path: '/modify-password',
    name: 'modifyPassword',
    component: () => import('../views/inner/mine/info/ModifyPassword.vue'),
    meta: {
      requireAuth: true
    }
  },
  {
    path: '/modify-phone',
    name: 'modifyPhone',
    component: () => import('../views/inner/mine/info/ModifyPhone.vue'),
    meta: {
      requireAuth: true
    }
  },
  {
    path: '/top-up',
    name: 'topUp',
    component: () => import('../views/inner/mine/info/TopUp.vue'),
    meta: {
      requireAuth: true
    }
  },
  {
    path: '/book-type',
    name: 'bookType',
    component: () => import('../views/inner/index/BookTypeIndex.vue')
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