<template>
  <div class="comment-section">
    <h2>留言</h2>

    <div class="comment-list">
      <div
        v-for="comment in comments"
        :key="comment.commentId"
        class="comment-item"
      >
        <p>
          <strong>{{ comment.user.username }}：</strong>{{ comment.content }}
        </p>
        <small>{{ formatDate(comment.commentDate) }}</small>
      </div>
    </div>

    <div class="comment-form">
      <textarea
        v-model="newComment"
        placeholder="輸入你的留言..."
        rows="3"
      ></textarea>
      <button @click="submitComment">送出留言</button>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    postId: Number, // 外部傳入貼文 ID
    userId: Number, // 外部傳入使用者 ID
  },
  data() {
    return {
      comments: [],
      newComment: "",
    };
  },
  mounted() {
    this.fetchComments();
  },
  methods: {
    async fetchComments() {
      try {
        const res = await fetch(`/api/comments/${this.postId}`);
        this.comments = await res.json();
      } catch (error) {
        console.error("載入留言失敗:", error);
      }
    },
    async submitComment() {
      if (!this.newComment.trim()) return;

      const formData = new URLSearchParams();
      formData.append("postId", this.postId);
      formData.append("userId", this.userId);
      formData.append("content", this.newComment);

      try {
        const res = await fetch("/api/comments", {
          method: "POST",
          headers: {
            "Content-Type": "application/x-www-form-urlencoded",
          },
          body: formData,
        });
        const result = await res.json();
        this.comments.push(result); // 立即顯示新留言
        this.newComment = ""; // 清空輸入框
      } catch (error) {
        console.error("留言失敗:", error);
      }
    },
    formatDate(datetime) {
      return new Date(datetime).toLocaleString();
    },
  },
};
</script>

<style scoped>
.comment-section {
  margin-top: 20px;
}
.comment-list {
  margin-bottom: 20px;
}
.comment-item {
  border-bottom: 1px solid #ddd;
  padding: 10px 0;
}
.comment-form textarea {
  width: 100%;
  resize: none;
}
.comment-form button {
  margin-top: 8px;
}
</style>
