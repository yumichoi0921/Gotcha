const gameStore = {
  namespaced: true,
  state: {
    emotion: "",
    isGameEnd: false,
  },
  getters: {
    emotion(state) {
      return state.emotion;
    },
    isGameEnd(state) {
      return state.isGameEnd;
    },
  },
  mutations: {
    SET_EMOTION: (state, emotion) => {
      state.emotion = emotion;
    },
    SET_ISGAMEEND: (state, isGameEnd) => {
      state.isGameEnd = isGameEnd;
    },
  },
};
export default gameStore;
