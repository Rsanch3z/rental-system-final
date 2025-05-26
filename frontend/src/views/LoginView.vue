<template>
  <div class="login-container">
    <el-card class="login-card" shadow="hover">
      <template #header>
        <h2 class="card-title">🔐 登入</h2>
      </template>

      <el-form :model="loginForm" @submit.prevent="handleLogin" class="login-form">
        <el-form-item label="電子郵件">
          <el-input
            v-model="loginForm.email"
            type="email"
            placeholder="請輸入電子郵件"
            clearable
          />
        </el-form-item>

        <el-form-item label="密碼">
          <el-input
            v-model="loginForm.password"
            type="password"
            placeholder="請輸入密碼"
            show-password
          />
        </el-form-item>

        <el-form-item>
          <el-button
            type="primary"
            native-type="submit"
            :loading="loading"
            class="login-button"
          >
            登入
          </el-button>
        </el-form-item>
      </el-form>

      <div class="register-link">
        還沒有帳號？<router-link to="/register" class="register-text">立即註冊</router-link>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../stores/auth'
import { ElMessage } from 'element-plus'

const router = useRouter()
const authStore = useAuthStore()
const loading = ref(false)

const loginForm = ref({
  email: '',
  password: ''
})

const handleLogin = async () => {
  loading.value = true
  try {
    const success = await authStore.login(loginForm.value)
    if (success) {
      ElMessage.success('登入成功')
      if (authStore.user.role === 'LANDLORD') {
        router.push('/landlord/dashboard')
      } else if (authStore.user.role === 'TENANT') {
        router.push('/tenant/home')
      } else {
        router.push('/')
      }
    } else {
      ElMessage.error('登入失敗，請檢查帳號密碼')
    }
  } catch (error) {
    ElMessage.error('登入時發生錯誤')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(to right, #e0f7fa, #e3f2fd);
  padding: 1rem;
}

.login-card {
  width: 100%;
  max-width: 420px;
  padding: 1.5rem;
  border-radius: 12px;
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.08);
}

.card-title {
  font-size: 1.5rem;
  color: #1e40af;
  font-weight: bold;
  text-align: center;
  margin: 0;
}

.login-form {
  margin-top: 1rem;
}

.el-form-item {
  margin-bottom: 20px;
}

.login-button {
  width: 100%;
  font-weight: 600;
  font-size: 1rem;
}

.register-link {
  text-align: center;
  margin-top: 1.25rem;
  font-size: 0.95rem;
  color: #4b5563;
}

.register-text {
  color: #2563eb;
  text-decoration: none;
  font-weight: 500;
  margin-left: 4px;
}

.register-text:hover {
  text-decoration: underline;
}
</style>
