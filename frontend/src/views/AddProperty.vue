<template>
  <div class="p-6 max-w-2xl mx-auto">
    <h2 class="text-xl font-bold mb-4">新增房源</h2>
    <form @submit.prevent="submitProperty">
      <!-- 房源基本資料 -->
      <div class="mb-4">
        <label class="block font-medium mb-1">標題</label>
        <input
          v-model="form.title"
          type="text"
          class="w-full border p-2 rounded"
          required
        />
      </div>

      <div class="mb-4">
        <label class="block font-medium mb-1">地址</label>
        <input
          v-model="form.address"
          type="text"
          class="w-full border p-2 rounded"
          required
        />
      </div>

      <div class="mb-4">
        <label class="block font-medium mb-1">租金 (NT$)</label>
        <input
          v-model.number="form.price"
          type="number"
          class="w-full border p-2 rounded"
          required
        />
      </div>

      <div class="mb-4">
        <label class="block font-medium mb-1">描述</label>
        <textarea
          v-model="form.description"
          class="w-full border p-2 rounded"
          required
        ></textarea>
      </div>

      <div class="mb-4">
        <label class="block font-medium mb-1">房間數</label>
        <input
          v-model.number="form.bedrooms"
          type="number"
          class="w-full border p-2 rounded"
          required
        />
      </div>

      <div class="mb-4">
        <label class="block font-medium mb-1">衛浴數</label>
        <input
          v-model.number="form.bathrooms"
          type="number"
          class="w-full border p-2 rounded"
          required
        />
      </div>

      <div class="mb-4">
        <label class="block font-medium mb-1">坪數 (m²)</label>
        <input
          v-model.number="form.squareMeters"
          type="number"
          class="w-full border p-2 rounded"
          required
        />
      </div>

      <div class="mb-4">
        <label class="block font-medium mb-1">區域代碼</label>
        <input
          v-model.number="form.area"
          type="number"
          class="w-full border p-2 rounded"
          required
        />
      </div>

      <div class="mb-4">
        <label class="block font-medium mb-1">類型</label>
        <select v-model="form.type" class="w-full border p-2 rounded" required>
          <option value="套房">套房</option>
          <option value="公寓">公寓</option>
          <option value="透天">透天</option>
        </select>
      </div>

      <!-- ✅ 新增圖片選擇欄位 -->
      <div class="mb-4">
        <label class="block font-medium mb-1">上傳圖片</label>
        <input type="file" multiple @change="handleFileChange" />
      </div>

      <button
        type="submit"
        class="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
      >
        上傳
      </button>
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

const handleFileChange = (e) => {
  selectedFiles.value = Array.from(e.target.files);
};

const submitProperty = async () => {
  try {
    const payload = {
      ...form.value,
      owner: { id: authStore.user.id },
    };

    // 1. 先新增房源
    const { data } = await axios.post(
      "http://localhost:8080/api/properties",
      payload
    );
    ElMessage.success("房源新增成功");

    // 2. 有圖片再上傳
    if (selectedFiles.value.length > 0) {
      const formData = new FormData();
      selectedFiles.value.forEach((file) => formData.append("files", file));

      await axios.post(
        `http://localhost:8080/api/properties/${data.id}/uploadPhotos`,
        formData,
        { headers: { "Content-Type": "multipart/form-data" } }
      );
      ElMessage.success("圖片上傳成功");
    }
  } catch (err) {
    ElMessage.error("新增失敗：" + (err.response?.data || err.message));
  }
};
</script>
