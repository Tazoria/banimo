// userStore.js
import axiosInstance from '@/repositories/axios/axiosInstance';
import tokenService from '@/repositories/axios/tokenService';

const userStore = {
  namespaced: true,
  state: () => ({
    accessToken: tokenService.getAccessToken() || '',
    userInfo: null,
    isAuthenticated: !!tokenService.getAccessToken(),
  }),
  mutations: {
    SET_TOKEN(state, accessToken) {
      state.accessToken = accessToken;
      state.isAuthenticated = true;
    },
    SET_USER(state, userInfo) {
      state.userInfo = userInfo;
    },
    LOGOUT(state) {
      state.accessToken = '';
      state.userInfo = null;
      state.isAuthenticated = false;
    },
  },
  actions: {
    async login({ commit }, credentials) {
      try {
        const response = await axiosInstance.post('/user/login', credentials);
        const { accessToken } = response.data.data;

        tokenService.setAccessToken(accessToken);

        commit('SET_TOKEN', accessToken);

        const userResponse = await axiosInstance.get('/user/me');
        commit('SET_USER', userResponse.data);
      } catch (error) {
        console.error('로그인 실패:', error);
        throw new Error('로그인 실패');
      }
    },
    async signup(_, userInfo) {
      try {
        const response = await axiosInstance.post('/user/signup', userInfo);
        console.log('회원가입 성공:', response.data);
      } catch (error) {
        console.error('회원가입 실패:', error);
        throw new Error('회원가입 실패');
      }
    },
    logout({ commit }) {
      tokenService.removeAccessToken();
      commit('LOGOUT');
    },
    async checkAuth({ commit }) {
      const accessToken = tokenService.getAccessToken();
      if (accessToken) {
        try {
          const response = await axiosInstance.get('/user/me');
          commit('SET_TOKEN', accessToken);
          commit('SET_USER', response.data);
        } catch (error) {
          console.error('인증 확인 실패, 로그아웃:', error);
          tokenService.removeAccessToken();
          commit('LOGOUT');
        }
      } else {
        commit('LOGOUT');
      }
    },
  },
  getters: {
    isAuthenticated: (state) => state.isAuthenticated,
    userInfo: (state) => state.userInfo,
    accessToken: (state) => state.accessToken,
  },
};

export default userStore;
