const modalStore = {
  namespaced: true,
  state: () => ({
    showModal: false,
    modalComponent: null,
    modalProps: {},
    resolve: null,
    reject: null,
  }),
  mutations: {
    OPEN_MODAL(state, { component, props, resolve, reject }) {
      state.showModal = true;
      state.modalComponent = component;
      state.modalProps = props || {};
      state.resolve = resolve;
      state.reject = reject;
    },
    CLOSE_MODAL(state) {
      state.showModal = false;
      state.modalComponent = null;
      state.modalProps = {};
      state.resolve = null;
      state.reject = null;
    },
  },
  actions: {
    openModal({ state, commit }, { component, props }) {
      if (state.showModal) {
        console.warn('모달이 이미 열려있습니다.');
        return Promise.reject(new Error('중첩 모달 방지'));
      }

      return new Promise((resolve, reject) => {
        commit('OPEN_MODAL', { component, props, resolve, reject });
      });
    },
    resolveModal({ commit, state }, value) {
      if (state.resolve) state.resolve(value);
      commit('CLOSE_MODAL');
    },
    rejectModal({ commit, state }, reason) {
      if (state.reject) state.reject(reason);
      commit('CLOSE_MODAL');
    },
    closeModal({ commit }) {
      commit('CLOSE_MODAL');
    },
  },
  getters: {
    showModal: (state) => state.showModal,
    modalComponent: (state) => state.modalComponent,
    modalProps: (state) => state.modalProps,
  },
};

export default modalStore;
