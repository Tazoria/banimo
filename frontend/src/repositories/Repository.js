import axios from 'axios';
import tokenService from '@/repositories/tokenService';

/**
 * 공통 Axios 인스턴스
 */
const axiosInstance = axios.create({
  baseURL: '/api',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
});

// 요청 인터셉터: 액세스 토큰을 Authorization 헤더에 자동 추가
axiosInstance.interceptors.request.use(
  (config) => {
    const token = tokenService.getAccessToken();

    const newConfig = {
      ...config,
      headers: {
        ...config.headers,
        Authorization: token ? `Bearer ${token}` : undefined,
      },
    };

    return newConfig;
  },
  (error) => Promise.reject(error),
);

// 응답 인터셉터: 에러 처리 통일화 가능 (토큰 만료 등 대응 가능)
axiosInstance.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response && error.response.status === 401) {
      tokenService.removeAccessToken();
    }

    return Promise.reject(error);
  },
);

export default axiosInstance;
