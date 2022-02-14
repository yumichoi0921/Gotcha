import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";
Vue.use(Vuex);
import roomStore from "@/store/roomStore.js";
import memberStore from "@/store/memberStore.js";
import gameStore from "@/store/gameStore.js";
const store = new Vuex.Store({
  modules: {
    roomStore,
    memberStore,
    gameStore,
  },
  plugins: [
    createPersistedState({
      paths: ["memberStore"],
    }),
  ],
});
export default store;
