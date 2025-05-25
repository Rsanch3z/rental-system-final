<template>
  <div class="p-6">
    <h1 class="text-2xl font-bold mb-4">房東後台</h1>
    <span v-if="user">
      您好：{{ user.username }}（{{ roleName(user.role) }}）
    </span>
    <p class="mb-4">歡迎，您可以管理房源與評價租客：</p>

    <router-link
      to="/add-property"
      class="block mb-2 text-blue-500 hover:underline"
    >
      ➕ 新增房源
    </router-link>
    <router-link to="/rate-tenants" class="block text-blue-500 hover:underline">
      🌟 評價租客
    </router-link>
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
/* 可自行加入美化樣式 */
</style>
