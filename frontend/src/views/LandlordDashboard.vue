<template>
  <div class="dashboard-container">
    <h1 class="dashboard-title">🏠 房東後台</h1>
    
    <div class="welcome-box">
      <p v-if="user" class="welcome-text">
        您好，<strong>{{ user.username }}</strong>（{{ roleName(user.role) }}）
      </p>
      <p>歡迎，您可以管理房源與評價租客：</p>
    </div>

    <div class="actions">
      <router-link to="/add-property" class="action-link">
        ➕ 新增房源
      </router-link>
      <router-link to="/rate-tenants" class="action-link">
        🌟 評價租客
      </router-link>
    </div>
  </div>
</template>

<script setup>
import { useAuthStore } from "@/stores/auth";
import { computed } from "vue";

const authStore = useAuthStore();
const user = computed(() => authStore.user);

const roleName = (role) => {
  if (role === "LANDLORD") return "房東";
  if (role === "TENANT") return "租客";
  return "使用者";
};
</script>

<style scoped>
.dashboard-container {
  max-width: 720px;
  margin: 2rem auto;
  padding: 2rem;
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.08);
}

.dashboard-title {
  font-size: 2rem;
  font-weight: bold;
  color: #1e3a8a;
  margin-bottom: 1.5rem;
  border-bottom: 2px solid #e5e7eb;
  padding-bottom: 0.5rem;
}

.welcome-box {
  background-color: #f0f4ff;
  padding: 1rem;
  border-radius: 8px;
  margin-bottom: 1.5rem;
  color: #1e40af;
}

.welcome-text {
  font-size: 1.1rem;
  margin-bottom: 0.5rem;
}

.actions {
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
}

.action-link {
  display: inline-block;
  font-size: 1rem;
  color: #2563eb;
  font-weight: 500;
  text-decoration: none;
  padding: 0.75rem 1rem;
  background-color: #e0edff;
  border: 1px solid #bfd7ff;
  border-radius: 6px;
  transition: all 0.2s ease-in-out;
}

.action-link:hover {
  background-color: #c7e0ff;
  color: #1e40af;
  transform: translateY(-2px);
}
</style>
