<template>
  <div class="bg-white dark:bg-gray-800 p-6 rounded shadow w-72 text-gray-900 dar:text-gray-100 text-center">
    <h2 class="font-semilbold text-lg mb-4"> {{ title }}</h2>
    <p class="mb-6">{{ message }}</p>
    <button @click="$emit('resolve')" class="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700">
      확인
    </button>
  </div>
</template>

<script>
export default {
  props: {
    title: String,
    message: String,
  },
};
</script>
