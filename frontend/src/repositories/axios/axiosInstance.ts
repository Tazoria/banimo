import axios from 'axios';
import tokenService from '@/repositories/axios/tokenService';

/**
 * 공통 Axios 인스턴스
 */
const axiosInstance = axios.create({
  baseURL: '/api',
  timeout: 10000,
  headers: { 'Content-Type': 'application/json' },
});

// 요청 인터셉터 (예: 토큰추가)
axiosInstance.interceptors.request.use((config) => {
  const token = tokenService.getAccessToken();
  if (token && config.headers) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// 응답 인터셉터 (에러 통일 처리 가능)
axiosInstance.interceptors.response.use(
  (res) => res,
  (error) => Promise.reject(error),
);

export default axiosInstance;
