const gameStore = {
  namespaced: true,
  state: {
    emotion: "",
  },
  getters: {
    emotion(state) {
      return state.emotion;
    },
  },
  mutations: {
    SET_EMOTION: (state, emotion) => {
      state.emotion = emotion;
    },
  },
};
export default gameStore;
