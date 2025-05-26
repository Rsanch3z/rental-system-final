<template>
  <div class="register-container">
    <el-card class="register-card">
      <template #header>
        <h2>註冊</h2>
      </template>
      <el-form :model="registerForm" @submit.prevent="handleRegister" :rules="rules" ref="formRef">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="registerForm.name" placeholder="請輸入姓名" />
        </el-form-item>
        <el-form-item label="電子郵件" prop="email">
          <el-input v-model="registerForm.email" type="email" placeholder="請輸入電子郵件" />
        </el-form-item>
        <el-form-item label="密碼" prop="password">
          <el-input v-model="registerForm.password" type="password" placeholder="請輸入密碼" />
        </el-form-item>
        <el-form-item label="確認密碼" prop="confirmPassword">
          <el-input v-model="registerForm.confirmPassword" type="password" placeholder="請再次輸入密碼" />
        </el-form-item>
        <el-form-item label="電話" prop="phoneNumber">
        <el-input v-model="registerForm.phoneNumber" placeholder="請輸入電話" />
        </el-form-item>
        <el-form-item>
           </el-form-item>
        <el-form-item label="身份" prop="role">
        <el-select v-model="registerForm.role" placeholder="請選擇身份">
        <el-option label="租客 (Tenant)" value="TENANT" />
        <el-option label="房東 (Landlord)" value="LANDLORD" />
        </el-select>
        </el-form-item>
        <el-button type="primary" native-type="submit" :loading="loading">註冊</el-button>
       

      </el-form>
      <div class="login-link">
        已有帳號？ <router-link to="/login">立即登入</router-link>
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
const formRef = ref(null)

const registerForm = ref({
  name: '',
  email: '',
  password: '',
  confirmPassword: '',
  phoneNumber: '',
  role:''
})

const rules = {
  name: [{ required: true, message: '請輸入姓名', trigger: 'blur' }],
  email: [
    { required: true, message: '請輸入電子郵件', trigger: 'blur' },
    { type: 'email', message: '請輸入有效的電子郵件', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '請輸入密碼', trigger: 'blur' },
    { min: 6, message: '密碼長度至少為6個字符', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '請確認密碼', trigger: 'blur' },
    {
      validator: (rule, value, callback) => {
        if (value !== registerForm.value.password) {
          callback(new Error('兩次輸入的密碼不一致'))
        } else {
          callback()
        }
      },
      trigger: 'blur'
    }
  ],
  phoneNumber: [
  { required: true, message: '請輸入電話', trigger: 'blur' },
  {
    pattern: /^09\d{8}$/,
    message: '請輸入正確的台灣手機號碼（09 開頭，共 10 碼）',
    trigger: 'blur'
  }
],
  role: [{ required: true, message: '請選擇身份', trigger: 'change' }]
}

const handleRegister = async () => {
  if (!formRef.value) return
  
  await formRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      try {
        const success = await authStore.register({
          name: registerForm.value.name,
          email: registerForm.value.email,
          password: registerForm.value.password,
          phoneNumber: registerForm.value.phoneNumber,
          role: registerForm.value.role
        })
        
        if (success) {
          ElMessage.success('註冊成功')
          router.push('/login')
        } else {
          ElMessage.error('註冊失敗')
        }
      } catch (error) {
        ElMessage.error('註冊時發生錯誤')
      }
      loading.value = false
    }
  })
}
</script>

<style scoped>
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background-color: #f5f7fa;
}

.register-card {
  width: 100%;
  max-width: 400px;
}

.login-link {
  text-align: center;
  margin-top: 16px;
}

.el-form-item {
  margin-bottom: 20px;
}
</style> 