<template>
  <el-container class="app-container">
    <el-header class="app-header">
      <el-menu mode="horizontal" router class="app-menu">
        <el-menu-item index="home" @click="goHome">首頁</el-menu-item>
        <el-menu-item index="/properties">房產列表</el-menu-item>

        <!-- 📄 合約範本 -->
        <el-menu-item index="contract" @click="showContract">
          📄 住宅租賃契約書範本
        </el-menu-item>

        <div class="flex-grow" />
        <template v-if="!authStore.isAuthenticated">
          <el-menu-item index="/login">登入</el-menu-item>
          <el-menu-item index="/register">註冊</el-menu-item>
        </template>
        <template v-else>
          <el-menu-item index="logout" @click="handleLogout">登出</el-menu-item>
        </template>
      </el-menu>
    </el-header>

    <el-main class="app-main">
      <router-view />
    </el-main>

    <el-footer class="app-footer">
      © 2024 租屋系統. All rights reserved.
    </el-footer>

    <!-- 📄 合約預覽彈窗 -->
    <el-dialog v-model="showDialog" title="📄 住宅租賃契約書範本" width="80%">
      <div class="mb-2 text-right">
        <a
          href="/contracts/住宅租賃契約書範本.pdf"
          download
          target="_blank"
          class="text-blue-600 hover:underline"
        >
          ⬇️ 下載 PDF
        </a>
      </div>
      <iframe
        src="/contracts/住宅租賃契約書範本.pdf"
        width="100%"
        height="500px"
        frameborder="0"
      ></iframe>
    </el-dialog>
  </el-container>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from './stores/auth'
import { ElMessage } from 'element-plus'

const router = useRouter()
const authStore = useAuthStore()

const showDialog = ref(false)

const handleLogout = () => {
  authStore.logout()
  ElMessage.success('已成功登出')
  router.push('/login')
}

const goHome = () => {
  const user = authStore.user
  if (user?.role === 'LANDLORD') {
    router.push('/landlord/dashboard')
  } else if (user?.role === 'TENANT') {
    router.push('/tenant/home')
  } else {
    router.push('/')
  }
}

const showContract = () => {
  showDialog.value = true
}
</script>

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

.app-header {
  padding: 0;
  width: 100vw;
  max-width: 100vw;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  box-sizing: border-box;
}

.app-menu {
  width: 100%;
}

.flex-grow {
  flex-grow: 1;
}

.app-main {
  flex: 1;
  padding: 0 !important;
  margin: 0;
  width: 100vw;
  max-width: 100vw;
  overflow-x: hidden;
  box-sizing: border-box;
}

.app-footer {
  text-align: center;
  padding: 20px;
  background-color: #f5f7fa;
  width: 100vw;
  max-width: 100vw;
  box-sizing: border-box;
}
</style>
