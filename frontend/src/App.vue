<script setup>
import { useAuthStore } from './stores/auth'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

const authStore = useAuthStore()
const router = useRouter()

const handleLogout = () => {
  authStore.logout()
  ElMessage.success('已成功登出')
  router.push('/login')
}
</script>

<template>
  <el-container class="app-container">
    <el-header class="app-header">
      <el-menu mode="horizontal" router class="app-menu">
        <el-menu-item index="/">首頁</el-menu-item>
        <el-menu-item index="/properties">房產列表</el-menu-item>
        <div class="flex-grow" />
        <template v-if="!authStore.isAuthenticated">
          <el-menu-item index="/login">登入</el-menu-item>
          <el-menu-item index="/register">註冊</el-menu-item>
        </template>
        <template v-else>
          <el-menu-item @click="handleLogout">登出</el-menu-item>
        </template>
      </el-menu>
    </el-header>

    <el-main class="app-main">
      <router-view />
    </el-main>

    <el-footer class="app-footer">
      © 2024 租屋系統. All rights reserved.
    </el-footer>
  </el-container>
</template>

<style>
html, body, #app {
  margin: 0;
  padding: 0;
  height: 100%;
  width: 100%;
}

.app-container {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

/* ✅ Header 滿版 */
.app-header {
  padding: 0;
  width: 100vw;
  max-width: 100vw;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  box-sizing: border-box;
}

/* ✅ Menu 寬度撐滿 */
.app-menu {
  width: 100%;
}

/* ✅ 空白區推開右側登入 */
.flex-grow {
  flex-grow: 1;
}

/* ✅ Main 滿版 */
.app-main {
  flex: 1;
  padding: 0 !important;
  margin: 0;
  width: 100vw;
  max-width: 100vw;
  overflow-x: hidden;
  box-sizing: border-box;
}

/* ✅ Footer 滿版 */
.app-footer {
  text-align: center;
  padding: 20px;
  background-color: #f5f7fa;
  width: 100vw;
  max-width: 100vw;
  box-sizing: border-box;
}
</style>
