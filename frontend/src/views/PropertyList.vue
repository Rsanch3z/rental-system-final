<template>
  <div class="property-list">
    <h2 class="title">🏘 可用房源列表</h2>
    <el-row :gutter="24">
      <el-col :span="24">
        <template v-if="properties.length === 0">
          <el-card class="empty-state">
            <el-empty description="暫無可用房源" />
          </el-card>
        </template>
        <template v-else>
          <el-card
            v-for="property in properties"
            :key="property.id"
            class="property-card"
            shadow="hover"
          >
            <div class="property-info">
              <img
                v-if="property.photos.length > 0"
                :src="'http://localhost:8080' + property.photos[0].photoUrl"
                alt="房源圖片"
                class="property-image"
              />
              <h3 class="property-title">{{ property.title }}</h3>
              <p class="property-description">{{ property.description }}</p>
              <div class="property-details">
                <span>💰 價格：${{ property.price }}/月</span>
                <span>📍 地址：{{ property.address }}</span>
                <span>📌 狀態：{{ property.status }}</span>
              </div>
              <div class="actions">
                <el-button type="primary" @click="viewDetails(property.id)">
                  查看詳情
                </el-button>
                <el-button
                  type="success"
                  v-if="property.status === 'AVAILABLE'"
                  @click="rentProperty(property.id)"
                >
                  申請租用
                </el-button>
              </div>
            </div>
          </el-card>
        </template>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();
const properties = ref([]);

const fetchProperties = async () => {
  try {
    const response = await fetch("http://localhost:8080/api/properties");
    const data = await response.json();
    console.log("房源資料：", data);
    properties.value = data;
  } catch (error) {
    console.error("獲取房源列表失敗:", error);
  }
};

const viewDetails = (id) => {
  router.push(`/properties/${id}`);
};

const rentProperty = async (id) => {
  console.log("申請租用房源:", id);
};

onMounted(() => {
  fetchProperties();
});
</script>

<style scoped>
.property-list {
  padding: 24px;
  background-color: #f9fafb;
  min-height: 100vh;
}

.title {
  font-size: 1.8rem;
  font-weight: bold;
  margin-bottom: 20px;
  color: #1e40af;
}

.property-card {
  margin-bottom: 20px;
  border-radius: 12px;
  overflow: hidden;
  transition: box-shadow 0.3s ease;
}

.property-card:hover {
  box-shadow: 0 6px 18px rgba(0, 0, 0, 0.08);
}

.property-info {
  text-align: left;
}

.property-image {
  width: 100%;
  height: 200px;
  object-fit: cover;
  border-radius: 8px;
  margin-bottom: 15px;
}

.property-title {
  font-size: 1.25rem;
  font-weight: 600;
  margin-bottom: 5px;
  color: #111827;
}

.property-description {
  font-size: 0.95rem;
  color: #4b5563;
  margin-bottom: 10px;
}

.property-details {
  display: flex;
  flex-direction: column;
  gap: 6px;
  font-size: 0.9rem;
  color: #374151;
  margin-bottom: 10px;
}

.actions {
  display: flex;
  gap: 10px;
  margin-top: 10px;
}

.empty-state {
  padding: 50px;
  background-color: #fff;
  border-radius: 12px;
}
</style>
