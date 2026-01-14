<template>
  <el-container class="layout-container">
    <el-aside width="220px">
      <div class="logo">
        <span>Re-Book Admin</span>
      </div>

      <el-menu
          background-color="#304156"
          text-color="#bfcbd9"
          active-text-color="#409EFF"
          :default-active="$route.path"
          router
          class="el-menu-vertical"
      >
        <el-menu-item index="/home/dashboard">
          <el-icon><DataLine /></el-icon>
          <span>数据统计</span>
        </el-menu-item>

        <el-menu-item index="/home/audit">
          <el-icon><Stamp /></el-icon>
          <span>审核管理</span>
        </el-menu-item>

        <el-menu-item index="/home/books">
          <el-icon><Reading /></el-icon>
          <span>书籍管理</span>
        </el-menu-item>

        <el-menu-item index="/home/system">
          <el-icon><Setting /></el-icon>
          <span>系统管理</span>
        </el-menu-item>
      </el-menu>
    </el-aside>

    <el-container>
      <el-header>
        <div class="header-left">
          <span>欢迎回来，管理员</span>
        </div>
        <div class="header-right">
          <el-button type="danger" link @click="handleLogout">退出登录</el-button>
        </div>
      </el-header>

      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { useRouter, useRoute } from 'vue-router'
import { ElMessageBox, ElMessage } from 'element-plus'

const router = useRouter()
// const route = useRoute() // 如果要在 script 里获取当前路由可以用这个

const handleLogout = () => {
  ElMessageBox.confirm('确定要退出登录吗?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  }).then(() => {
    localStorage.removeItem('token') // 清除 Token
    router.push('/login') // 回到登录页
    ElMessage.success('已退出')
  })
}
</script>

<style scoped>
.layout-container {
  height: 100vh; /* 全屏高度 */
}

/* 侧边栏样式 */
.el-aside {
  background-color: #304156;
  color: #fff;
}

.logo {
  height: 60px;
  line-height: 60px;
  text-align: center;
  font-size: 20px;
  font-weight: bold;
  background-color: #2b2f3a;
  color: white;
}

.el-menu-vertical {
  border-right: none; /* 去掉菜单右侧的白边 */
}

/* 顶部样式 */
.el-header {
  background-color: #fff;
  border-bottom: 1px solid #dcdfe6;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
}

/* 内容区背景 */
.el-main {
  background-color: #f0f2f5;
  padding: 20px;
}
</style>