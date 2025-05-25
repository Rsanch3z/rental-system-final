<template>
  <div class="p-6 max-w-xl mx-auto">
    <h2 class="text-xl font-bold mb-4">論壇發文</h2>
    <form @submit.prevent="submitPost">
      <div class="mb-4">
        <label class="block mb-1">標題</label>
        <input v-model="post.title" class="w-full border p-2 rounded" required />
      </div>
      <div class="mb-4">
        <label class="block mb-1">內容</label>
        <textarea v-model="post.content" class="w-full border p-2 rounded" required></textarea>
      </div>
      <button class="bg-purple-600 text-white px-4 py-2 rounded hover:bg-purple-700">
        發文
      </button>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'
import { ElMessage } from 'element-plus'
import { useAuthStore } from '@/stores/auth'

const authStore = useAuthStore()
const post = ref({
  title: '',
  content: ''
})

const submitPost = async () => {
  try {
    const payload = {
      ...post.value,
      userId: authStore.user.id
    }
    await axios.post('http://localhost:8080/api/forums', payload)
    ElMessage.success('發文成功')
  } catch (err) {
    ElMessage.error('發文失敗：' + err.response?.data || err.message)
  }
}
</script>
