<template>
  <div class="p-6 max-w-xl mx-auto">
    <h2 class="text-xl font-bold mb-4">評價租客</h2>
    <form @submit.prevent="submitReview">
      <div class="mb-4">
        <label class="block mb-1">租客 ID</label>
        <input v-model="review.tenantId" type="number" class="w-full border p-2 rounded" required />
      </div>
      <div class="mb-4">
        <label class="block mb-1">評分 (1~5)</label>
        <input v-model.number="review.rating" type="number" min="1" max="5" class="w-full border p-2 rounded" required />
      </div>
      <div class="mb-4">
        <label class="block mb-1">留言</label>
        <textarea v-model="review.comment" class="w-full border p-2 rounded" required></textarea>
      </div>
      <button type="submit" class="bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700">
        送出評價
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
const review = ref({
  tenantId: '',
  rating: 5,
  comment: ''
})

const submitReview = async () => {
  try {
    const payload = {
      targetType: 'tenant',
      targetId: parseInt(review.value.tenantId),
      reviewerId: authStore.user.id,
      reviewerType: 'landlord',
      comment: review.value.comment,
      rating: review.value.rating,
      propertyId: null,
      userId: null
    }

    await axios.post('http://localhost:8080/api/reviews', payload)
    ElMessage.success('評價成功')
  } catch (err) {
    const msg = err.response?.data || err.message || '未知錯誤'
    ElMessage.error('評價失敗：' + msg)
  }
}

</script>
