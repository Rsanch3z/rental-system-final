<template>
  <div class="tenant-home">
    <div class="card">
      <h1 class="title">👋 歡迎回來，{{ user?.username || "租客" }}！</h1>
      <p class="subtitle">角色：{{ roleName(user?.role) }}</p>
      <p class="description">您可以從以下操作開始使用：</p>

      <div class="link-list">
        <router-link to="/properties" class="link">
          🏘️ 瀏覽所有房源
        </router-link>
        <router-link to="/forum" class="link">
          💬 進入論壇留言區
        </router-link>
        <router-link to="/rate-landlord" class="link">
          🌟 評價房東
        </router-link>
        <router-link to="/rate-house" class="link">
          🏠 評價房子
        </router-link>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";

const user = ref(null);

onMounted(() => {
  const userData = localStorage.getItem("user");
  if (userData) {
    user.value = JSON.parse(userData);
  }
});

const roleName = (role) => {
  if (role === "TENANT") return "租客";
  if (role === "LANDLORD") return "房東";
  return "使用者";
};
</script>

<style scoped>
.tenant-home {
  display: flex;
  justify-content: center;
  padding: 2rem;
  background: linear-gradient(to bottom, #f0f4ff, #ffffff);
  min-height: 100vh;
}

.card {
  background: #ffffff;
  border-radius: 12px;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.05);
  padding: 2rem;
  width: 100%;
  max-width: 500px;
  text-align: center;
}

.title {
  font-size: 1.75rem;
  font-weight: bold;
  margin-bottom: 0.5rem;
  color: #1e3a8a;
}

.subtitle {
  font-size: 1rem;
  color: #4b5563;
  margin-bottom: 1rem;
}

.description {
  font-size: 0.95rem;
  color: #6b7280;
  margin-bottom: 1.5rem;
}

.link-list {
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
}

.link {
  display: block;
  background-color: #f0f4ff;
  border: 1px solid #cbd5e1;
  color: #1d4ed8;
  padding: 0.75rem;
  border-radius: 8px;
  text-decoration: none;
  font-weight: 500;
  transition: all 0.2s ease;
}

.link:hover {
  background-color: #e0e7ff;
  border-color: #93c5fd;
}
</style>
