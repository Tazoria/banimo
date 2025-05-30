// store/index.js
import Vue from 'vue';
import Vuex from 'vuex';
import userStore from '@/store/modules/userStore';
import VueGoodTablePlugin from 'vue-good-table';
import 'vue-good-table/dist/vue-good-table.css';

Vue.use(Vuex);
Vue.use(VueGoodTablePlugin);

const store = new Vuex.Store({
  state: {
    appName: 'BanimoDiary',
  },
  modules: {
    user: userStore,
  },
});

export default store;
