<template> 
  <div class="login-container"> 
    <el-card class="login-card"> 
      <template #header> 
        <h2>登入</h2> 
      </template> 
      <el-form :model="loginForm" @submit.prevent="handleLogin"> 
        <el-form-item label="電子郵件"> 
          <el-input v-model="loginForm.email" type="email" placeholder="請輸入電子郵件" /> 
        </el-form-item> 
        <el-form-item label="密碼"> 
          <el-input v-model="loginForm.password" type="password" placeholder="請輸入密碼" /> 
        </el-form-item> 
        <el-form-item> 
          <el-button type="primary" native-type="submit" :loading="loading">登入</el-button> 
        </el-form-item> 
      </el-form> 
      <div class="register-link"> 
        還沒有帳號？ <router-link to="/register">立即註冊</router-link> 
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
        router.push('/') // fallback
      }
    } else {
      ElMessage.error('登入失敗')
    }
  } catch (error) {
    ElMessage.error('登入時發生錯誤')
  }
  loading.value = false
}

</script> 
 
 
<style scoped> 
.login-container { 
  display: flex; 
  justify-content: center; 
  align-items: center; 
  min-height: 100vh; 
  background-color: #f5f7fa; 
} 
 
.login-card { 
  width: 100%; 
  max-width: 400px; 
} 
 
.register-link { 
  text-align: center; 
  margin-top: 16px; 
} 
 
.el-form-item { 
  margin-bottom: 20px; 
} 
</style>