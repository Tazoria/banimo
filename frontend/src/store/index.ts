// store/index.ts
import Vue from 'vue';
import Vuex from 'vuex';
import userStore from '@/store/modules/userStore';
import { RootState } from '@/store/types';

Vue.use(Vuex);

const store = new Vuex.Store<RootState>({
  state: {
    appName: 'BanimoDiary',
  },
  modules: {
    user: userStore,
  },
});

export default store;
