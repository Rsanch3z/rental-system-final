<template>
  <div class="forum-container">
    <h2 class="title">📢 論壇留言區</h2>

    <div class="actions">
      <router-link to="/forum/new" class="new-post-btn">➕ 新增貼文</router-link>
    </div>

    <div v-if="posts.length === 0" class="empty">目前尚無貼文。</div>

    <div v-for="post in posts" :key="post.id" class="post-card">
      <h3 class="post-title">{{ post.title }}</h3>
      <p class="meta">✏️ {{ post.authorName }} ‧ 🕒 {{ formatDate(post.createdAt) }}</p>
      <p class="content">{{ post.content.slice(0, 80) }}...</p>
      <router-link :to="`/forum/${post.id}`" class="view-comments-btn">💬 查看留言</router-link>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import axios from 'axios'

const posts = ref([])

const fetchPosts = async () => {
  try {
    const res = await axios.get('http://localhost:8080/api/forum')
    posts.value = res.data
  } catch (err) {
    console.error('載入貼文失敗', err)
  }
}

const formatDate = (timestamp) => {
  return new Date(timestamp).toLocaleString()
}

onMounted(() => {
  fetchPosts()
})
</script>

<style scoped>
.forum-container {
  max-width: 800px;
  margin: auto;
  padding: 20px;
}
.title {
  font-size: 1.8rem;
  font-weight: bold;
  margin-bottom: 20px;
}
.actions {
  margin-bottom: 20px;
}
.new-post-btn {
  background-color: #409eff;
  color: white;
  padding: 8px 16px;
  border-radius: 6px;
  text-decoration: none;
}
.post-card {
  background: #fff;
  padding: 16px;
  margin-bottom: 20px;
  border-radius: 8px;
  border: 1px solid #e0e0e0;
}
.post-title {
  font-size: 1.2rem;
  font-weight: bold;
}
.meta {
  font-size: 0.85rem;
  color: #888;
  margin: 5px 0 10px;
}
.content {
  margin-bottom: 10px;
}
.view-comments-btn {
  color: #409eff;
  font-weight: bold;
  text-decoration: underline;
  font-size: 0.95rem;
}
.empty {
  text-align: center;
  color: #666;
  font-style: italic;
}
</style>
