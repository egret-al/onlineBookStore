import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [{
    path: '/',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },
  {
    path: '/home',
    component: Layout,
    redirect: '/home/upload',
    meta: {
      title: '图书',
      icon: 'plane'
    },
    children: [{
        path: 'books',
        name: 'book-list',
        component: () => import('@/views/book/BookList.vue'),
        meta: {
          title: '图书列表',
        }
      },
      {
        path: 'add',
        name: 'add',
        component: () => import('@/views/book/AddBook'),
        meta: {
          title: '上架图书',
        }
      },
      {
        path: 'detail',
        name: 'detail',
        component: () => import('@/views/book/BookDetail'),
      },
      {
        path: 'generate',
        name: 'generate',
        component: () => import('@/views/book/generate'),
        meta: {
          title: '生成素材',
        }
      },
      {
        path: 'check',
        name: 'check',
        component: () => import('@/views/book/check'),
        meta: {
          title: '查看素材',
        }
      },
    ]
  },
  {
    path: '/order',
    component: Layout,
    redirect: '/home/upload',
    meta: {
      title: '订单',
      icon: 'plane'
    },
    children: [{
      path: 'overview',
      name: 'overview',
      component: () => import('@/views/order/OrderOverview.vue'),
      meta: {
        title: '订单总览',
      }
    },
    {
      path: 'order-list',
      name: 'orderList',
      component: () => import('@/views/order/OrderList.vue'),
      meta: {
        title: '订单列表'
      }
    },
    {
      path: 'user-activity',
      name: 'userActivity',
      component: () => import('@/views/order/UserActivity.vue'),
      meta: {
        title: '用户活动',
      }
    }
    ]
  },
  // 404 page must be placed at the end !!!
  {
    path: '*',
    redirect: '/404',
    hidden: true
  }
]

const createRouter = () => new Router({
  scrollBehavior: () => ({
    y: 0
  }),
  routes: constantRoutes
})

const router = createRouter()

export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
