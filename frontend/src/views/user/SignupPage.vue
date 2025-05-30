<template>
  <div class="min-h-screen flex items-center justify-center px-4 bg-gray-50 dark:bg-gray-900">
    <div class="w-full max-w-lg bg-white dark:bg-gray-800 rounded-lg shadow-md px-8 py-12">
      <h1 class="text-2xl font-bold text-center text-gray-800 dark:text-gray-100 mb-8">Welcome to My Company</h1>

      <form @submit.prevent="handleSignup" class="space-y-6">
        <div>
          <label for="username" class="block text-sm font-medium text-gray-700 dark:text-gray-300">Username</label>
          <input
            id="username"
            name="username"
            type="text"
            v-model="userInfo.username"
            autocomplete="username"
            class="mt-1 block w-full rounded-md border border-gray-300 dark:border-gray-600 bg-white dark:bg-gray-900 text-gray-900 dark:text-gray-100 px-3 py-2 focus:ring-blue-500 focus:border-blue-500"
            required
          />
        </div>

        <div>
          <label for="email" class="block text-sm font-medium text-gray-700 dark:text-gray-300">Email</label>
          <input
            id="email"
            name="email"
            type="email"
            v-model="userInfo.email"
            autocomplete="email"
            class="mt-1 block w-full rounded-md border border-gray-300 dark:border-gray-600 bg-white dark:bg-gray-900 text-gray-900 dark:text-gray-100 px-3 py-2 focus:ring-blue-500 focus:border-blue-500"
            required
          />
        </div>

        <div>
          <label for="password" class="block text-sm font-medium text-gray-700 dark:text-gray-300">Password</label>
          <input
            id="password"
            name="password"
            type="password"
            v-model="userInfo.password"
            autocomplete="new-password"
            class="mt-1 block w-full rounded-md border border-gray-300 dark:border-gray-600 bg-white dark:bg-gray-900 text-gray-900 dark:text-gray-100 px-3 py-2 focus:ring-blue-500 focus:border-blue-500"
            required
          />
        </div>

        <div>
          <label for="confirmPassword" class="block text-sm font-medium text-gray-700 dark:text-gray-300">Confirm Password</label>
          <input
            id="confirmPassword"
            name="confirmPassword"
            type="password"
            v-model="confirmPassword"
            autocomplete="new-password"
            class="mt-1 block w-full rounded-md border border-gray-300 dark:border-gray-600 bg-white dark:bg-gray-900 text-gray-900 dark:text-gray-100 px-3 py-2 focus:ring-blue-500 focus:border-blue-500"
            required
          />
        </div>

        <div>
          <button type="submit" class="w-full bg-blue-600 hover:bg-blue-700 text-white font-semibold py-2 px-4 rounded-md shadow-sm">
            Register
          </button>
        </div>
      </form>

      <p class="text-red-600 mt-4 text-sm" v-if="isNull">모든 필드를 입력해주세요.</p>
      <p class="text-red-600 mt-4 text-sm" v-if="isinvalidPassword">비밀번호가 일치하지 않습니다.</p>

      <div class="mt-6 text-center">
        <span class="text-sm text-gray-500 dark:text-gray-300">Already have an account?</span>
        <router-link to="/login" class="ml-1 text-blue-600 hover:text-blue-700">Login</router-link>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'SignupPage',
  data() {
    return {
      userInfo: {
        username: '',
        email: '',
        password: '',
      },
      confirmPassword: '',
      isNull: false,
      isinvalidPassword: false,
    };
  },
  methods: {
    isValidUserInfo() {
      this.isNull = false;
      this.isinvalidPassword = false;

      if (!this.userInfo.username || !this.userInfo.email || !this.userInfo.password || !this.confirmPassword) {
        this.isNull = true;
        return false;
      }
      if (this.userInfo.password !== this.confirmPassword) {
        this.isinvalidPassword = true;
        return false;
      }
      return true;
    },
    async handleSignup() {
      if (this.isValidUserInfo()) {
        await this.$store.dispatch('user/signup', this.userInfo);
        this.$router.push('/login');
      }
    },
  },
};
</script>
