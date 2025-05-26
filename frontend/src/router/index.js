import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
    },
    {
      path: "/properties/:id",
      name: "PropertyDetail",
      component: () => import("@/views/PropertyDetail.vue"),
    },
    {
      path: "/properties",
      name: "properties",
      component: () => import("@/views/PropertyList.vue"),
    },
    {
      path: "/login",
      name: "login",
      component: () => import("../views/LoginView.vue"),
    },
    {
      path: "/register",
      name: "register",
      component: () => import("../views/RegisterView.vue"),
    },
    {
      path: "/landlord/dashboard",
      name: "LandlordDashboard",
      component: () => import("@/views/LandlordDashboard.vue"),
    },
    {
      path: "/tenant/home",
      name: "TenantHome",
      component: () => import("@/views/TenantHome.vue"),
    },
    {
      path: "/add-property",
      name: "AddProperty",
      component: () => import("@/views/AddProperty.vue"),
    },
    {
      path: "/rate-tenants",
      name: "RateTenant",
      component: () => import("@/views/RateTenant.vue"),
    },
    {
      path: "/forum", // ✅ 補上這個
      name: "Forum",
      component: () => import("@/views/Forum.vue"),
    },
    {
  path: "/forum",
  name: "Forum",
  component: () => import("@/views/Forum.vue"),
},
{
  path: "/forum/new",
  name: "ForumPost",
  component: () => import("@/views/ForumPost.vue"),
},
{
  path: "/rate-house",
  name: "RateHouse",
  component: () => import("@/views/RateHouse.vue")
},
{
  path: "/rate-landlord",
  name: "RateLandlord",
  component: () => import("@/views/RateLandlord.vue")
}

  ],
});

export default router;
