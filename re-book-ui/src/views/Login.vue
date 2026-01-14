<template>
  <div class="login-container">
    <div class="login-box">
      <div class="title-container">
        <h2>旧书回收系统</h2>
        <p>让知识循环利用</p>
      </div>

      <el-form
          ref="loginFormRef"
          :model="loginForm"
          :rules="rules"
          label-width="0"
          class="login-form"
      >
        <el-form-item prop="username">
          <el-input
              v-model="loginForm.username"
              placeholder="请输入管理员账号"
              :prefix-icon="User"
              size="large"
          />
        </el-form-item>

        <el-form-item prop="password">
          <el-input
              v-model="loginForm.password"
              type="password"
              placeholder="请输入密码"
              :prefix-icon="Lock"
              show-password
              size="large"
              @keyup.enter="handleLogin"
          />
        </el-form-item>

        <el-form-item>
          <el-button
              type="primary"
              :loading="loading"
              class="login-btn"
              size="large"
              @click="handleLogin"
          >
            {{ loading ? '登录中...' : '立即登录' }}
          </el-button>
        </el-form-item>
      </el-form>

      <div class="footer-tips">
        默认账号: admin / 123456 (仅供调试)
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { User, Lock } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import request from '../utils/request'

const router = useRouter()
const loginFormRef = ref(null)
const loading = ref(false)

// 绑定表单数据
const loginForm = reactive({
  username: '',
  password: ''
})

// 表单校验规则
const rules = {
  username: [
    { required: true, message: '请输入账号', trigger: 'blur' },
    { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' }
  ]
}

// 登录处理函数
const handleLogin = async () => {
  if (!loginFormRef.value) return

  await loginFormRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      try {
        // 1. 发起登录请求
        // 注意：这里的 res 已经是我们 request.js 拦截器处理后返回的 res.data 了
        // 也就是我们在后端 AuthController 里包装的那个包含 token 和 user 的 Map
        const res = await request.post('/auth/login', loginForm)

        // 2. 存储 Token (非常重要，用于后续请求头携带)
        localStorage.setItem('token', res.token)

        // 3. 存储用户信息 (用于在 Header 或个人中心展示昵称、头像)
        localStorage.setItem('user', JSON.stringify(res.user))

        // 4. 提示并跳转
        ElMessage.success({
          message: '登录成功，欢迎回来：' + res.user.nickname,
          type: 'success',
          duration: 2000
        })

        router.push('/home')
      } catch (error) {
        // 具体的错误提示已经在 request.js 的拦截器里用 ElMessage 处理过了
        console.error('登录异常:', error)
      } finally {
        loading.value = false
      }
    }
  })
}
</script>

<style scoped>
/* 整个屏幕背景：深蓝渐变 */
.login-container {
  height: 100vh;
  width: 100vw;
  background: linear-gradient(135deg, #1c2e4a 0%, #2d4b7a 100%);
  display: flex;
  justify-content: center;
  align-items: center;
}

/* 登录白盒卡片 */
.login-box {
  width: 400px;
  padding: 40px;
  background: white;
  border-radius: 10px;
  box-shadow: 0 15px 30px rgba(0, 0, 0, 0.2);
}

.title-container {
  text-align: center;
  margin-bottom: 30px;
}

.title-container h2 {
  margin: 0;
  color: #303133;
  font-size: 24px;
}

.title-container p {
  margin: 10px 0 0;
  color: #909399;
  font-size: 14px;
}

.login-btn {
  width: 100%;
  font-weight: bold;
}

.footer-tips {
  margin-top: 20px;
  text-align: center;
  font-size: 12px;
  color: #c0c4cc;
}
</style>