import axios from 'axios'
import { ElMessage } from 'element-plus'
import router from '../router'

// 1. 创建 axios 实例
const request = axios.create({
    baseURL: 'http://localhost:8080', // Spring Boot 后端地址
    timeout: 5000                     // 请求超时时间
})

// 2. 请求拦截器 (Request Interceptor)
// 在请求发送出去之前，检查本地是否有 Token，如果有就塞进请求头
request.interceptors.request.use(
    config => {
        const token = localStorage.getItem('token')
        if (token) {
            // 注意：这里 header 的 key 要和后端拦截器校验的 key 一致
            config.headers['Authorization'] = token
        }
        return config
    },
    error => {
        return Promise.reject(error)
    }
)

// 3. 响应拦截器 (Response Interceptor)
// 统一处理后端返回的 Result 结果
request.interceptors.response.use(
    response => {
        const res = response.data

        // 如果后端返回的不是 200，说明业务上有错
        if (res.code !== 200) {
            ElMessage.error(res.msg || '系统错误')

            // 特殊逻辑：如果后端定义某个 code 代表 token 失效（比如 401）
            if (res.code === 401) {
                localStorage.removeItem('token')
                location.href = '/login'
            }

            return Promise.reject(new Error(res.msg || 'Error'))
        }

        // 业务成功，直接返回 Result 里的 data 部分给前端调用者
        return res.data
    },
    error => {
        // 处理 HTTP 状态码错误 (如 404, 500, 403)
        let message = '网络连接异常'
        if (error.response) {
            switch (error.response.status) {
                case 401:
                    message = '登录已过期，请重新登录'
                    localStorage.removeItem('token')
                    location.href = '/login'
                    break
                case 403:
                    message = '没有权限访问'
                    break
                case 404:
                    message = '请求地址不存在'
                    break
                case 500:
                    message = '服务器内部错误'
                    break
            }
        }
        ElMessage.error(message)
        return Promise.reject(error)
    }
)

export default request