import { defineStore } from "pinia";
import axios from "axios";

export const useAuthStore = defineStore("auth", {
  state: () => ({
    user: JSON.parse(localStorage.getItem("user")) || null,
    token: localStorage.getItem("token") || null,
  }),

  getters: {
    isAuthenticated: (state) => !!state.token,
    getUser: (state) => state.user,
  },

  actions: {
    async login(credentials) {
      try {
        const response = await axios.post(
          "http://localhost:8080/api/auth/login",
          credentials
        );
        this.token = response.data.token;
        this.user = response.data.user;
        localStorage.setItem("token", this.token);
        localStorage.setItem("user", JSON.stringify(this.user)); // ✅ 加上這行
        return true;
      } catch (error) {
        console.error("Login failed:", error);
        return false;
      }
    },

    async register(userData) {
      try {
        const response = await axios.post(
          "http://localhost:8080/api/auth/register",
          userData
        );
        return true;
      } catch (error) {
        console.error("Registration failed:", error);
        return false;
      }
    },

    logout() {
      this.user = null;
      this.token = null;
      localStorage.removeItem("token");
      localStorage.removeItem("user"); // ✅ 清除使用者資料
    },
  },
});
