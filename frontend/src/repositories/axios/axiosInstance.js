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

// 요청 인터셉터 (예: 토큰 추가)
axiosInstance.interceptors.request.use((config) => {
  const token = tokenService.getAccessToken();

  // config 복사 + headers 복사 (헤더가 없을 수 있으니 기본값 설정)
  const newConfig = {
    ...config,
    headers: {
      ...config.headers,
      ...(token ? { Authorization: `Bearer ${token}` } : {}),
    },
  };

  return newConfig;
});

// 응답 인터셉터 (에러 통일 처리 가능)
axiosInstance.interceptors.response.use(
  (res) => res,
  (error) => Promise.reject(error),
);

export default axiosInstance;
