// store/index.js
import Vue from 'vue';
import Vuex from 'vuex';
import userStore from '@/store/modules/userStore';

Vue.use(Vuex);

const store = new Vuex.Store({
  state: {
    appName: 'BanimoDiary',
  },
  modules: {
    user: userStore,
  },
});

export default store;
