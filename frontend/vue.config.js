const { defineConfig } = require('@vue/cli-service');
const fs = require('fs');
const path = require('path');
const dotenv = require('dotenv');
const webpack = require('webpack');

module.exports = defineConfig({
/*   transpileDependencies: [
    'vuetify',
  ], */
  css: {
    loaderOptions: {
      sass: {
        additionalData: `
          @import "@/assets/css/style.scss";
        `,
      },
    },
  },

  // 환경설정파일 경로정보 추가
  configureWebpack: () => {
    const mode = process.env.NODE_ENV || 'development';
    const envPath = path.resolve(__dirname, `config/env/.env.${mode}`); // NODE_ENV에 맞는 환경변수 파일 경로 지정

    const plugins = []; // Webpack 플러그인을 담을 배열

    // 해당 경로에 .env 파일이 실제로 존재할 경우 실행
    if (fs.existsSync(envPath)) {
      const env = dotenv.config({ path: envPath }).parsed; // dotenv로 해당 env 파일을 읽어옴

      // 읽어온 환경 변수들을 Webpack의 DefinePlugin에서 사용할 수 있도록 가공
      // 예: { 'process.env.API_URL': '"http://localhost:8080"' }
      const envKeys = Object.keys(env).reduce((acc, key) => {
        acc[`process.env.${key}`] = JSON.stringify(env[key]);
        return acc;
      }, {});

      plugins.push(new webpack.DefinePlugin(envKeys));
    }

    return {
      plugins,
    };
  },

  devServer: {
    open: true, // 개발서버 시작시 브라우저 자동열기
    hot: true, // HMR(Hot Module Replacement) 활성화: 수정 시 저넻 새로고침 없이 적용
    historyApiFallback: true, // Vue Router의 history 모드 지원: SPA에서 새로 고침 시 index.html로 fallback
    host: 'localhost', // 호스트 이름 설정
    port: 8080, // 개발서버 포트 설정
    proxy: { // 프록시 설정: /api로 시작하는 요청을 다른 서버로 전달
      '/api': {
        target: 'http://localhost:18080',
        pathRewrite: { '^/api': '' }, // 요청 경로에서 '/api' 부분을 제거
      },
    },
    client: {
      logging: 'warn', // or 'info', 'error', 'none'
      overlay: { // 컴파일 에러나 워닝 발생 시 브라우저 화면에 빨간 배경으로 오버레이로 표시
        warnings: true,
        errors: true,
      },
    },
  },

});
