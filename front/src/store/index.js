import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);
import roomStore from "@/store/roomStore.js";

const store = new Vuex.Store({
  modules: {
    roomStore,
  },
});
export default store;
