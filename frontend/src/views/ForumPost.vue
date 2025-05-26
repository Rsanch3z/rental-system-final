<template>
  <div class="forum-post-container">
    <h2 class="title">📝 發表新貼文</h2>

    <form @submit.prevent="submitPost" class="space-y-5">
      <div>
        <label class="form-label">標題</label>
        <input
          v-model="post.title"
          class="form-input"
          placeholder="請輸入貼文標題"
          required
        />
        <p v-if="post.title && post.title.length < 5" class="text-red">
          標題至少需 5 個字
        </p>
      </div>

      <div>
        <label class="form-label">內容</label>
        <textarea
          v-model="post.content"
          rows="6"
          class="form-textarea"
          placeholder="分享你的想法或疑問..."
          required
        ></textarea>
        <p v-if="post.content && post.content.length < 10" class="text-red">
          內容至少需 10 個字
        </p>
      </div>

      <div class="text-center pt-2">
        <button
          type="submit"
          class="submit-btn"
          :disabled="
            loading || post.title.length < 5 || post.content.length < 10
          "
        >
          <span v-if="loading" class="spinner"></span>
          {{ loading ? "發送中..." : "📢 發文" }}
        </button>
      </div>
    </form>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";
import { ElMessage } from "element-plus";
import { useAuthStore } from "@/stores/auth";

const router = useRouter();
const authStore = useAuthStore();
const loading = ref(false);

const post = ref({
  title: "",
  content: "",
});

const resetForm = () => {
  post.value.title = "";
  post.value.content = "";
};

const submitPost = async () => {
  if (!authStore.user || !authStore.user.id) {
    ElMessage.error("請先登入後發文");
    return;
  }

  loading.value = true;
  try {
    const payload = {
      ...post.value,
      tenant: { id: authStore.user.id },
    };
    await axios.post("http://localhost:8080/api/forums", payload);
    ElMessage.success("✅ 發文成功");
    resetForm();
    router.push("/forum");
  } catch (err) {
    const msg = err.response?.data || err.message || "未知錯誤";
    ElMessage.error("發文失敗：" + msg);
  } finally {
    loading.value = false;
  }
};
</script>

<style scoped>
.forum-post-container {
  max-width: 700px;
  margin: auto;
  padding: 30px 20px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
}
.title {
  font-size: 1.5rem;
  font-weight: bold;
  margin-bottom: 25px;
  text-align: center;
}
.form-label {
  font-weight: 600;
  margin-bottom: 6px;
  display: block;
  color: #333;
}
.form-input,
.form-textarea {
  width: 100%;
  border: 1px solid #ccc;
  padding: 10px 12px;
  border-radius: 6px;
  font-size: 1rem;
}
.text-red {
  color: #e53935;
  font-size: 0.875rem;
  margin-top: 4px;
}
.submit-btn {
  background-color: #7e57c2;
  color: white;
  font-weight: bold;
  padding: 10px 24px;
  border-radius: 6px;
  font-size: 1rem;
  transition: 0.3s ease;
  cursor: pointer;
}
.submit-btn:hover {
  background-color: #673ab7;
}
.submit-btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}
.spinner {
  border: 2px solid white;
  border-top-color: transparent;
  border-radius: 9999px;
  width: 16px;
  height: 16px;
  margin-right: 8px;
  display: inline-block;
  animation: spin 0.6s linear infinite;
}
@keyframes spin {
  to {
    transform: rotate(360deg);
  }
}
</style>
