// userStore.ts
import { ActionContext, Module } from 'vuex';
import axios from 'axios';
import { RootState } from '@/store/types';

export interface UserInfo {
  username: string;
  email: string;
}

export interface UserState {
  accessToken: string;
  userInfo: UserInfo | null;
  isAuthenticated: boolean;
}

interface LoginCredentials {
  username: string;
  password: string;
}

interface LoginResponseData {
  accessToken: string;
}

type UserMeResponseData = UserInfo;

const userStore: Module<UserState, RootState> = {
  namespaced: true,
  state: (): UserState => ({
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
    async login({ commit }: ActionContext<UserState, RootState>, credentials: LoginCredentials): Promise<void> {
      try {
        const response = await axios.post<LoginResponseData>('/api/user/login', credentials);
        const { accessToken } = response.data;
        localStorage.setItem('accessToken', accessToken);
        axios.defaults.headers.common.Authorization = `Bearer ${accessToken}`;
        commit('SET_TOKEN', accessToken);

        const userResponse = await axios.get<UserMeResponseData>('/api/user/me');
        commit('SET_USER', userResponse.data);
      } catch (error) {
        console.error('로그인 실패:', error);
        throw new Error('로그인 실패');
      }
    },
    logout({ commit }) {
      localStorage.removeItem('accessToken');
      delete axios.defaults.headers.common.Authorization;
      commit('LOGOUT');
    },
    async checkAuth({ commit }) {
      const accessToken = localStorage.getItem('accessToken');
      if (accessToken) {
        axios.defaults.headers.common.Authorization = `Bearer ${accessToken}`;
        try {
          const response = await axios.get<UserMeResponseData>('/api/user/me');
          commit('SET_TOKEN', accessToken);
          commit('SET_USER', response.data);
        } catch (error) {
          console.error('인증 확인 실패, 로그아웃:', error);
          localStorage.removeItem('accessToken');
          delete axios.defaults.headers.common.Authorization;
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
