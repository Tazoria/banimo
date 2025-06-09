<template>
  <div class="bg-white dark:bg-gray-800 p-6 rounded-lg shadow-xl w-80 text-center text-gray-900 dark:text-gray-100">
    <h2 class="text-lg font-semibold mb-4"{{ title }}></h2>
    <p class="mb-6">{{ message }}</p>
    <div class="flex justify-between">
      <button @click="$emit('reject')" class="px-4 py-2 rounded bg-gray-300 dark:bg-gray-600 hover:bg-gray-400 dark:hover:bgr-gray-500">
        취소
      </button>
      <button @click="$emit('resolve', true)" class="px-4 py-2 rounded bg-blue-600 text-white hover:bg-blue-700">
        확인
      </button>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    title: { type: String, default: '확인'},
    message: { type: String, required: true },
  }
};
</script>
