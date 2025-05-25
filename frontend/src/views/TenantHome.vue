<template>
  <div class="p-6">
    <h1 class="text-2xl font-bold mb-4">租客首頁</h1>
    <p class="mb-4">
      <span v-if="user"
        >您好：{{ user.username }}（{{ roleName(user.role) }}）</span
      >
    </p>
    <p class="mb-4">您可以開始瀏覽房源、留言與評價：</p>

    <router-link
      to="/properties"
      class="block mb-2 text-blue-500 hover:underline"
    >
      🏘️ 瀏覽所有房源
    </router-link>
    <router-link to="/forum" class="block mb-2 text-blue-500 hover:underline">
      💬 進入論壇留言區
    </router-link>
    <router-link
      to="/rate-landlord"
      class="block mb-2 text-blue-500 hover:underline"
    >
      🌟 評價房東
    </router-link>
    <router-link to="/review-house" class="block text-blue-500 hover:underline">
      🏠 評價房子
    </router-link>
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
/* 美化樣式區域 */
</style>
