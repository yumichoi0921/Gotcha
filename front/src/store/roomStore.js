import { roomList } from "@/api/room.js";

const roomStore = {
  namespaced: true,
  state: {
    rooms: [], //모든 방들
    room: null, // 하나의 방
  },
  getters: {},
  mutations: {
    SET_ROOMS: (state, rooms) => {
      rooms.forEach((room) => {
        console.log(room);
        state.rooms.push(room);
      });
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
