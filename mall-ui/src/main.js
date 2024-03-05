import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css' // 引入ElementUI的样式
import axios from "axios";
import Router from "vue-router"

import ErrorPage from "./components/ErrorPage/ErrorPage"
import LoginPage from "./components/LoginPage/LoginPage"

// 安装ElementUI
Vue.use(ElementUI)
//安装vue-router
Vue.use(Router)

// 配置路由
const routes = [
    {path: '/', redirect:'/login'},
    {path: '/login', component: LoginPage}
    // 其他路由配置...
]

const router = new Router({
    routes
})

router.beforeEach((to, from, next) => {
    if (to.matched.length === 0) {  // 如果未匹配到路由
        // from.name ? next({name: from.name}) :
        // router.replace('/404.html')
        // history.pushState(404,'资源未找到','/404.html')
        window.location.href='/404.html'
    } else {
        next()  // 如果匹配到正确跳转
    }
})

// 配置axios的baseURL
axios.defaults.baseURL = window.IPConfig.baseURL

// 配置axios的统一拦截器
axios.interceptors.response.use(
    response => {
        if (response.data.code === 200) {
            return response
        } else {
            ElementUI.Message.error(!response.msg ? '系统异常' : response.msg)
            return Promise.reject(response.data.msg)
        }
    },
    error => {
        if (error.response.data) {
            error.message = error.response.data.msg
        }

        // 401未登录
        if (error.response.status === 401) {
            router.push("/login") // 使用router实例来push路由
        }

        ElementUI.Message.error(error.message)
        return Promise.reject(error)
    }
)

// 创建Vue实例并挂载
new Vue({
    router,
    render: h => h(App)
}).$mount('#app')