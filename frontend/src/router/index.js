// src/router/index.js
import Vue from 'vue';
import VueRouter from 'vue-router';
import store from '@/store';

Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    redirect: '/login',
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/user/LoginPage.vue'),
    meta: {
      title: 'Login',
    },
  },
  {
    path: '/signup',
    name: 'Signup',
    component: () => import('@/views/user/SignupPage.vue'),
    meta: {
      title: 'Signup',
    },
  },
  {
    path: '/diary/list',
    name: 'DiaryList',
    component: () => import('@/views/diary/DiaryList.vue'),
  },
  {
    path: '/diary/detail/:diaryId', // 다이어리 상세페이지, 작성페이지, 수정페이지 공통
    name: 'DiaryDetail',
    component: () => import('@/views/diary/DiaryDetail.vue'),
    props: true,
  },
  {
    path: '/diary/create', // 다이어리 상세페이지, 작성페이지, 수정페이지 공통
    name: 'DiaryDetail',
    component: () => import('@/views/diary/DiaryDetail.vue'),
    props: true,
  },
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
});

// 전역 네비게이션 가드
router.beforeEach((to, from, next) => {
  const isAuthenticated = !!store.getters['user/isAuthenticated'];

  // 로그인 됐는데 로그인 페이지 접근시
  if (to.path === '/login' && isAuthenticated) {
    return next('/diary/list');
  }

  // 인증 필요한 페이지인데 로그인 안된경우
  if (to.matched.some((record) => record.meta.requiresAuth) && !isAuthenticated) {
    return next('/login');
  }

  return next();
});

export default router;
