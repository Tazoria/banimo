// userStore.js
import axiosInstance from '@/repositories/axios/axiosInstance'; // 본인이 만든 axios 인스턴스 경로 맞춰서 수정

const userStore = {
  namespaced: true,
  state: () => ({
    accessToken: localStorage.getItem('accessToken') || '',
    userInfo: null,
    isAuthenticated: !!localStorage.getItem('accessToken'),
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
        const { accessToken } = response.data;
        localStorage.setItem('accessToken', accessToken);

        commit('SET_TOKEN', accessToken);

        const userResponse = await axiosInstance.get('/user/me');
        commit('SET_USER', userResponse.data);
      } catch (error) {
        console.error('로그인 실패:', error);
        throw new Error('로그인 실패');
      }
    },
    logout({ commit }) {
      localStorage.removeItem('accessToken');
      commit('LOGOUT');
    },
    async checkAuth({ commit }) {
      const accessToken = localStorage.getItem('accessToken');
      if (accessToken) {
        // axiosInstance 인터셉터에서 토큰 읽어서 설정함 (tokenService 사용 중)
        try {
          const response = await axiosInstance.get('/user/me');
          commit('SET_TOKEN', accessToken);
          commit('SET_USER', response.data);
        } catch (error) {
          console.error('인증 확인 실패, 로그아웃:', error);
          localStorage.removeItem('accessToken');
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
