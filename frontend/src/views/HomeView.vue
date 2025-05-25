<template>
  <div class="home">
    <div class="hero">
      <h1>歡迎來到租屋系統</h1>
      <div class="actions">
        <el-button type="primary" @click="router.push('/properties')">瀏覽房源</el-button>
        <el-button v-if="!isLoggedIn" type="success" @click="router.push('/login')">登入</el-button>
        <el-button v-if="!isLoggedIn" @click="router.push('/register')">註冊</el-button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const isLoggedIn = ref(false)
</script>

<style scoped>
.home {
  width: 100%;
  min-height: calc(100vh - 120px); /* 預留 header + footer 空間，視實際高度調整 */
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(to right, #e0f7fa, #80deea);
  padding: 40px 20px;
  box-sizing: border-box;
}

.hero {
  text-align: center;
  width: 100%;
  max-width: 960px;
}

h1 {
  font-size: 48px;
  margin-bottom: 40px;
}

.actions {
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
  gap: 20px;
}

.el-button {
  font-size: 18px;
  padding: 12px 24px;
}
</style>
