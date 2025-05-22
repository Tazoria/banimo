import Vue from 'vue';
import VueRouter, { RouteConfig } from 'vue-router';
import LoginPage from '@/views/user/LoginPage.vue';
import SignupPage from '@/views/user/SignupPage.vue';

Vue.use(VueRouter);

const routes: Array<RouteConfig> = [
  {
    path: '/',
    redirect: '/login',
  },
  {
    path: '/login',
    name: 'Login',
    component: LoginPage,
  },
  {
    path: '/signup',
    name: 'Signup',
    component: SignupPage,
  },
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
});

export default router;
