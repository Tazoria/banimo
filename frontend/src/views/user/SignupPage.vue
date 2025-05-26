<template>
  <div class="max-w-lg mx-auto bg-white dark:bg-gray-800 rounded-lg shadow-md px-8 py-10 flex flex-col items-center">
    <h1 class="text-xl font-bold text-center text-gray-700 dark:text-gray-200 mb-8">Welcome to My Company</h1>

    <form action="#" class="w-full flex flex-col gap-4">
      <div class="flex items-start flex-col justify-start">
        <label for="username" class="text-sm text-gray-700 dark:text-gray-200 mr-2">Username:</label>
        <input
          type="text"
          id="username"
          name="username"
          autocomplete="username"
          class="w-full px-3 dark:text-gray-200 dark:bg-gray-900 py-2 rounded-md border border-gray-300 dark:border-gray-700 focus:outline-none focus:ring-1 focus:ring-blue-500"
        />
      </div>

      <div class="flex items-start flex-col justify-start">
        <label for="email" class="text-sm text-gray-700 dark:text-gray-200 mr-2">Email:</label>
        <input
          type="email"
          id="email"
          name="email"
          autocomplete="email"
          class="w-full px-3 dark:text-gray-200 dark:bg-gray-900 py-2 rounded-md border border-gray-300 dark:border-gray-700 focus:outline-none focus:ring-1 focus:ring-blue-500"
        />
      </div>

      <div class="flex items-start flex-col justify-start">
        <label for="password" class="text-sm text-gray-700 dark:text-gray-200 mr-2">Password:</label>
        <input
          type="password"
          id="password"
          name="password"
          autocomplete="new-password"
          class="w-full px-3 dark:text-gray-200 dark:bg-gray-900 py-2 rounded-md border border-gray-300 dark:border-gray-700 focus:outline-none focus:ring-1 focus:ring-blue-500"
        />
      </div>

      <div class="flex items-start flex-col justify-start">
        <label for="confirmPassword" class="text-sm text-gray-700 dark:text-gray-200 mr-2">Confirm Password:</label>
        <input
          type="password"
          id="confirmPassword"
          name="confirmPassword"
          autocomplete="new-password"
          class="w-full px-3 dark:text-gray-200 dark:bg-gray-900 py-2 rounded-md border border-gray-300 dark:border-gray-700 focus:outline-none focus:ring-1 focus:ring-blue-500"
        />
      </div>

      <button type="submit" class="bg-blue-500 hover:bg-blue-600 text-white font-medium py-2 px-4 rounded-md shadow-sm">
        Register
      </button>
    </form>

    <div class="mt-4 text-center">
      <span class="text-sm text-gray-500 dark:text-gray-300">Already have an account? </span>
      <a href="#" class="text-blue-500 hover:text-blue-600">Login</a>
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
    };
  },
  methods: {
    /* isValidUserInfo() {
      return this.userInfo.username && this.userInfo.email && this.userInfo.password && (this.userInfo.password === this.confirmPassword);
    },
    async onSubmit() {
      if (this.isValidUserInfo()) {
        await this.$store.dispatch('user/signup', this.userInfo);
        console.log('회원가입이 완료되었습니다.');
        this.$router.push('user/login');
      } else {
        console.log('가입정보를 확인해주세요.');
      }
    }, */
  },
};
</script>
