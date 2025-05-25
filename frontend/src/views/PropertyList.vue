<template>
  <div class="property-list">
    <h2>可用房源列表</h2>
    <el-row :gutter="20">
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
          >
            <div class="property-info">
              <img
                v-if="property.photos.length > 0"
                :src="'http://localhost:8080' + property.photos[0].photoUrl"
                alt="房源圖片"
                class="property-image"
              />
              <h3>{{ property.title }}</h3>
              <p>{{ property.description }}</p>
              <div class="property-details">
                <span>價格: ${{ property.price }}/月</span>
                <span>地址: {{ property.address }}</span>
                <span>狀態: {{ property.status }}</span>
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
    const data = await response.json(); // ✅ 正確順序
    console.log("房源資料：", data); // ✅ 現在才 log
    properties.value = data;
  } catch (error) {
    console.error("獲取房源列表失敗:", error);
  }
};

const viewDetails = (id) => {
  router.push(`/properties/${id}`);
};

const rentProperty = async (id) => {
  // 實現租用邏輯
  console.log("申請租用房源:", id);
};

onMounted(() => {
  fetchProperties();
});
</script>

<style scoped>
.property-image {
  height: 200px;
  width: auto;
  object-fit: contain;
  margin-bottom: 15px;
}

.property-list {
  padding: 20px;
}

.property-card {
  margin-bottom: 20px;
}

.property-info {
  h3 {
    margin-bottom: 10px;
  }
}

.property-details {
  margin: 15px 0;
  display: flex;
  gap: 20px;
}

.actions {
  margin-top: 15px;
}

.empty-state {
  padding: 40px;
  text-align: center;
}
</style>
