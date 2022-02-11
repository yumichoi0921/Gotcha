import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import RoomList from "../views/RoomList.vue";
import GameRoom from "../views/GameRoom.vue";
import JokerGame from "../views/JokerGame.vue";
import UserLogin from "../views/UserLogin.vue";
import UserJoin from "../views/UserJoin.vue";
Vue.use(VueRouter);

const routes = [
  {
    path: "/login",
    name: "UserLogin",
    component: UserLogin,
  },
  {
    path: "/join",
    name: "Userjoin",
    component: UserJoin,
  },
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
