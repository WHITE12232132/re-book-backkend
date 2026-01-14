import { createRouter, createWebHistory } from 'vue-router'

const routes = [
    {
        path: '/login',
        name: 'Login',
        component: () => import('../views/Login.vue')
    },
    {
        path: '/home',
        name: 'Layout',
        component: () => import('../views/Layout.vue'),
        redirect: '/home/dashboard',
        children: [
            {
                path: 'dashboard',
                name: 'Dashboard',
                component: () => import('../views/Dashboard.vue')
            },
            { path: 'audit', component: () => import('../views/Audit.vue') },
            { path: 'books', component: () => import('../views/BookList.vue') },
            { path: 'system', component: () => import('../views/System.vue') }
        ]
    },
    {
        path: '/',
        redirect: '/login'
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

// 路由守卫：每次跳转前都会执行
router.beforeEach((to, from, next) => {
    const token = localStorage.getItem('token')

    // 1. 如果去的是登录页，直接放行
    if (to.path === '/login') {
        next()
    } else {
        // 2. 如果去的是其他页面，检查是否有 Token
        if (!token) {
            // 没有 Token，强制跳回登录页
            next('/login')
        } else {
            // 有 Token，允许进入
            next()
        }
    }
})

export default router