<template>
  <div class="review-container">
    <h2 class="title">📝 評價租客</h2>
    <form @submit.prevent="submitReview" class="space-y-5">
      <!-- 🧑‍💼 租客 ID -->
      <div>
        <label class="form-label">租客 ID</label>
        <input
          v-model="review.tenantId"
          type="number"
          class="form-input"
          required
        />
      </div>

      <!-- ⭐ 評分 -->
      <div>
        <label class="form-label">評分 (1~5)</label>
        <input
          v-model.number="review.rating"
          type="number"
          min="1"
          max="5"
          class="form-input"
          required
        />
      </div>

      <!-- 💬 留言 -->
      <div>
        <label class="form-label">留言</label>
        <textarea
          v-model="review.comment"
          class="form-textarea"
          rows="4"
          required
        ></textarea>
      </div>

      <!-- ✅ 送出按鈕 -->
      <div class="text-center pt-2">
        <button
          type="submit"
          class="submit-btn"
          :disabled="loading"
        >
          <span v-if="loading" class="loading-spinner"></span>
          {{ loading ? '送出中...' : '✅ 送出評價' }}
        </button>
      </div>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'
import { ElMessage } from 'element-plus'
import { useAuthStore } from '@/stores/auth'

const authStore = useAuthStore()
const loading = ref(false)

const review = ref({
  tenantId: '',
  rating: 5,
  comment: ''
})

const submitReview = async () => {
  loading.value = true
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
    review.value = { tenantId: '', rating: 5, comment: '' }
  } catch (err) {
    const msg = err.response?.data?.error || err.response?.data || err.message || '未知錯誤'
    console.error('評價失敗：', msg)
    ElMessage.error('評價失敗：' + msg)
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.review-container {
  max-width: 640px;
  margin: 2rem auto;
  padding: 2rem;
  background-color: #ffffff;
  border-radius: 12px;
  box-shadow: 0 10px 24px rgba(0, 0, 0, 0.06);
}

.title {
  font-size: 1.75rem;
  font-weight: bold;
  color: #1e3a8a;
  margin-bottom: 1.5rem;
  border-bottom: 2px solid #e5e7eb;
  padding-bottom: 0.5rem;
}

.form-label {
  font-weight: 600;
  margin-bottom: 0.5rem;
  display: block;
  color: #374151;
}

.form-input,
.form-textarea {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #d1d5db;
  border-radius: 6px;
  font-size: 1rem;
  transition: box-shadow 0.2s ease-in-out;
}

.form-input:focus,
.form-textarea:focus {
  outline: none;
  border-color: #3b82f6;
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.3);
}

.submit-btn {
  background-color: #10b981;
  color: white;
  padding: 0.6rem 1.5rem;
  font-weight: 600;
  border-radius: 8px;
  border: none;
  cursor: pointer;
  transition: background-color 0.2s ease;
}

.submit-btn:hover {
  background-color: #059669;
}

.submit-btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.loading-spinner {
  width: 16px;
  height: 16px;
  border: 2px solid white;
  border-top-color: transparent;
  border-radius: 9999px;
  display: inline-block;
  margin-right: 0.5rem;
  animation: spin 0.6s linear infinite;
}

@keyframes spin {
  to {
    transform: rotate(360deg);
  }
}
</style>
