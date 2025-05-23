<template>
  <div class="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
    <div class="sm:mx-auto sm:w-full sm:max-w-sm">
      <!-- <img class="mx-auto h-10 w-auto" src="https://tailwindcss.com/plus-assets/img/logos/mark.svg?color=indigo&shade=600" alt="Your Company" /> -->
      <h2 class="mt-10 text-center text-2xl leading-tight font-bold tracking-tight text-gray-900">Sign in to your account</h2>
    </div>

    <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
      <form class="space-y-6" action="#" method="POST">
        <div>
          <label for="username" class="block text-sm leading-6 font-medium text-gray-900">Username</label>
          <div class="mt-2">
            <input
              type="text"
              name="username"
              id="username"
              v-model.trim="userInfo.username"
              autocomplete="username"
              required
              class="block w-full rounded-md bg-white px-3 py-1.5 text-base text-gray-900 border border-gray-300 placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-indigo-600 sm:text-sm leading-6"
            />
          </div>
        </div>

        <div>
          <div class="flex items-center justify-between">
            <label for="password" class="block text-sm leading-6 font-medium text-gray-900">Password</label>
          </div>
          <div class="mt-2">
            <input
              type="password"
              name="password"
              id="password"
              v-model.trim="userInfo.password"
              autocomplete="current-password"
              required
              class="block w-full rounded-md bg-white px-3 py-1.5 text-base text-gray-900 border border-gray-300 placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-indigo-600 sm:text-sm leading-6"
            />
          </div>
        </div>

        <div>
          <button
            type="button"
            class="flex w-full justify-center rounded-md bg-indigo-600 px-3 py-1.5 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-600"
            @click="submit()"
          >
            Sign in
          </button>
          <p class="pt-4 text-red-600" v-if="error">로그인 실패!</p>
        </div>
      </form>
      <div class="flex flex-col w-full mt-4">
        <a href="#" class="font-semibold text-center text-indigo-600 hover:text-indigo-500">Forgot password?</a>
        <router-link to="/signup" class="mt-10 text-center text-sm leading-6 text-gray-500 ">Not a member?</router-link>
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
    async submit() {
      try {
        this.error = null;
        await this.$store.dispatch('user/login', this.userInfo);
        this.$router.push('/');
      } catch (err) {
        this.error = true;
        console.log('error > ', err);
      }
    },
  },
};
</Script>
