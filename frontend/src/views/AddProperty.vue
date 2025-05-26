<template>
  <div class="form-container">
    <h2 class="form-title">📋 新增房源</h2>
    <form @submit.prevent="submitProperty">
      <!-- 📍 基本資訊 -->
      <div class="form-group">
        <label>標題</label>
        <input v-model="form.title" type="text" required />
        <p
          v-if="form.title.length > 0 && form.title.length < 5"
          class="error-msg"
        >
          標題至少需 5 個字
        </p>
      </div>

      <div class="form-group">
        <label>地址</label>
        <input v-model="form.address" type="text" required />
      </div>

      <div class="form-row">
        <div class="form-group">
          <label>租金 (NT$)</label>
          <input v-model.number="form.price" type="number" required />
        </div>
        <div class="form-group">
          <label>坪數 (m²)</label>
          <input v-model.number="form.squareMeters" type="number" required />
        </div>
      </div>

      <div class="form-group">
        <label>描述</label>
        <textarea v-model="form.description" rows="3" required></textarea>
        <p
          v-if="form.description.length > 0 && form.description.length < 10"
          class="error-msg"
        >
          描述至少需 10 個字
        </p>
      </div>

      <!-- 🛏 格局 -->
      <div class="form-row">
        <div class="form-group">
          <label>房間數</label>
          <input v-model.number="form.bedrooms" type="number" required />
        </div>
        <div class="form-group">
          <label>衛浴數</label>
          <input v-model.number="form.bathrooms" type="number" required />
        </div>
        <div class="form-group">
          <label>區域代碼</label>
          <input v-model.number="form.area" type="number" required />
        </div>
      </div>

      <div class="form-group">
        <label>類型</label>
        <select v-model="form.type" required>
          <option value="套房">套房</option>
          <option value="公寓">公寓</option>
          <option value="透天">透天</option>
        </select>
      </div>

      <!-- 🖼 上傳圖片 -->
      <div class="form-group">
        <label>上傳圖片</label>
        <input type="file" multiple @change="handleFileChange" />
        <ul class="preview-list">
          <li
            v-for="(file, idx) in selectedFiles"
            :key="idx"
            class="preview-item"
          >
            <img
              v-if="file?.type?.startsWith('image/') && previewUrls[idx]"
              :src="previewUrls[idx]"
              class="preview-img"
            />
            <span v-else>📁 {{ file?.name || "未命名檔案" }}</span>
          </li>
        </ul>
      </div>

      <!-- 📤 送出按鈕 -->
      <div class="form-group text-center">
        <button :disabled="loading">
          <span v-if="loading" class="spinner"></span>
          {{ loading ? "處理中..." : "📤 提交房源" }}
        </button>
      </div>
    </form>
  </div>
</template>

<script setup>
import { ref } from "vue";
import axios from "axios";
import { ElMessage } from "element-plus";
import { useAuthStore } from "@/stores/auth";

const authStore = useAuthStore();

const form = ref({
  title: "",
  address: "",
  price: 0,
  description: "",
  bedrooms: 1,
  bathrooms: 1,
  squareMeters: 5,
  area: 0,
  type: "套房",
});

const selectedFiles = ref([]);
const previewUrls = ref([]);
const loading = ref(false);

const handleFileChange = (e) => {
  selectedFiles.value = Array.from(e.target.files || []);
  previewUrls.value = selectedFiles.value.map((file) =>
    URL.createObjectURL(file)
  );
};

const resetForm = () => {
  form.value = {
    title: "",
    address: "",
    price: 0,
    description: "",
    bedrooms: 1,
    bathrooms: 1,
    squareMeters: 5,
    area: 0,
    type: "套房",
  };
  selectedFiles.value = [];
  previewUrls.value = [];
};

const submitProperty = async () => {
  if (!authStore.user || !authStore.user.id) {
    ElMessage.error("無法取得登入使用者 ID，請重新登入");
    return;
  }

  if (form.value.title.length < 5 || form.value.description.length < 10) {
    ElMessage.error("請確認標題與描述長度是否足夠");
    return;
  }

  loading.value = true;
  try {
    const payload = { ...form.value };

    const { data } = await axios.post(
      `http://localhost:8080/api/properties?userId=${authStore.user.id}`,
      payload,
      {
        headers: { "Content-Type": "application/json" },
      }
    );

    ElMessage.success("✅ 房源新增成功");

    if (selectedFiles.value.length > 0) {
      const formData = new FormData();
      selectedFiles.value.forEach((file) => formData.append("files", file));
      await axios.post(
        `http://localhost:8080/api/properties/${data.id}/uploadPhotos`,
        formData,
        {
          headers: { "Content-Type": "multipart/form-data" },
        }
      );
      ElMessage.success("✅ 圖片上傳成功");
    }

    resetForm();
  } catch (err) {
    const errorMessage =
      err.response?.data?.error ||
      err.response?.data ||
      err.message ||
      "未知錯誤";
    console.error("新增房源失敗：", errorMessage);
    ElMessage.error("新增失敗：" + errorMessage);
  } finally {
    loading.value = false;
  }
};
</script>

<style scoped>
/* 原樣保留 CSS 不變 */
.form-container {
  max-width: 680px;
  margin: 2rem auto;
  padding: 2rem;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.form-title {
  font-size: 1.75rem;
  font-weight: bold;
  margin-bottom: 1.5rem;
  border-bottom: 2px solid #eee;
  padding-bottom: 0.5rem;
}

.form-group {
  margin-bottom: 1.25rem;
}

.form-row {
  display: flex;
  gap: 1rem;
}

.form-row .form-group {
  flex: 1;
}

input,
textarea,
select {
  width: 100%;
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 1rem;
}

textarea {
  resize: vertical;
}

button {
  background-color: #2563eb;
  color: white;
  font-weight: 600;
  padding: 0.6rem 1.5rem;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background 0.2s;
}

button:hover {
  background-color: #1e40af;
}

button:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.error-msg {
  font-size: 0.85rem;
  color: #e53e3e;
  margin-top: 0.25rem;
}

.preview-list {
  display: flex;
  flex-wrap: wrap;
  gap: 0.5rem;
  margin-top: 0.5rem;
}

.preview-img {
  width: 80px;
  height: 80px;
  object-fit: cover;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.spinner {
  width: 16px;
  height: 16px;
  border: 2px solid #fff;
  border-top-color: transparent;
  border-radius: 50%;
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
