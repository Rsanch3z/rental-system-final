<template>
  <div class="review-container">
    <h2 class="title">🏠 評價房子</h2>
    <form @submit.prevent="submitReview" class="form">
      <label class="label">房源 ID</label>
      <input
        v-model.number="review.propertyId"
        type="number"
        class="input"
        placeholder="請輸入房源 ID"
        required
      />

      <label class="label">評分 (1~5)</label>
      <input
        v-model.number="review.rating"
        type="number"
        min="1"
        max="5"
        class="input"
        placeholder="請輸入評分"
        required
      />

      <label class="label">留言</label>
      <textarea
        v-model="review.comment"
        class="textarea"
        placeholder="寫下您對此房源的感想..."
        required
      ></textarea>

      <button type="submit" class="submit-btn">📤 送出評價</button>
    </form>
  </div>
</template>

<script setup>
import { ref } from "vue"
import axios from "axios"
import { ElMessage } from "element-plus"
import { useAuthStore } from "@/stores/auth"

const authStore = useAuthStore()

const review = ref({
  propertyId: null,
  rating: 5,
  comment: ""
})

const submitReview = async () => {
  try {
    const payload = {
      reviewerId: authStore.user.id,
      reviewerType: "tenant",
      propertyId: review.value.propertyId,
      targetType: "property",
      targetId: review.value.propertyId,
      rating: review.value.rating,
      comment: review.value.comment
    }
    await axios.post("http://localhost:8080/api/reviews", payload)
    ElMessage.success("✅ 評價成功")
    review.value = { propertyId: null, rating: 5, comment: "" }
  } catch (err) {
    ElMessage.error("❌ 評價失敗：" + (err.response?.data || err.message))
  }
}
</script>

<style scoped>
.review-container {
  max-width: 500px;
  margin: 40px auto;
  padding: 30px;
  background: linear-gradient(to bottom, #f8fafc, #ffffff);
  border-radius: 12px;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.06);
}

.title {
  font-size: 1.75rem;
  font-weight: bold;
  text-align: center;
  margin-bottom: 20px;
  color: #334155;
}

.form {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.label {
  font-weight: 600;
  color: #475569;
  margin-bottom: 4px;
}

.input,
.textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #cbd5e1;
  border-radius: 8px;
  font-size: 1rem;
  box-sizing: border-box;
}

.input:focus,
.textarea:focus {
  outline: none;
  border-color: #60a5fa;
  box-shadow: 0 0 0 2px rgba(96, 165, 250, 0.3);
}

.textarea {
  resize: vertical;
  min-height: 100px;
}

.submit-btn {
  background-color: #3b82f6;
  color: white;
  font-weight: 600;
  padding: 10px 16px;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: background 0.2s ease;
}

.submit-btn:hover {
  background-color: #2563eb;
}
</style>
