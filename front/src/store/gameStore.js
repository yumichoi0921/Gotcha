const gameStore = {
  namestaced: true,
  state: {
    emotion: "",
  },
  getters: {
    emotion(state) {
      return state.emotion;
    },
  },
};
export default gameStore;
