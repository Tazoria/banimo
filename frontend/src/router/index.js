// src/router/index.js
import Vue from 'vue';
import VueRouter from 'vue-router';
import LoginPage from '@/views/user/LoginPage.vue';
import SignupPage from '@/views/user/SignupPage.vue';
import DiaryList from '@/views/diary/DiaryList.vue';

Vue.use(VueRouter);

const routes = [
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
  {
    path: '/diaryList',
    name: 'DiaryList',
    component: DiaryList,
  },
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
});

export default router;
