import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import RoomList from "../views/RoomList.vue";
import GameRoom from "../views/GameRoom.vue";
Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/roomList",
    name: "RoomList",
    component: RoomList,
  },
  {
    path: "/room/:roomId",
    name: "GameRoom",
    component: GameRoom,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
