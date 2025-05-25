<template>
  <div v-if="property">
    <h2>{{ property.title }}</h2>
    <p>{{ property.description }}</p>
    <p>地址：{{ property.address }}</p>
    <p>價格：${{ property.price }}</p>
    <p>房間數：{{ property.bedrooms }}</p>
    <p>衛浴數：{{ property.bathrooms }}</p>
    <p>狀態：{{ property.status }}</p>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";

const route = useRoute();
const property = ref(null);

onMounted(async () => {
  const res = await fetch(
    `http://localhost:8080/api/properties/${route.params.id}`
  );
  property.value = await res.json();
});
</script>
