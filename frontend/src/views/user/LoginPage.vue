<template>
  <div class="flex min-h-screen items-center justify-center bg-gray-50 dark:bg-gray-900 px-6 py-12">
    <div class="w-full max-w-sm space-y-8">
      <div>
        <h2 class="mt-6 text-center text-2xl font-bold tracking-tight text-gray-900 dark:text-white">
          Sign in to your account
        </h2>
      </div>

      <form class="mt-8 space-y-6" @submit.prevent="handleLogin">
        <div class="space-y-4">
          <div>
            <label for="username" class="block text-sm font-medium text-gray-700 dark:text-gray-200">Username</label>
            <input
              type="text"
              id="username"
              name="username"
              v-model.trim="userInfo.username"
              autocomplete="username"
              required
              class="mt-1 block w-full rounded-md border border-gray-300 dark:border-gray-600 bg-white dark:bg-gray-800 text-gray-900 dark:text-gray-100 placeholder-gray-400 dark:placeholder-gray-500 px-3 py-2 focus:outline-none focus:ring-2 focus:ring-indigo-500"
            />
          </div>

          <div>
            <label for="password" class="block text-sm font-medium text-gray-700 dark:text-gray-200">Password</label>
            <input
              type="password"
              id="password"
              name="password"
              v-model.trim="userInfo.password"
              autocomplete="current-password"
              required
              class="mt-1 block w-full rounded-md border border-gray-300 dark:border-gray-600 bg-white dark:bg-gray-800 text-gray-900 dark:text-gray-100 placeholder-gray-400 dark:placeholder-gray-500 px-3 py-2 focus:outline-none focus:ring-2 focus:ring-indigo-500"
            />
          </div>
        </div>

        <div>
          <button
            type="submit"
            class="w-full flex justify-center rounded-md bg-indigo-600 hover:bg-indigo-700 px-4 py-2 text-sm font-medium text-white shadow-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2"
          >
            Sign in
          </button>
          <p class="pt-4 text-sm text-red-600" v-if="error">로그인 실패!</p>
        </div>
      </form>

      <div class="text-center mt-6">
        <a href="#" class="block text-sm text-indigo-600 hover:text-indigo-500 font-medium">Forgot password?</a>
        <router-link to="/signup" class="block mt-2 text-sm text-gray-500 dark:text-gray-400">Not a member? Sign up</router-link>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'LoginPage',
  data() {
    return {
      userInfo: {
        username: '',
        password: '',
      },
      error: null,
    };
  },
  methods: {
    async handleLogin() {
      try {
        this.error = null;
        await this.$store.dispatch('user/login', this.userInfo);
        this.$router.push('/diaryList');
      } catch (err) {
        this.error = true;
        console.error('로그인 실패:', err);
      }
    },
  },
};
</script>
