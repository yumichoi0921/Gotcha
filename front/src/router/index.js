import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import RoomList from "../views/RoomList.vue";
import GameRoom from "../views/GameRoom.vue";
import JokerGame from "../views/JokerGame.vue";

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
  {
    path: "/joker",
    name: "JokerGame",
    component: JokerGame,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
