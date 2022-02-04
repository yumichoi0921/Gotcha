import { roomList } from "@/api/room.js";

const roomStore = {
  namespaced: true,
  state: {
    rooms: [], //모든 방들
    room: null, // 하나의 방
  },
  getters: {
    rooms(state) {
      return state.rooms;
    },
  },
  mutations: {
    SET_ROOMS: (state, rooms) => {
      state.rooms = rooms;
    },
  },
  actions: {
    getRoomList: ({ commit }) => {
      roomList(
        ({ data }) => {
          commit("SET_ROOMS", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};
export default roomStore;
